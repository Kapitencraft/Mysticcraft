package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.entity.FrozenBlazeEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntityTypes {
    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MysticcraftMod.MOD_ID);

    public static final RegistryObject<EntityType<FrozenBlazeEntity>> FROZEN_BLAZE = REGISTRY.register("frozen_blaze", ()-> EntityType.Builder.of(FrozenBlazeEntity::new, MobCategory.MONSTER).sized(0.6f, 1.8f).build(new ResourceLocation(MysticcraftMod.MOD_ID, "frozen_blaze").toString()));
}
