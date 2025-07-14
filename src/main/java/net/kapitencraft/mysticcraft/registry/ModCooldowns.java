package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.kap_lib.cooldown.Cooldown;
import net.kapitencraft.kap_lib.registry.custom.core.ExtraRegistries;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.spell.spells.WitherShieldSpell;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public interface ModCooldowns {
    DeferredRegister<Cooldown> REGISTRY = MysticcraftMod.registry(ExtraRegistries.Keys.COOLDOWNS);

    RegistryObject<Cooldown> WITHER_SHIELD = REGISTRY.register("wither_shield", () -> new Cooldown(100, living -> {
        CompoundTag tag = living.getPersistentData();
        float absorption = tag.getFloat(WitherShieldSpell.ABSORPTION_AMOUNT_ID);
        living.heal(absorption / 2);
        living.setAbsorptionAmount(living.getAbsorptionAmount() - absorption);
        tag.remove(WitherShieldSpell.ABSORPTION_AMOUNT_ID);
    }));
    RegistryObject<Cooldown> EXPLOSIVE_SIGHT = REGISTRY.register("explosive_sight", () -> new Cooldown(600, living -> {}));
    RegistryObject<Cooldown> IMPLOSION = REGISTRY.register("implosion", () -> new Cooldown(100, living -> {}));
    RegistryObject<Cooldown> HUGH_HEAL = REGISTRY.register("hugh_heal", () -> new Cooldown(140, living -> {}));
    RegistryObject<Cooldown> FIRE_BOLT = REGISTRY.register("fire_bolt", () -> new Cooldown(20, living -> {}));
    RegistryObject<Cooldown> SHADOW_STEP = REGISTRY.register("shadow_step", () -> new Cooldown(300, living -> {}));
}
