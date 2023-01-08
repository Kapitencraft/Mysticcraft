package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.entity.DamageIndicator;
import net.kapitencraft.mysticcraft.entity.FrozenBlazeEntity;
import net.kapitencraft.mysticcraft.entity.SchnauzenPluesch;
import net.kapitencraft.mysticcraft.entity.WithermancerLordEntity;
import net.kapitencraft.mysticcraft.spell.spells.FireBoldProjectile;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MysticcraftMod.MOD_ID);

    public static final RegistryObject<EntityType<FrozenBlazeEntity>> FROZEN_BLAZE = REGISTRY.register("frozen_blaze", ()-> EntityType.Builder.of(FrozenBlazeEntity::new, MobCategory.MONSTER).sized(0.6f, 1.8f).build(new ResourceLocation(MysticcraftMod.MOD_ID, "frozen_blaze").toString()));
    public static final RegistryObject<EntityType<WithermancerLordEntity>> WITHERMANCER_LORD = REGISTRY.register("withermancer_lord", ()-> EntityType.Builder.of(WithermancerLordEntity::new, MobCategory.MONSTER).sized(0.6f, 1.95f).build(new ResourceLocation(MysticcraftMod.MOD_ID, "withermancer_lord").toString()));
    public static final RegistryObject<EntityType<FireBoldProjectile>> FIRE_BOLD = REGISTRY.register("fire_bold", ()-> EntityType.Builder.of(FireBoldProjectile::new, MobCategory.MISC).sized(0.5F, 0.5F).build(new ResourceLocation(MysticcraftMod.MOD_ID, "fire_bold").toString()));
    public static final RegistryObject<EntityType<DamageIndicator>> DAMAGE_INDICATOR = REGISTRY.register("damage_indicator", ()-> EntityType.Builder.of(DamageIndicator::new, MobCategory.MISC).sized(0f ,0f).build(new ResourceLocation(MysticcraftMod.MOD_ID, "indicator").toString()));
    public static final RegistryObject<EntityType<SchnauzenPluesch>> SCHNAUZEN_PLUESCH = REGISTRY.register("schnauzen_pluesch", ()-> EntityType.Builder.of(SchnauzenPluesch::new, MobCategory.AMBIENT).sized(1.5f, 0.5f).build(new ResourceLocation(MysticcraftMod.MOD_ID, "schnauzen_pluesch").toString()));
}