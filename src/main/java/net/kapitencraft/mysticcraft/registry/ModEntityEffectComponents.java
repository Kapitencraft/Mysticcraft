package net.kapitencraft.mysticcraft.registry;

import com.mojang.serialization.MapCodec;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.enchantments.components.ManaSyphon;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public interface ModEntityEffectComponents {
    DeferredRegister<MapCodec<? extends EnchantmentEntityEffect>> REGISTRY = MysticcraftMod.registry(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE);

    Supplier<MapCodec<ManaSyphon>> MANA_SYPHON = REGISTRY.register("mana_syphon", () -> ManaSyphon.CODEC);
}
