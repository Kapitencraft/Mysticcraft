package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
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

    @Override
    public TabGroup getGroup() {
        return null;
    }
}
