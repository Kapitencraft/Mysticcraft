package net.kapitencraft.mysticcraft.capability.spell;

import net.kapitencraft.kap_lib.item.capability.CapabilityProvider;
import net.kapitencraft.mysticcraft.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellSlot;

import java.util.List;
import java.util.function.Supplier;

public class SpellCapabilityProvider extends CapabilityProvider<List<SpellSlot>, SpellCapability> {

    public SpellCapabilityProvider(SpellCapability object) {
        super(object, SpellSlot.LIST_CODEC, CapabilityHelper.SPELL);
    }

    public static SpellCapabilityProvider with(Supplier<? extends Spell> slot) {
        SpellCapability capability = new SpellCapability();
        capability.setSlot(0, new SpellSlot(slot));
        return new SpellCapabilityProvider(capability);
    }

    public static SpellCapabilityProvider with(Supplier<? extends Spell> spell, int level) {
        SpellCapability capability = new SpellCapability();
        capability.setSlot(0, new SpellSlot(spell, level));
        return new SpellCapabilityProvider(capability);
    }

    public static SpellCapabilityProvider empty() {
        return new SpellCapabilityProvider(new SpellCapability());
    }

    @Override
    protected List<SpellSlot> fallback() {
        return List.of();
    }
}
