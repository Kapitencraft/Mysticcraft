package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.entity.*;
import net.kapitencraft.mysticcraft.entity.item.SoulBoundAnimator;
import net.kapitencraft.mysticcraft.entity.skeleton_master.SkeletonMaster;
import net.kapitencraft.mysticcraft.entity.vampire.VampireBat;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.firework.NapalmRocketEntity;
import net.kapitencraft.mysticcraft.spell.spells.FireBoltProjectile;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.UnaryOperator;

public interface ModEntityTypes {
    DeferredRegister<EntityType<?>> REGISTRY = MysticcraftMod.registry(ForgeRegistries.ENTITY_TYPES);

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.EntityFactory<T> factory, MobCategory category, UnaryOperator<EntityType.Builder<T>> provider) {
        return REGISTRY.register(name, ()-> provider.apply(EntityType.Builder.of(factory, category)).build(MysticcraftMod.res(name).toString()));
    }

    RegistryObject<EntityType<FrozenBlazeEntity>> FROZEN_BLAZE = register("frozen_blaze", FrozenBlazeEntity::new, MobCategory.MONSTER, (builder) -> builder.fireImmune().sized(0.6f, 1.8f));
    RegistryObject<EntityType<WithermancerLordEntity>> WITHERMANCER_LORD = register("withermancer_lord", WithermancerLordEntity::new, MobCategory.MONSTER, (builder) -> builder.sized(0.6f, 1.95f));
    RegistryObject<EntityType<FireBoltProjectile>> FIRE_BOLD = register("fire_bolt", FireBoltProjectile::new, MobCategory.MISC, (builder) -> builder.sized(0.5F, 0.5F));
    RegistryObject<EntityType<VampireBat>> VAMPIRE_BAT = register("vampire_bat", VampireBat::new, MobCategory.MONSTER, value -> value.sized(0.5f, 0.9f));
    RegistryObject<EntityType<CrimsonDeathRayProjectile>> CRIMSON_DEATH_RAY = register("crimson_death_ray", CrimsonDeathRayProjectile::new, MobCategory.MISC, (builder) -> builder.sized(0.5f, 0.5f));
    RegistryObject<EntityType<NapalmRocketEntity>> NAPALM_ROCKET = register("napalm_rocket", NapalmRocketEntity::new, MobCategory.MISC, (builder) -> builder.sized(0.25F, 0.25F));
    RegistryObject<EntityType<LavaFishingHook>> LAVA_FISHING_HOOK = register("lava_fishing_hook", LavaFishingHook::new, MobCategory.MISC, (builder) -> builder.sized(0.25f, 0.25f));
    RegistryObject<EntityType<SkeletonMaster>> SKELETON_MASTER = register("skeleton_master", SkeletonMaster::new, MobCategory.MONSTER, (builder) -> builder.sized(0.6F, 1.99F));
    RegistryObject<EntityType<RifleProjectile>> RIFLE_PROJECTILE = register("rifle_projectile", RifleProjectile::new, MobCategory.MISC, value -> value.sized(0.5f, 0.5f));
    RegistryObject<EntityType<SoulBoundAnimator>> SOUL_BOUND_ANIMATOR = register("soul_bound_animator", SoulBoundAnimator::new, MobCategory.MISC, builder -> builder.sized(0.5f, 1.975f).clientTrackingRange(10));
}