package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistries;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.spells.*;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Collection;

public interface Spells {
    DeferredRegister<Spell> REGISTRY = MysticcraftMod.registry(ModRegistries.Keys.SPELLS);

    Holder<Spell> EMPTY = REGISTRY.register("empty", EmptySpell::new);
    Holder<Spell> WITHER_IMPACT = REGISTRY.register("wither_impact", WitherImpactSpell::new);
    Holder<Spell> WITHER_SHIELD = REGISTRY.register("wither_shield", WitherShieldSpell::new);
    Holder<Spell> IMPLOSION = REGISTRY.register("implosion", ImplosionSpell::new);
    Holder<Spell> INSTANT_TRANSMISSION = REGISTRY.register("instant_transmission", InstantTransmissionSpell::new);
    Holder<Spell> ETHER_WARP = REGISTRY.register("ether_warp", EtherWarpSpell::new);
    Holder<Spell> EXPLOSIVE_SIGHT = REGISTRY.register("explosive_sight", ExplosiveSightSpell::new);
    Holder<Spell> SHADOW_STEP = REGISTRY.register("shadow_step", ShadowStepSpell::new);
    Holder<Spell> HUGE_HEAL = REGISTRY.register("huge_heal", HugeHealSpell::new);
    Holder<Spell> FIRE_BOLT = REGISTRY.register("fire_bolt", FireBoltSpell::new);
    Holder<Spell> FIRE_LANCE = REGISTRY.register("fire_lance", FireLanceSpell::new);
    Holder<Spell> CURE_VILLAGER = REGISTRY.register("cure_villager", CureVillagerSpell::new);
    Holder<Spell> MAKE_RAIN = REGISTRY.register("make_rain", MakeRainSpell::new);

    static Collection<ItemStack> createForCreativeModeTab() {
        return ModRegistries.SPELLS.holders().filter(s -> s != EMPTY).map(spell -> {
            ItemStack stack = new ItemStack(ModItems.SPELL_SCROLL.get());
            SpellHelper.setSpell(stack, 0, spell);
            return stack;
        }).toList();
    }
}
