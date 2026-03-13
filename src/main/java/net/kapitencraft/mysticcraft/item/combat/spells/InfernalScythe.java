package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.kap_lib.core.helpers.MiscHelper;
import net.kapitencraft.kap_lib.core.util.ExtraRarities;
import net.kapitencraft.mysticcraft.capability.spell.ItemSpells;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.kapitencraft.mysticcraft.registry.Spells;
import net.kapitencraft.mysticcraft.spell.SpellSlot;

import java.util.List;

public class InfernalScythe extends NormalSpellItem implements IDamageSpellItem, IFireScytheItem {
    public InfernalScythe() {
        super(MiscHelper.rarity(ExtraRarities.LEGENDARY).component(ModDataComponentTypes.ITEM_SPELLS, new ItemSpells(
                List.of(
                        new SpellSlot(Spells.FIRE_BOLT, 15)
                )
        )).attributes(createAttributes(350, 69)));
    }
}
