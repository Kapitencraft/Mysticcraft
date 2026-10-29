package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.mysticcraft.capability.spell.ItemSpells;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.kapitencraft.mysticcraft.registry.Spells;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.minecraft.world.item.Rarity;

import java.util.List;
import java.util.Properties;

public class TheStaffOfDestruction extends NormalSpellItem {
    public TheStaffOfDestruction(Properties properties) {
        super(properties.rarity(Rarity.RARE).component(ModDataComponentTypes.ITEM_SPELLS, new ItemSpells(List.of(
                new SpellSlot(Spells.EXPLOSIVE_SIGHT, 2)
        ))).attributes(createAttributes(50, 20)));
    }
}