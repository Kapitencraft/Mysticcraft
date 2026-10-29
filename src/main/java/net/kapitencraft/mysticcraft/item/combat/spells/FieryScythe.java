package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.mysticcraft.capability.spell.ItemSpells;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.kapitencraft.mysticcraft.registry.Spells;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.minecraft.world.item.Rarity;

import java.util.List;

public class FieryScythe extends NormalSpellItem implements IDamageSpellItem, IFireScytheItem {
    public FieryScythe(Properties properties) {
        super(properties.rarity(Rarity.RARE).fireResistant().component(ModDataComponentTypes.ITEM_SPELLS, new ItemSpells(
                List.of(
                        new SpellSlot(Spells.FIRE_BOLT, 4)
                )
        )).attributes(createAttributes(50, 10)));
    }
}
