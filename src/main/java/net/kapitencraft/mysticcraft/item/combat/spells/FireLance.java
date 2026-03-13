package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.kap_lib.core.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.capability.spell.ItemSpells;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.lance.LanceItem;
import net.kapitencraft.mysticcraft.item.misc.ModTiers;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.kapitencraft.mysticcraft.registry.Spells;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.minecraft.world.item.Rarity;

import java.util.List;

public class FireLance extends LanceItem {

    public FireLance() {
        super(ModTiers.SPELL_TIER, MiscHelper.rarity(Rarity.RARE).component(ModDataComponentTypes.ITEM_SPELLS, new ItemSpells(
                List.of(
                        new SpellSlot(Spells.FIRE_LANCE, 5)
                )
        )).attributes(createAttributes(ModTiers.SPELL_TIER, 7, -2.2f, 0, 20)));
    }
}