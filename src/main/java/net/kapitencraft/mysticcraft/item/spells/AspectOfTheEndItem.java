package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.spell.Spells;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Rarity;

import java.util.List;

public class AspectOfTheEndItem extends NormalSpellItem {
    public AspectOfTheEndItem(int intel) {
        super(new Properties().rarity(Rarity.UNCOMMON), 1, intel, 0);
        this.addSlot(new SpellSlot(Spells.INSTANT_TRANSMISSION));
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
