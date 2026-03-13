package net.kapitencraft.mysticcraft.potion;

import net.kapitencraft.kap_lib.mob_effect.registry.ExtraMobEffects;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.ModMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ModPotions {
    DeferredRegister<Potion> REGISTRY = MysticcraftMod.registry(Registries.POTION);

    private static Holder<Potion> register(String name, Supplier<Potion> potion) {
        return REGISTRY.register(name, potion);
    }

    Holder<Potion> STUN = register("stun", ()-> new Potion(new MobEffectInstance(ExtraMobEffects.STUN, 600)));
    Holder<Potion> LONG_STUN = register("long_stun", ()-> new Potion(new MobEffectInstance(ExtraMobEffects.STUN, 1200)));
    Holder<Potion> DISPLACEMENT = register("displacement", () -> new Potion(new MobEffectInstance(ModMobEffects.DISPLACEMENT, 90)));
}