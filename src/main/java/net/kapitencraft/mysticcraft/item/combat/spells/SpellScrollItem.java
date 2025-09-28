package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.mysticcraft.capability.spell.ISpellItem;
import net.kapitencraft.mysticcraft.capability.spell.SpellCapability;
import net.kapitencraft.mysticcraft.capability.spell.SpellCapabilityProvider;
import net.kapitencraft.mysticcraft.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class SpellScrollItem extends Item implements ISpellItem {
    public SpellScrollItem() {
        super(new Properties().rarity(Rarity.RARE));
    }

    public static SpellSlot getSpell(ItemStack stack) {
        SpellCapability capability = SpellHelper.getCapability(stack);
        return capability.getFirstEmpty() != 0 ? capability.getSlot(0) : null;
    }

    @Override
    public SpellCapabilityProvider createSpells() {
        return new SpellCapabilityProvider(new SpellCapability());
    }
}
