package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.kap_lib.bonus.Bonus;
import net.kapitencraft.kap_lib.bonus.registry.BonusRegistries;
import net.kapitencraft.kap_lib.core.io.serialization.RegistrySerializer;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.bonus.*;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ModBonusSerializers {
    DeferredRegister<RegistrySerializer<? extends Bonus<?>>> REGISTRY = MysticcraftMod.registry(BonusRegistries.Keys.SERIALIZERS);

    Supplier<RegistrySerializer<FreezingAuraBonus>> FREEZING_AURA = REGISTRY.register("frozen_blaze", () -> FreezingAuraBonus.SERIALIZER);
    //Supplier<RegistrySerializer<CrimsonArmorFullSetBonus>> CRIMSON_ARMOR = REGISTRY.register("crimson_armor", () -> );
    Supplier<RegistrySerializer<ManaSyphonBonus>> MANA_SYPHON = REGISTRY.register("mana_syphon", () -> ManaSyphonBonus.SERIALIZER);
    Supplier<RegistrySerializer<AssassinBonus>> ASSASSIN = REGISTRY.register("assassin", () -> AssassinBonus.SERIALIZER);
    Supplier<RegistrySerializer<SacredBonus>> SACRED = REGISTRY.register("sacred", () -> SacredBonus.SERIALIZER);
    Supplier<RegistrySerializer<DominusBonus>> DOMINUS = REGISTRY.register("dominus", () -> DominusBonus.SERIALIZER);
    Supplier<RegistrySerializer<HydraBonus>> HYDRA = REGISTRY.register("hydra", () -> HydraBonus.SERIALIZER);
}
