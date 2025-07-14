package net.kapitencraft.mysticcraft.rpg.perks;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.advancements.FrameType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

public class DisplayInfo {

   private final ItemStack icon;
   private final FrameType frame;
   private float x;
   private float y;

   public DisplayInfo(ItemStack pIcon, FrameType pFrame) {
      this.icon = pIcon;
      this.frame = pFrame;
   }

   public void setLocation(float pX, float pY) {
      this.x = pX;
      this.y = pY;
   }

   public ItemStack getIcon() {
      return this.icon;
   }

   public FrameType getFrame() {
      return this.frame;
   }

   public float getX() {
      return this.x;
   }

   public float getY() {
      return this.y;
   }

   public static DisplayInfo fromJson(JsonObject pJson) {
      ItemStack itemstack = getIcon(GsonHelper.getAsJsonObject(pJson, "icon"));
      FrameType frametype = pJson.has("frame") ? FrameType.byName(GsonHelper.getAsString(pJson, "frame")) : FrameType.TASK;
      return new DisplayInfo(itemstack, frametype);
   }

   private static ItemStack getIcon(JsonObject pJson) {
      if (!pJson.has("item")) {
         throw new JsonSyntaxException("Unsupported icon type, currently only items are supported (add 'item' key)");
      } else {
         Item item = GsonHelper.getAsItem(pJson, "item");
         if (pJson.has("data")) {
            throw new JsonParseException("Disallowed data tag found");
         } else {
            ItemStack itemstack = new ItemStack(item);
            if (pJson.has("nbt")) {
               try {
                  CompoundTag compoundtag = TagParser.parseTag(GsonHelper.convertToString(pJson.get("nbt"), "nbt"));
                  itemstack.setTag(compoundtag);
               } catch (CommandSyntaxException commandsyntaxexception) {
                  throw new JsonSyntaxException("Invalid nbt tag: " + commandsyntaxexception.getMessage());
               }
            }

            return itemstack;
         }
      }
   }

   public void serializeToNetwork(FriendlyByteBuf pBuffer) {
      pBuffer.writeItem(this.icon);
      pBuffer.writeEnum(this.frame);

      pBuffer.writeFloat(this.x);
      pBuffer.writeFloat(this.y);
   }

   public static DisplayInfo fromNetwork(FriendlyByteBuf pBuffer) {
      ItemStack itemstack = pBuffer.readItem();
      FrameType frametype = pBuffer.readEnum(FrameType.class);
      DisplayInfo displayinfo = new DisplayInfo(itemstack, frametype);
      displayinfo.setLocation(pBuffer.readFloat(), pBuffer.readFloat());
      return displayinfo;
   }

   public JsonElement serializeToJson() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.add("icon", this.serializeIcon());
      jsonobject.addProperty("frame", this.frame.getName());

      return jsonobject;
   }

   @SuppressWarnings("DataFlowIssue")
   private JsonObject serializeIcon() {
      JsonObject jsonobject = new JsonObject();
      jsonobject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.icon.getItem()).toString());
      if (this.icon.hasTag()) {
         jsonobject.addProperty("nbt", this.icon.getTag().toString());
      }

      return jsonobject;
   }
}