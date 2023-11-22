package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.mysticcraft.item.data.spell.SpellHelper;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.network.chat.Component;

import java.util.List;

public class InfernalScythe extends NormalSpellItem implements IDamageSpellItem, IFireScytheItem {
    public InfernalScythe() {
        super(new Properties().rarity(FormattingCodes.LEGENDARY), 350, 69);
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
    public int getSlotAmount() {
        return 1;
    }

    @Override
    public void generateSlots(SpellHelper stack) {
        stack.setSlot(0, new SpellSlot(Spells.FIRE_BOLT_4));
    }
}
