package net.kapitencraft.mysticcraft.entity;

import net.kapitencraft.kap_lib.entity.fishing.ModFishingHook;
import net.kapitencraft.mysticcraft.registry.ModEntityTypes;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class LavaFishingHook extends ModFishingHook {

    protected LavaFishingHook(Player player, Level level, int luck, int lureSpeed) {
        super(ModEntityTypes.LAVA_FISHING_HOOK.get(), player, level, luck, lureSpeed, FluidTags.LAVA);
    }

    public static LavaFishingHook create(Player player, Level level, int luck, int lureSpeed) {
        return new LavaFishingHook(player, level, luck, lureSpeed);
    }

    public LavaFishingHook(EntityType<? extends ModFishingHook> p_150138_, Level p_150139_) {
        super(p_150138_, p_150139_, FluidTags.LAVA);
    }
}
