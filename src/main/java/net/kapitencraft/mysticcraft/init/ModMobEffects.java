package net.kapitencraft.mysticcraft.init;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.mob_effects.StunMobEffect;
import net.kapitencraft.mysticcraft.mob_effects.VulnerabilityMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMobEffects {
    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MysticcraftMod.MOD_ID);
    public static final RegistryObject<MobEffect> VULNERABILITY = REGISTRY.register("vulnerability", VulnerabilityMobEffect::new);
    public static final RegistryObject<MobEffect> STUN = REGISTRY.register("stun", StunMobEffect::new);
}
