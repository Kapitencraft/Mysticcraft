package net.kapitencraft.mysticcraft.potion;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModMobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModPotions {
    public static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(ForgeRegistries.POTIONS, MysticcraftMod.MOD_ID);

    private static RegistryObject<Potion> register(String name, Supplier<Potion> potion) {
        return REGISTRY.register(name, potion);
    }

    public static final RegistryObject<Potion> STUN = register("stun", ()-> new Potion(new MobEffectInstance(ModMobEffects.STUN.get(), 600)));
    public static final RegistryObject<Potion> LONG_STUN = register("long_stun", ()-> new Potion(new MobEffectInstance(ModMobEffects.STUN.get(), 1200)));
}