package net.kapitencraft.mysticcraft.capability.spell;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ISpellItem {

    default boolean handleActiveMana(LivingEntity user, ItemStack stack) {
        return SpellHelper.handleManaAndExecute(user, SpellHelper.getActiveSpell(stack), stack);
    }

    SpellCapabilityProvider createSpells();
}
