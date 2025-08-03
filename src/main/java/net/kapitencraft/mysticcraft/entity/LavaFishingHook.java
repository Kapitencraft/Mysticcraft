package net.kapitencraft.mysticcraft.entity;

import net.kapitencraft.kap_lib.entity.fishing.AbstractFishingHook;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.ModEntityTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class LavaFishingHook extends AbstractFishingHook {
    private static final ResourceLocation LAVA_FISHING_LOOT = MysticcraftMod.res("gameplay/lava_fishing");

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
    public ResourceLocation lootTableId() {
        return LAVA_FISHING_LOOT;
    }
}
