package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.capability.spell.ISpellItem;
import net.kapitencraft.mysticcraft.item.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.lance.LanceItem;
import net.kapitencraft.mysticcraft.item.misc.ModTiers;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.world.item.Rarity;

public class FireLance extends LanceItem implements ISpellItem {

    public FireLance() {
        super(ModTiers.SPELL_TIER, 7, MiscHelper.rarity(Rarity.RARE));
    }

    @Override
    public void generateSlots(SpellHelper stack) {
        stack.setSlot(0, new SpellSlot(Spells.FIRE_LANCE));
    }

    @Override
    public int getSlotAmount() {
        return 1;
    }

    @Override
    public double getStrenght() {
        return 0;
    }

    @Override
    public double getCritDamage() {
        return 20;
    }

    @Override
    public TabGroup getGroup() {
        return null;
    }
}
