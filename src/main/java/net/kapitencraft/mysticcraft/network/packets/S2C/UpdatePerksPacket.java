package net.kapitencraft.mysticcraft.network.packets.S2C;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.kapitencraft.kap_lib.io.network.SimplePacket;
import net.kapitencraft.mysticcraft.client.rpg.perks.ClientPerks;
import net.kapitencraft.mysticcraft.rpg.perks.Perk;
import net.kapitencraft.mysticcraft.rpg.perks.PerkTree;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class UpdatePerksPacket implements SimplePacket {
   private final boolean reset;
   private final Map<ResourceLocation, Perk.Builder> added;
   private final Map<ResourceLocation, PerkTree.Builder> treesAdded;
   private final Set<ResourceLocation> removed;
   private final Map<ResourceLocation, Integer> progress;
   private final Map<ResourceLocation, Integer> treeProgress;

   public UpdatePerksPacket(boolean pReset, Collection<Perk> pAdded, Collection<PerkTree> treesAdded, Set<ResourceLocation> pRemoved, Map<ResourceLocation, Integer> pProgress, Map<ResourceLocation, Integer> pTreeProgress) {
      this.reset = pReset;
      ImmutableMap.Builder<ResourceLocation, Perk.Builder> perkBuilders = ImmutableMap.builder();

      for(Perk advancement : pAdded) {
         perkBuilders.put(advancement.getId(), advancement.deconstruct());
      }

      this.added = perkBuilders.build();
      this.removed = ImmutableSet.copyOf(pRemoved);
      this.progress = ImmutableMap.copyOf(pProgress);
      this.treeProgress = ImmutableMap.copyOf(pTreeProgress);

      ImmutableMap.Builder<ResourceLocation, PerkTree.Builder> treeBuilders = ImmutableMap.builder();

      for (PerkTree tree : treesAdded) {
         treeBuilders.put(tree.id(), tree.deconstruct());
      }
      this.treesAdded = treeBuilders.build();
   }

   public UpdatePerksPacket(FriendlyByteBuf pBuffer) {
      this.reset = pBuffer.readBoolean();
      this.added = pBuffer.readMap(FriendlyByteBuf::readResourceLocation, Perk.Builder::fromNetwork);
      this.treesAdded = pBuffer.readMap(FriendlyByteBuf::readResourceLocation, PerkTree.Builder::fromNetwork);
      this.removed = pBuffer.readCollection(Sets::newLinkedHashSetWithExpectedSize, FriendlyByteBuf::readResourceLocation);
      this.progress = pBuffer.readMap(FriendlyByteBuf::readResourceLocation, FriendlyByteBuf::readInt);
      this.treeProgress = pBuffer.readMap(FriendlyByteBuf::readResourceLocation, FriendlyByteBuf::readInt);
   }

   /**
    * Writes the raw packet data to the data stream.
    */
   @Override
   public void toBytes(FriendlyByteBuf pBuffer) {
      pBuffer.writeBoolean(this.reset);
      pBuffer.writeMap(this.added, FriendlyByteBuf::writeResourceLocation, (p_179441_, p_179442_) -> {
         p_179442_.serializeToNetwork(p_179441_);
      });
      pBuffer.writeMap(this.treesAdded, FriendlyByteBuf::writeResourceLocation,
              (buf, builder) -> builder.serializeToNetwork(buf));
      pBuffer.writeCollection(this.removed, FriendlyByteBuf::writeResourceLocation);
      pBuffer.writeMap(this.progress, FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::writeInt);
      pBuffer.writeMap(this.treeProgress, FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::writeInt);
   }

   public Map<ResourceLocation, Perk.Builder> getAdded() {
      return this.added;
   }

   public Set<ResourceLocation> getRemoved() {
      return this.removed;
   }

   public Map<ResourceLocation, Integer> getProgress() {
      return this.progress;
   }

   public Map<ResourceLocation, Integer> getTreeProgress() {
      return treeProgress;
   }

   public boolean shouldReset() {
      return this.reset;
   }

   @Override
   public boolean handle(Supplier<NetworkEvent.Context> sup) {
      sup.get().enqueueWork(() -> {
         ClientPerks.getInstance().update(this);
      });
      return true;
   }

    public Map<ResourceLocation, PerkTree.Builder> getTreesAdded() {
        return treesAdded;
    }
}