package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.mysticcraft.item.capability.spell.SpellCapabilityProvider;
import net.kapitencraft.mysticcraft.registry.Spells;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Rarity;

import java.util.List;

public class BurningScythe extends NormalSpellItem implements IDamageSpellItem, IFireScytheItem {
    public BurningScythe() {
        super(new Properties().fireResistant().rarity(Rarity.EPIC), 250, 50);
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
    public SpellCapabilityProvider createSpells() {
        return SpellCapabilityProvider.with(Spells.FIRE_BOLT_3);
    }
}
