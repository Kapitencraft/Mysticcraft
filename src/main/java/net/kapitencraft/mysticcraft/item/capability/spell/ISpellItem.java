package net.kapitencraft.mysticcraft.item.capability.spell;

import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.kapitencraft.mysticcraft.spell.spells.Spell;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public interface ISpellItem {

    private Item self() {
        return (Item) this;
    }

    default SpellHelper getSpells(ItemStack stack) {
        SpellHelper helper = new SpellHelper(getSlotAmount(), self());
        helper.loadData(stack, this::generateSlots);
        return helper;
    }

    default void setSlot(int slotId, SpellSlot slot, ItemStack stack) {
        SpellHelper helper = getSpells(stack);
        helper.setSlot(slotId, slot);
        helper.save(stack);
    }

    default int getIndexForSlot(ItemStack stack, Spells spells) {
        return getSpells(stack).getIndexForSlot(spells);
    }

    default void removeSlot(ItemStack stack, int slotId) {
        SpellHelper helper = getSpells(stack);
        helper.removeSlot(slotId);
        helper.save(stack);
    }


    default boolean hasAnySpell(ItemStack stack) {
        return getSpells(stack).getFirstEmptySpellSlot() != 0;
    }

    default boolean hasSpell(ItemStack stack, Spell spell) {
        return getSpells(stack).hasSpell(spell);
    }

    default int getItemUseDuration(ItemStack stack) {
        SpellHelper helper = getSpells(stack);
        if (helper.getType() != null) {
            Spells.Type type = helper.getType();
            return type == Spells.Type.RELEASE ? 2 : Integer.MAX_VALUE;
        }
        return -1;
    }

    default void executeSpell(String s, ItemStack stack, LivingEntity living) {
        getSpells(stack).executeSpell(s, stack, living);
    }

    default boolean handleMana(LivingEntity user, Spell spell, ItemStack stack) {
        return getSpells(stack).handleManaAndExecute(user, spell, stack);
    }

    default boolean handleActiveMana(LivingEntity user, ItemStack stack) {
        return handleMana(user, ((ISpellItem) stack.getItem()).getActiveSpell(stack), stack);
    }

    default Spell getActiveSpell(ItemStack stack) {
        return getSpells(stack).getActiveSpell();
    }

    @Deprecated
    default Spell getClosestSpell(String spell_exe) {
        return Spells.EMPTY_SPELL;
    }

    default void appendDisplay(List<Component> list, ItemStack stack, Player player) {
        getSpells(stack).appendDisplay(list, stack, player, this);
    }


    int getSlotAmount();

    void generateSlots(SpellHelper stack);
}
