package net.kapitencraft.mysticcraft.capability.spell;

import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ISpellItem {

    default boolean handleActiveMana(LivingEntity user, ItemStack stack) {
        SpellSlot slot = SpellHelper.getActiveSlot(stack);
        return SpellHelper.handleManaAndExecute(user, slot.getSpell(), slot.getLevel(), stack);
    }

    SpellCapabilityProvider createSpells();
}
