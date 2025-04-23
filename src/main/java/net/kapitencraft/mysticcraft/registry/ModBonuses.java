package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.kap_lib.io.serialization.DataPackSerializer;
import net.kapitencraft.kap_lib.item.bonus.Bonus;
import net.kapitencraft.kap_lib.registry.custom.core.ExtraRegistries;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.bonus.FreezingAuraBonus;
import net.kapitencraft.mysticcraft.item.bonus.MagicConversionBonus;
import net.kapitencraft.mysticcraft.item.bonus.SoulMageArmorBonus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public interface ModBonuses {
    DeferredRegister<DataPackSerializer<? extends Bonus<?>>> REGISTRY = MysticcraftMod.registry(ExtraRegistries.Keys.SET_BONUSES);

    RegistryObject<DataPackSerializer<FreezingAuraBonus>> FREEZING_AURA = REGISTRY.register("frozen_blaze", () -> FreezingAuraBonus.SERIALIZER);
    //RegistryObject<DataPackSerializer<CrimsonArmorFullSetBonus>> CRIMSON_ARMOR = REGISTRY.register("crimson_armor", () -> );
    RegistryObject<DataPackSerializer<SoulMageArmorBonus>> SOUL_MAGE_ARMOR = REGISTRY.register("soul_mage_armor", () -> SoulMageArmorBonus.SERIALIZER);
    RegistryObject<DataPackSerializer<MagicConversionBonus>> MAGIC_CONVERSION = REGISTRY.register("soul_mage_armor_helmet", () -> MagicConversionBonus.SERIALIZER);
}
