package net.kapitencraft.mysticcraft.capability.spell;

import com.mojang.serialization.Codec;
import net.kapitencraft.kap_lib.item.capability.CapabilityProvider;
import net.kapitencraft.mysticcraft.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellSlot;

import java.util.List;
import java.util.function.Supplier;

public class    SpellCapabilityProvider extends CapabilityProvider<List<SpellSlot>, SpellCapability> {
    private static final Codec<List<SpellSlot>> DATA_CODEC = SpellSlot.CODEC.listOf();

    public SpellCapabilityProvider(SpellCapability object) {
        super(object, DATA_CODEC, CapabilityHelper.SPELL);
    }

    public static SpellCapabilityProvider with(Supplier<? extends Spell> slot) {
        SpellCapability capability = new SpellCapability();
        capability.setSlot(0, new SpellSlot(slot));
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
