package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.kap_lib.util.ExtraRarities;
import net.kapitencraft.mysticcraft.item.capability.spell.SpellCapabilityProvider;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.registry.Spells;
import net.minecraft.network.chat.Component;

import java.util.List;

public class InfernalScythe extends NormalSpellItem implements IDamageSpellItem, IFireScytheItem {
    public InfernalScythe() {
        super(new Properties().rarity(ExtraRarities.LEGENDARY), 350, 69);
    }

    @Override
    public List<Component> getItemDescription() {
        return null;
    }

    @Override
    public List<Component> getPostDescription() {
        return null;
    }

    @Override
    public TabGroup getGroup() {
        return FIRE_SCYTHE_GROUP;
    }

    @Override
    public SpellCapabilityProvider createSpells() {
        return SpellCapabilityProvider.with(Spells.FIRE_BOLT_4);
    }
}
