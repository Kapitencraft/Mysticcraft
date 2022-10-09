package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.item.gemstone_slot.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.gemstone_slot.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class SpellItem extends Item {
    @Override
    public void appendHoverText(@Nonnull ItemStack itemStack, @Nullable Level level, @Nonnull List<Component> list, @Nonnull TooltipFlag flag) {
        @Nullable SpellSlot activeSpellSlot = this.getSpellSlots()[this.getActiveSpell()];
        Spell spell;
        if (activeSpellSlot == null) {
            spell = Spells.EMPTY_SPELL;
        } else {
            spell = activeSpellSlot.getSpell();
        }
        StringBuilder gemstoneText = new StringBuilder();
        if (itemStack.getItem() instanceof IGemstoneApplicable gemstoneApplicable) {
            for (@Nullable GemstoneSlot slot : gemstoneApplicable.getGemstoneSlots()) {
                if (slot != null) {
                    gemstoneText.append(slot.getDisplay());
                }
            }
        }
        if (!gemstoneText.toString().equals("")) {
            list.add(Component.literal(gemstoneText.toString()));
        }
        list.addAll(this.getItemDescription());
        list.add(Component.literal(""));
        list.addAll(spell.createDescription(this));
        list.add(Component.literal(""));
            if (this.getPostDescription() != null) {
            list.addAll(this.getPostDescription());
        }
        if (flag.isAdvanced() && !gemstoneText.toString().equals("")) {
            list.add(Component.literal("Gemstone Modifications:").withStyle(ChatFormatting.GREEN));

        }
    }

    public SpellItem(Properties p_41383_) {
        super(p_41383_.stacksTo(1));
    }

    public abstract List<Component> getItemDescription();
    public abstract List<Component> getPostDescription();
    public abstract SpellSlot[] getSpellSlots();
    public abstract int getSpellSlotAmount();
    public abstract int getActiveSpell();

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @Nullable Level level, @NotNull LivingEntity user, int p_41415_) {
        Spell spell = this.getSpellSlots()[this.getActiveSpell()].getSpell();
        if (spell.TYPE == Spells.RELEASE) {
            spell.execute(user, stack);
        }

    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, @Nullable int count) {
        Spell spell = this.getSpellSlots()[this.getActiveSpell()].getSpell();
        if (spell.TYPE == Spells.CYCLE) {
            spell.execute(player, stack);
        }
    }
}
