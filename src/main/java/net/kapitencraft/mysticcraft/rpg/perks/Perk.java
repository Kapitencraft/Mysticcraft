package net.kapitencraft.mysticcraft.rpg.perks;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.google.gson.JsonObject;
import net.kapitencraft.mysticcraft.rpg.perks.rewards.PerkReward;
import net.minecraft.Util;
import net.minecraft.advancements.FrameType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class Perk {

   @Nullable
   private final Perk parent;
   @NotNull
   private final DisplayInfo display;
   private final PerkReward rewards;
   private final ResourceLocation id;
   private final int maxLevel, childrenVisibleMin, cost;
   private final Set<Perk> children = Sets.newLinkedHashSet();
   private final Component title, description;

   public Perk(ResourceLocation pId, @Nullable Perk pParent, @NotNull DisplayInfo pDisplay, PerkReward pRewards, int maxLevel, int childrenVisibleMin, int cost) {
       this.id = pId;
       this.display = pDisplay;
       this.parent = pParent;
       this.rewards = pRewards;
       this.maxLevel = maxLevel;
       this.childrenVisibleMin = childrenVisibleMin;
       this.cost = cost;
       String id = Util.makeDescriptionId("perk", pId);
       this.title = Component.translatable(id);
       this.description = Component.translatable(id + ".desc");

      if (pParent != null) {
          pParent.addChild(this);
      }
   }

    public static Builder builder() {
        return new Builder();
    }

    /**
    * Deconstructs this advancement into a {@link Perk}.
    */
   public Perk.Builder deconstruct() {
      return new Perk.Builder(this.parent == null ? null : this.parent.getId(), this.display, this.rewards, this.maxLevel, this.childrenVisibleMin, this.cost);
   }

   /**
    * @return The parent advancement to display in the advancements screen or {@code null} to signify this is a root
    * advancement.
    */
   @Nullable
   public Perk getParent() {
      return this.parent;
   }

   public Perk getRoot() {
      return getRoot(this);
   }

   public static Perk getRoot(Perk pPerk) {
      Perk perk = pPerk;

      while(true) {
         Perk perk1 = perk.getParent();
         if (perk1 == null) {
            return perk;
         }

         perk = perk1;
      }
   }

   /**
    * @return Display information for this perk
    */
   @NotNull
   public DisplayInfo getDisplay() {
      return this.display;
   }

   public PerkReward getReward() {
      return this.rewards;
   }

   public String toString() {
      return "Perk{id=" + this.getId() + ", parent=" + (this.parent == null ? "null" : this.parent.getId()) + ", display=" + this.display + ", rewards=" + this.rewards + "}";
   }

   /**
    * @return An iterable through this perk's children.
    */
   public Iterable<Perk> getChildren() {
      return this.children;
   }

   /**
    * Add the provided {@code child} as a child of this perk.
    */
   public void addChild(Perk pChild) {
      this.children.add(pChild);
   }

   /**
    * @return The ID of this perk.
    */
   public ResourceLocation getId() {
      return this.id;
   }

   public boolean equals(Object pOther) {
      if (this == pOther) {
         return true;
      } else if (!(pOther instanceof Perk perk)) {
         return false;
      } else {
          return this.id.equals(perk.id);
      }
   }

   public int hashCode() {
      return this.id.hashCode();
   }

   public int getMaxLevel() {
        return maxLevel;
    }

   public int getChildrenVisibleMin() {
       return childrenVisibleMin;
   }

   public Component getTitle() {
       return this.title;
   }

   public Component getDescription() {
       return this.description;
   }

    public int getCost() {
        return cost;
    }

    public static class Builder {
       @Nullable
       private ResourceLocation parentId;
       @Nullable
       private Perk parent;
       private DisplayInfo display;
       private PerkReward rewards;
       private int maxLevel, childrenVisibleMin = 1, cost = 1;

       Builder(@Nullable ResourceLocation pParentId, @NotNull DisplayInfo pDisplay, PerkReward pRewards, int maxLevel, int childrenVisibleMin, int cost) {
          this.parentId = pParentId;
          this.display = pDisplay;
          this.rewards = pRewards;
          this.maxLevel = maxLevel;
          this.childrenVisibleMin = childrenVisibleMin;
          this.cost = cost;
       }

       Builder() {}

       public Perk.Builder maxLevel(int maxLevel) {
           this.maxLevel = maxLevel;
           return this;
       }

       public Perk.Builder childrenVisibleMin(int min) {
           Preconditions.checkArgument(this.maxLevel > 0, "set max level before modifying visible min!");
           this.childrenVisibleMin = Mth.clamp(min, 0, this.maxLevel);
           return this;
       }

       public Perk.Builder cost(int cost) {
           this.cost = cost;
           return this;
       }

       public Perk.Builder parent(Perk pParent) {
           this.parent = pParent;
           return this;
       }

       public Perk.Builder parent(ResourceLocation pParentId) {
           this.parentId = pParentId;
           return this;
       }

       public Perk.Builder display(ItemStack pStack, FrameType pFrame) {
           return this.display(new DisplayInfo(pStack, pFrame));
       }

       public Perk.Builder display(ItemLike pItem, FrameType pFrame) {
           return this.display(new DisplayInfo(new ItemStack(pItem.asItem()), pFrame));
       }

       public Perk.Builder display(DisplayInfo pDisplay) {
           this.display = pDisplay;
           return this;
       }

       public Perk.Builder rewards(PerkReward pRewards) {
           this.rewards = pRewards;
           return this;
       }

       public Perk.Builder rewards(PerkReward.Builder builder) {
           this.rewards = builder.build();
           return this;
       }

      /**
       * Tries to resolve the parent of this advancement, if possible. Returns {@code true} on success.
       */
      public boolean canBuild(Function<ResourceLocation, Perk> pParentLookup) {
         if (this.parentId == null) {
            return true;
         } else {
            if (this.parent == null) {
               this.parent = pParentLookup.apply(this.parentId);
            }

            return this.parent != null;
         }
      }

      public Perk build(ResourceLocation pId) {
         if (!this.canBuild((p_138407_) -> null)) {
            throw new IllegalStateException("Tried to build incomplete advancement!");
         } else {

            return new Perk(pId, this.parent, this.display, this.rewards, this.maxLevel, this.childrenVisibleMin, this.cost);
         }
      }

      public Perk save(Consumer<Perk> pConsumer, ResourceLocation pId) {
         Perk advancement = this.build(pId);
         pConsumer.accept(advancement);
         return advancement;
      }

      public JsonObject serializeToJson() {

         JsonObject jsonobject = new JsonObject();
         if (this.parent != null) {
            jsonobject.addProperty("parent", this.parent.getId().toString());
         } else if (this.parentId != null) {
            jsonobject.addProperty("parent", this.parentId.toString());
         }

         if (this.display != null) {
            jsonobject.add("display", this.display.serializeToJson());
         }
         jsonobject.add("reward", ServerPerksManager.saveRewards(this.rewards));
         jsonobject.addProperty("maxLevel", this.maxLevel);
         jsonobject.addProperty("childrenVisibleMin", this.childrenVisibleMin);
         jsonobject.addProperty("cost", this.cost);

         return jsonobject;
      }

      public void serializeToNetwork(FriendlyByteBuf pBuffer) {

         pBuffer.writeNullable(this.parentId, FriendlyByteBuf::writeResourceLocation);
         pBuffer.writeNullable(this.display, (p_214831_, p_214832_) -> {
            p_214832_.serializeToNetwork(p_214831_);
         });
         pBuffer.writeInt(this.maxLevel);
         pBuffer.writeInt(this.childrenVisibleMin);
         pBuffer.writeInt(this.cost);
      }

      public String toString() {
         return "Task Advancement{parentId=" + this.parentId + ", display=" + this.display + ", rewards=" + this.rewards + "}";
      }

      public static Perk.Builder fromJson(JsonObject pJson) {
         ResourceLocation resourcelocation = pJson.has("parent") ? new ResourceLocation(GsonHelper.getAsString(pJson, "parent")) : null;
         DisplayInfo displayinfo = pJson.has("display") ? DisplayInfo.fromJson(GsonHelper.getAsJsonObject(pJson, "display")) : null;
         PerkReward reward = ServerPerksManager.readRewards(GsonHelper.getAsJsonObject(pJson, "reward"));
         int maxLevel = GsonHelper.getAsInt(pJson, "maxLevel");
         int childrenVisibleMin = GsonHelper.getAsInt(pJson, "childrenVisibleMin");
         int cost = GsonHelper.getAsInt(pJson, "cost");
         return new Perk.Builder(resourcelocation, displayinfo, reward, maxLevel, childrenVisibleMin, cost);
      }

      public static Perk.Builder fromNetwork(FriendlyByteBuf pBuffer) {
         ResourceLocation resourcelocation = pBuffer.readNullable(FriendlyByteBuf::readResourceLocation);
         DisplayInfo displayinfo = pBuffer.readNullable(DisplayInfo::fromNetwork);
         return new Perk.Builder(resourcelocation, displayinfo, null, pBuffer.readInt(), pBuffer.readInt(), pBuffer.readInt());
      }

        public boolean valid() {
            return this.rewards != null;
        }
    }
}
