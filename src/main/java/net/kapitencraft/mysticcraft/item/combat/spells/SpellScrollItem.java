package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.mysticcraft.capability.spell.ItemSpells;
import net.kapitencraft.mysticcraft.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

import java.util.List;

public class SpellScrollItem extends Item {
    public SpellScrollItem() {
        super(new Properties().rarity(Rarity.RARE).component(ModDataComponentTypes.ITEM_SPELLS, new ItemSpells(List.of(SpellSlot.EMPTY))));
    }

    public static SpellSlot getSpell(ItemStack stack) {
        ItemSpells spells = SpellHelper.getSpells(stack);
        return spells.getFirstEmpty() != 0 ? spells.getSlot(0) : null;
    }
}
