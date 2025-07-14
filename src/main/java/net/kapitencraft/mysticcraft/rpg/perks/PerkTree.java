package net.kapitencraft.mysticcraft.rpg.perks;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public record PerkTree(Perk root, ResourceLocation id, ResourceLocation background, ItemStack icon) {

    public Component getTitle() {
        return Component.translatable(Util.makeDescriptionId("perk_tree", this.id()));
    }

    public static Builder builder(Perk root) {
        return new Builder(root);
    }

    public PerkTree.Builder deconstruct() {
        return new Builder(this.root.getId(), this.background, this.icon);
    }

    public static class Builder {
        private ResourceLocation background;
        private final ResourceLocation rootId;
        private Perk root;
        private ItemStack icon;

        public Builder(Perk root) {
            this.root = root;
            this.rootId = root.getId();
        }

        public Builder(ResourceLocation rootId, ResourceLocation background, ItemStack icon) {
            this.rootId = rootId;
            this.background = background;
            this.icon = icon;
        }

        public Builder background(ResourceLocation location) {
            this.background = location;
            return this;
        }

        public Builder icon(ItemStack icon) {
            this.icon = icon;
            return this;
        }

        public boolean canBuild(Function<ResourceLocation, Perk> perkLookup) {
            if (this.rootId != null) {
                if (this.root == null) this.root = perkLookup.apply(rootId);
                return this.root != null;
            }
            return false;
        }

        public PerkTree build(ResourceLocation id) {
            if (!canBuild(r -> null)) throw new IllegalStateException("could not build perk tree with id '" + id + "'");
            return new PerkTree(this.root, id, this.background, this.icon);
        }

        public PerkTree save(Consumer<PerkTree> consumer, ResourceLocation id) {
            PerkTree tree = this.build(id);
            consumer.accept(tree);
            return tree;
        }

        public JsonElement serializeToJson() {
            JsonObject object = new JsonObject();
            object.addProperty("root", this.rootId.toString());
            object.add("icon", this.serializeIcon());
            object.addProperty("background", Objects.requireNonNull(this.background).toString());
            return object;
        }

        @SuppressWarnings("DataFlowIssue")
        private JsonObject serializeIcon() {
            if (icon == null) throw new IllegalStateException("icon is null!");
            JsonObject jsonobject = new JsonObject();
            jsonobject.addProperty("item", ForgeRegistries.ITEMS.getKey(this.icon.getItem()).toString());
            if (this.icon.hasTag()) {
                jsonobject.addProperty("nbt", this.icon.getTag().toString());
            }

            return jsonobject;
        }


        public void serializeToNetwork(FriendlyByteBuf buf) {
            buf.writeResourceLocation(this.background);
            buf.writeResourceLocation(this.rootId);
            buf.writeItem(this.icon);
        }

        public static PerkTree.Builder fromJson(JsonObject object) {
            ResourceLocation background = new ResourceLocation(GsonHelper.getAsString(object, "background"));
            ItemStack icon = getIcon(GsonHelper.getAsJsonObject(object, "icon"));
            ResourceLocation root = new ResourceLocation(GsonHelper.getAsString(object, "root"));
            return new Builder(root, background, icon);
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

        public static PerkTree.Builder fromNetwork(FriendlyByteBuf buf) {
            ResourceLocation background = buf.readResourceLocation();
            ResourceLocation root = buf.readResourceLocation();
            ItemStack icon = buf.readItem();

            return new Builder(root, background, icon);
        }
    }
}
