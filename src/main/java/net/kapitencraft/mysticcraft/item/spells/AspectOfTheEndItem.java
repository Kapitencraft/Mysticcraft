package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Rarity;

import java.util.List;

public class AspectOfTheEndItem extends NormalSpellItem {
    public AspectOfTheEndItem() {
        super(new Properties().rarity(Rarity.UNCOMMON), 1, 40, 0);
        this.addSlot(new SpellSlot(Spells.INSTANT_TRANSMISSION.getSpell()));
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
