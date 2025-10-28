package net.kapitencraft.mysticcraft.rpg.skill;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;

import java.util.HashSet;
import java.util.Set;

public class PlayerPlacedBlocks extends SavedData {
    private final Set<BlockPos> placedBlocks = new HashSet<>();

    public static PlayerPlacedBlocks get(LevelAccessor level) {
        if (level.isClientSide()) {
            throw new IllegalAccessError("player placed blocks are not synced!");
        } else return ((ServerLevel) level).getDataStorage().computeIfAbsent(new Factory<>(PlayerPlacedBlocks::new, PlayerPlacedBlocks::load, null), "player_placed_blocks");
    }

    private static PlayerPlacedBlocks load(CompoundTag tag, HolderLookup.Provider provider) {
        PlayerPlacedBlocks blocks = new PlayerPlacedBlocks();
        ListTag list = tag.getList("data", Tag.TAG_LONG);
        blocks.placedBlocks.addAll(list.stream().map(LongTag.class::cast).map(LongTag::getAsLong).map(BlockPos::of).toList());
        return blocks;
    }

    public PlayerPlacedBlocks() {
    }

    @Override
    public CompoundTag save(CompoundTag tag, HolderLookup.Provider registries) {
        ListTag listTag = new ListTag();
        for (BlockPos block : placedBlocks) {
            listTag.add(LongTag.valueOf(block.asLong()));
        }
        tag.put("data", listTag);
        return tag;
    }

    public void addBlock(BlockPos pos) {
        placedBlocks.add(pos);
        setDirty();
    }

    public void removeBlock(BlockPos pos) {
        placedBlocks.remove(pos);
        setDirty();
    }

    public boolean hasBlock(BlockPos pos) {
        return placedBlocks.contains(pos);
    }
}
