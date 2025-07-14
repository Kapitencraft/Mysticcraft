package net.kapitencraft.mysticcraft.spell.cast;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;

public interface SpellCastContextParams {
    SpellCastContextParam<Vec3> ORIGIN = register("origin");
    SpellCastContextParam<BlockPos> TARGET_BLOCK = register("origin_block");
    SpellCastContextParam<LivingEntity> CASTER = register("caster");
    SpellCastContextParam<Entity> TARGET = register("target");

    private static <T> SpellCastContextParam<T> register(String name) {
        return new SpellCastContextParam<>(MysticcraftMod.res(name));
    }
}
