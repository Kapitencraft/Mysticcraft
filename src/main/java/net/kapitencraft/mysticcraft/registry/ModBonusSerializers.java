package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.kap_lib.io.serialization.DataPackSerializer;
import net.kapitencraft.kap_lib.item.bonus.Bonus;
import net.kapitencraft.kap_lib.registry.custom.core.ExtraRegistries;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.bonus.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public interface ModBonusSerializers {
    DeferredRegister<DataPackSerializer<? extends Bonus<?>>> REGISTRY = MysticcraftMod.registry(ExtraRegistries.Keys.BONUS_SERIALIZERS);

    RegistryObject<DataPackSerializer<FreezingAuraBonus>> FREEZING_AURA = REGISTRY.register("frozen_blaze", () -> FreezingAuraBonus.SERIALIZER);
    //RegistryObject<DataPackSerializer<CrimsonArmorFullSetBonus>> CRIMSON_ARMOR = REGISTRY.register("crimson_armor", () -> );
    RegistryObject<DataPackSerializer<SoulMageArmorBonus>> SOUL_MAGE_ARMOR = REGISTRY.register("soul_mage_armor", () -> SoulMageArmorBonus.SERIALIZER);
    RegistryObject<DataPackSerializer<MagicConversionBonus>> MAGIC_CONVERSION = REGISTRY.register("soul_mage_armor_helmet", () -> MagicConversionBonus.SERIALIZER);
    RegistryObject<DataPackSerializer<AssassinBonus>> ASSASSIN = REGISTRY.register("assassin", () -> AssassinBonus.SERIALIZER);
    RegistryObject<DataPackSerializer<SacredBonus>> SACRED = REGISTRY.register("sacred", () -> SacredBonus.SERIALIZER);
    RegistryObject<DataPackSerializer<DominusBonus>> DOMINUS = REGISTRY.register("dominus", () -> DominusBonus.SERIALIZER);
    RegistryObject<DataPackSerializer<HydraBonus>> HYDRA = REGISTRY.register("hydra", () -> HydraBonus.SERIALIZER);
}
