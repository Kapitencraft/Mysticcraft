package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.network.chat.Component;

import java.util.List;

public class InfernalScythe extends SpellItem implements IDamageSpellItem {
    public InfernalScythe() {
        super(new Properties().rarity(FormattingCodes.LEGENDARY), 1, 350, 69);
        this.addSlot(new SpellSlot(Spells.FIRE_BOLD_4));
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
