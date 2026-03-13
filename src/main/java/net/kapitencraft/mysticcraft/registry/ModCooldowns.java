package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.kap_lib.cooldown.Cooldown;
import net.kapitencraft.kap_lib.cooldown.registry.CooldownRegistries;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.spell.spells.WitherShieldSpell;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface ModCooldowns {
    DeferredRegister<Cooldown> REGISTRY = MysticcraftMod.registry(CooldownRegistries.Keys.COOLDOWNS);

    Holder<Cooldown> WITHER_SHIELD = REGISTRY.register("wither_shield", () -> new Cooldown(100, living -> {
        CompoundTag tag = living.getPersistentData();
        float absorption = tag.getFloat(WitherShieldSpell.ABSORPTION_AMOUNT_ID);
        living.heal(absorption / 2);
        living.setAbsorptionAmount(living.getAbsorptionAmount() - absorption);
        tag.remove(WitherShieldSpell.ABSORPTION_AMOUNT_ID);
    }));
    Holder<Cooldown> EXPLOSIVE_SIGHT = REGISTRY.register("explosive_sight", () -> new Cooldown(600, living -> {}));
    Holder<Cooldown> IMPLOSION = REGISTRY.register("implosion", () -> new Cooldown(100, living -> {}));
    Holder<Cooldown> HUGH_HEAL = REGISTRY.register("hugh_heal", () -> new Cooldown(140, living -> {}));
    Holder<Cooldown> FIRE_BOLT = REGISTRY.register("fire_bolt", () -> new Cooldown(20, living -> {}));
    Holder<Cooldown> SHADOW_STEP = REGISTRY.register("shadow_step", () -> new Cooldown(300, living -> {}));
}
