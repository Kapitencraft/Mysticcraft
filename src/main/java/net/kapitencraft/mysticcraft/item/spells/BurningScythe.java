package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Rarity;

import java.util.List;

public class BurningScythe extends NormalSpellItem implements IDamageSpellItem, IFireScytheItem {
    public BurningScythe() {
        super(new Properties().fireResistant().rarity(Rarity.EPIC), 1, 250, 50);
        this.addSlot(new SpellSlot(Spell.FIRE_BOLT_3));
    }

    @Override
    public List<Component> getItemDescription() {
        return null;
    }

    @Override
    public List<Component> getPostDescription() {
        return null;
    }
}
