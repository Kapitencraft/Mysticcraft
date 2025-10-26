package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.capability.spell.ItemSpells;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.kapitencraft.mysticcraft.registry.Spells;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.minecraft.world.item.Rarity;

import java.util.List;

public class BurningScythe extends NormalSpellItem implements IDamageSpellItem, IFireScytheItem {
    public BurningScythe() {
        super(MiscHelper.rarity(Rarity.EPIC).fireResistant().component(ModDataComponentTypes.ITEM_SPELLS, new ItemSpells(
                List.of(
                        new SpellSlot(Spells.FIRE_BOLT, 10)
                )
        )).attributes(createAttributes(250, 50)));
    }
}
