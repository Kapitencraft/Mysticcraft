package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.entity.*;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.firework.NapalmRocketEntity;
import net.kapitencraft.mysticcraft.misc.function.Provider;
import net.kapitencraft.mysticcraft.spell.spells.FireBoltProjectile;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MysticcraftMod.MOD_ID);

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String name, EntityType.EntityFactory<T> factory, MobCategory category, Provider<EntityType.Builder<T>, EntityType.Builder<T>> provider) {
        return REGISTRY.register(name, ()-> provider.provide(EntityType.Builder.of(factory, category)).build(new ResourceLocation(MysticcraftMod.MOD_ID, name).toString()));
    }

    public static final RegistryObject<EntityType<FrozenBlazeEntity>> FROZEN_BLAZE = register("frozen_blaze", FrozenBlazeEntity::new, MobCategory.MONSTER, (builder) -> builder.fireImmune().sized(0.6f, 1.8f));
    public static final RegistryObject<EntityType<WithermancerLordEntity>> WITHERMANCER_LORD = register("withermancer_lord", WithermancerLordEntity::new, MobCategory.MONSTER, (builder) -> builder.sized(0.6f, 1.95f));
    public static final RegistryObject<EntityType<FireBoltProjectile>> FIRE_BOLD = register("fire_bolt", FireBoltProjectile::new, MobCategory.MISC, (builder) -> builder.sized(0.5F, 0.5F));
    public static final RegistryObject<EntityType<CrimsonDeathRayProjectile>> CRIMSON_DEATH_RAY = register("crimson_death_ray", CrimsonDeathRayProjectile::new, MobCategory.MISC, (builder) -> builder.sized(0.5f, 0.5f));
    public static final RegistryObject<EntityType<NapalmRocketEntity>> NAPALM_ROCKET = register("napalm_rocket", NapalmRocketEntity::new, MobCategory.MISC, (builder) -> builder.sized(0.25F, 0.25F));
    public static final RegistryObject<EntityType<LavaFishingHook>> LAVA_FISHING_HOOK = register("lava_fishing_hook", LavaFishingHook::new, MobCategory.MISC, (builder) -> builder.sized(0.25f, 0.25f));
    public static final RegistryObject<EntityType<DamageIndicator>> DAMAGE_INDICATOR = register("damage_indicator", DamageIndicator::new, MobCategory.MISC, (builder) -> builder.sized(0f ,0f));
    public static final RegistryObject<EntityType<SchnauzenPluesch>> SCHNAUZEN_PLUESCH = register("schnauzen_pluesch", SchnauzenPluesch::new, MobCategory.AMBIENT, (builder) -> builder.sized(1.5f, 0.5f));
    public static final RegistryObject<EntityType<SkeletonMaster>> SKELETON_MASTER = register("skeleton_master", SkeletonMaster::new, MobCategory.MONSTER, (builder) -> builder.sized(0.6F, 1.99F));
}