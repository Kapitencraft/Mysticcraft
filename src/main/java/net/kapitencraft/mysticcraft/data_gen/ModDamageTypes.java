package net.kapitencraft.mysticcraft.data_gen;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public interface ModDamageTypes {
    ResourceKey<DamageType> CHAIN_LIGHTNING = register("chain_lightning");
    ResourceKey<DamageType> MANA_OVERFLOW = register("mana_overflow");
    ResourceKey<DamageType> MANA_OVERFLOW_SELF = register("mana_overflow_self");
    ResourceKey<DamageType> ABILITY = register("ability");
    ResourceKey<DamageType> MAGIC_EXPLOSION = register("magic_explosion");
    ResourceKey<DamageType> NUMBNESS = register("numbness");
    ResourceKey<DamageType> SCORCH = register("scorch");

    static ResourceKey<DamageType> register(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, MysticcraftMod.res(name));
    }

    static void bootstrap(BootstapContext<DamageType> context) {
        context.register(CHAIN_LIGHTNING, new DamageType("chain_lightning", .1f));
        context.register(ABILITY, new DamageType("ability", .1f));
        context.register(MAGIC_EXPLOSION, new DamageType("magic_explosion", .3f));
        context.register(NUMBNESS, new DamageType("numbness", .1f));
        context.register(SCORCH, new DamageType("scorch", .1f));
    }
}
