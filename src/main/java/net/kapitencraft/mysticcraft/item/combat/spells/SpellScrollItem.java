package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.mysticcraft.item.capability.spell.ISpellItem;
import net.kapitencraft.mysticcraft.item.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.item.misc.IModItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.spell.spells.Spell;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SpellScrollItem extends Item implements IModItem, ISpellItem {
    public SpellScrollItem() {
        super(new Properties());
    }

    @Override
    public @NotNull Rarity getRarity(@NotNull ItemStack stack) {
        return getSpells(stack).getActiveSpell().getRarity();
    }

    public Spell getSpellForStack(ItemStack stack) {
        return getSpells(stack).getActiveSpell();
    }

    public static Spell getSpell(ItemStack stack) {
        return ((SpellScrollItem) stack.getItem()).getSpellForStack(stack);
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag tooltipFlag) {
        Spell spell = this.getSpellForStack(stack);
        list.add(Component.literal("Spell Ability: " + spell.getName()).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GOLD));
        list.addAll(spell.getDescription());
    }

    @Override
    public TabGroup getGroup() {
        return null;
    }

    @Override
    public int getSlotAmount() {
        return 1;
    }

    @Override
    public void generateSlots(SpellHelper stack) {

    }
}
