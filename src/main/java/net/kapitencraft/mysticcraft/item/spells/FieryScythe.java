package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Rarity;

import java.util.List;

public class FieryScythe extends SpellItem implements IDamageSpellItem {
    public FieryScythe() {
        super(new Properties().rarity(Rarity.RARE).fireResistant(), 1, 50, 10);
        this.addSlot(new SpellSlot(Spells.FIRE_BOLD_2));
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