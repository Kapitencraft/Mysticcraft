package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistries;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistryKeys;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.spells.*;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;

public interface Spells {
    DeferredRegister<Spell> REGISTRY = MysticcraftMod.registry(ModRegistryKeys.SPELLS);

    RegistryObject<EmptySpell> EMPTY = REGISTRY.register("empty", EmptySpell::new);
    RegistryObject<WitherImpactSpell> WITHER_IMPACT = REGISTRY.register("wither_impact", WitherImpactSpell::new);
    RegistryObject<WitherShieldSpell> WITHER_SHIELD = REGISTRY.register("wither_shield", WitherShieldSpell::new);
    RegistryObject<ImplosionSpell> IMPLOSION = REGISTRY.register("implosion", ImplosionSpell::new);
    RegistryObject<InstantTransmissionSpell> INSTANT_TRANSMISSION = REGISTRY.register("instant_transmission", InstantTransmissionSpell::new);
    RegistryObject<EtherWarpSpell> ETHER_WARP = REGISTRY.register("ether_warp", EtherWarpSpell::new);
    RegistryObject<ExplosiveSightSpell> EXPLOSIVE_SIGHT = REGISTRY.register("explosive_sight", ExplosiveSightSpell::new);
    RegistryObject<ShadowStepSpell> SHADOW_STEP = REGISTRY.register("shadow_step", ShadowStepSpell::new);
    RegistryObject<HugeHealSpell> HUGE_HEAL = REGISTRY.register("huge_heal", HugeHealSpell::new);
    RegistryObject<FireBoltSpell> FIRE_BOLT_1 = REGISTRY.register("fire_bolt_1", () -> new FireBoltSpell(false, 1));
    RegistryObject<FireBoltSpell> FIRE_BOLT_2 = REGISTRY.register("fire_bolt_2", () -> new FireBoltSpell(false, 1.4f));
    RegistryObject<FireBoltSpell> FIRE_BOLT_3 = REGISTRY.register("fire_bolt_3", () -> new FireBoltSpell(true, 2.8f));
    RegistryObject<FireBoltSpell> FIRE_BOLT_4 = REGISTRY.register("fire_bolt_4", () -> new FireBoltSpell(true, 5.2f));
    RegistryObject<FireLanceSpell> FIRE_LANCE = REGISTRY.register("fire_lance", FireLanceSpell::new);
    RegistryObject<CureVillagerSpell> CURE_VILLAGER = REGISTRY.register("cure_villager", CureVillagerSpell::new);

    static Collection<ItemStack> createForCreativeModeTab() {
        return ModRegistries.SPELLS.getValues().stream().filter(s -> s != EMPTY.get()).map(spell -> {
            ItemStack stack = new ItemStack(ModItems.SPELL_SCROLL.get());
            SpellHelper.setSpell(stack, 0, spell);
            return stack;
        }).toList();
    }
}
