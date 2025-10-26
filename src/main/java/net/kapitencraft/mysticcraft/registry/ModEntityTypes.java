package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.entity.*;
import net.kapitencraft.mysticcraft.entity.dragon.Dragon;
import net.kapitencraft.mysticcraft.entity.item.SoulBoundAnimator;
import net.kapitencraft.mysticcraft.entity.vampire.VampireBat;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.firework.NapalmRocketEntity;
import net.kapitencraft.mysticcraft.spell.spells.FireBoltProjectile;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public interface ModEntityTypes {
    DeferredRegister<EntityType<?>> REGISTRY = MysticcraftMod.registry(Registries.ENTITY_TYPE);

    private static <T extends Entity> Supplier<EntityType<T>> register(String name, EntityType.EntityFactory<T> factory, MobCategory category, UnaryOperator<EntityType.Builder<T>> provider) {
        return REGISTRY.register(name, ()-> provider.apply(EntityType.Builder.of(factory, category)).build(MysticcraftMod.res(name).toString()));
    }

    Supplier<EntityType<FrozenBlazeEntity>> FROZEN_BLAZE = register("frozen_blaze", FrozenBlazeEntity::new, MobCategory.MONSTER, (builder) -> builder.fireImmune().sized(0.6f, 1.8f));
    Supplier<EntityType<WithermancerLordEntity>> WITHERMANCER_LORD = register("withermancer_lord", WithermancerLordEntity::new, MobCategory.MONSTER, (builder) -> builder.sized(0.6f, 1.95f));
    Supplier<EntityType<FireBoltProjectile>> FIRE_BOLD = register("fire_bolt", FireBoltProjectile::new, MobCategory.MISC, (builder) -> builder.sized(0.5F, 0.5F));
    Supplier<EntityType<VampireBat>> VAMPIRE_BAT = register("vampire_bat", VampireBat::new, MobCategory.MONSTER, value -> value.sized(0.5f, 0.9f));
    Supplier<EntityType<CrimsonDeathRayProjectile>> CRIMSON_DEATH_RAY = register("crimson_death_ray", CrimsonDeathRayProjectile::new, MobCategory.MISC, (builder) -> builder.sized(0.5f, 0.5f));
    Supplier<EntityType<NapalmRocketEntity>> NAPALM_ROCKET = register("napalm_rocket", NapalmRocketEntity::new, MobCategory.MISC, (builder) -> builder.sized(0.25F, 0.25F));
    Supplier<EntityType<LavaFishingHook>> LAVA_FISHING_HOOK = register("lava_fishing_hook", LavaFishingHook::new, MobCategory.MISC, (builder) -> builder.sized(0.25f, 0.25f));
    Supplier<EntityType<RifleProjectile>> RIFLE_PROJECTILE = register("rifle_projectile", RifleProjectile::new, MobCategory.MISC, value -> value.sized(0.5f, 0.5f));
    Supplier<EntityType<SoulBoundAnimator>> SOUL_BOUND_ANIMATOR = register("soul_bound_animator", SoulBoundAnimator::new, MobCategory.MISC, builder -> builder.sized(0.5f, 1.975f).clientTrackingRange(10));
    Supplier<EntityType<ThrownCursedPearl>> CURSED_PEARL = register("cursed_pearl", ThrownCursedPearl::new, MobCategory.MISC, builder -> builder.sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));
    Supplier<EntityType<Dragon>> DRAGON = register("dragon", Dragon::new, MobCategory.CREATURE, builder -> builder.sized(4, 4));
}