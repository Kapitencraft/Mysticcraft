package net.kapitencraft.mysticcraft.item.combat.weapon.melee.dagger;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.util.ExtraRarities;
import net.kapitencraft.mysticcraft.capability.spell.ItemSpells;
import net.kapitencraft.mysticcraft.item.misc.ModTiers;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.kapitencraft.mysticcraft.registry.Spells;
import net.kapitencraft.mysticcraft.spell.SpellSlot;

import java.util.List;

public class ShadowDagger extends DarkDagger {

    public ShadowDagger() {
        super(MiscHelper.rarity(ExtraRarities.LEGENDARY)
                .component(ModDataComponentTypes.ITEM_SPELLS, new ItemSpells(
                        List.of(
                                new SpellSlot(Spells.SHADOW_STEP)
                        )
                )).attributes(createAttributes(ModTiers.SHADOW_TIER, 3, -1.8f, 35, 100)));
    }
}
