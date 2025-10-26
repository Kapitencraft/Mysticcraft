package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.capability.spell.ItemSpells;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.kapitencraft.mysticcraft.registry.Spells;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.minecraft.world.item.Rarity;

import java.util.List;

public class AspectOfTheEndItem extends NormalSpellItem {
    public AspectOfTheEndItem(int maxMana) {
        super(MiscHelper.rarity(Rarity.UNCOMMON).component(ModDataComponentTypes.ITEM_SPELLS, new ItemSpells(
                List.of(
                        new SpellSlot(Spells.INSTANT_TRANSMISSION, 4)
                )
        )).attributes(createAttributes(maxMana, 0)));
    }
}
