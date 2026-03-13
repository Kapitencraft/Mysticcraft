package net.kapitencraft.mysticcraft.entity;

import net.kapitencraft.kap_lib.item.entity.fishing.AbstractFishingHook;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.ModEntityTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootTable;

public class LavaFishingHook extends AbstractFishingHook {
    private static final ResourceKey<LootTable> LAVA_FISHING_LOOT = ResourceKey.create(Registries.LOOT_TABLE, MysticcraftMod.res("gameplay/lava_fishing"));

    protected LavaFishingHook(Player player, Level level, int luck, int lureSpeed) {
        super(ModEntityTypes.LAVA_FISHING_HOOK.get(), player, level, luck, lureSpeed, FluidTags.LAVA);
    }

    public static LavaFishingHook create(Player player, Level level, int luck, int lureSpeed) {
        return new LavaFishingHook(player, level, luck, lureSpeed);
    }

    public LavaFishingHook(EntityType<? extends AbstractFishingHook> p_150138_, Level p_150139_) {
        super(p_150138_, p_150139_, FluidTags.LAVA);
    }

    @Override
    public ResourceKey<LootTable> lootTableId() {
        return LAVA_FISHING_LOOT;
    }
}
