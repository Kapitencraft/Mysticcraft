package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.item.gemstone_slot.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.gemstone_slot.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
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
    public final int spellSlotAmount = 1;
    public SpellSlot[] spellSlots = new SpellSlot[spellSlotAmount];
    private final int activeSpell = 0;

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, @Nonnull List<Component> list, @Nullable TooltipFlag flag) {
        StringBuilder gemstoneText = new StringBuilder();
        if (itemStack.getItem() instanceof IGemstoneApplicable) {
            for (GemstoneSlot slot : ((IGemstoneApplicable) itemStack.getItem()).getGemstoneSlots()) {
                gemstoneText.append(slot.getDisplay());
            }
        }
        if (!gemstoneText.toString().equals("")) {
            list.add(Component.Serializer.fromJson(gemstoneText.toString()));
        }
        list.addAll(this.getItemDescription());
        list.add(Component.Serializer.fromJson(""));
        list.addAll(this.spellSlots[this.getActiveSpell()].getSpell().getDescription());
        list.add(Component.Serializer.fromJson(""));
        list.addAll(this.getPostDescription());
    }

    public SpellItem(Properties p_41383_) {
        super(p_41383_);
    }

    public abstract List<Component> getItemDescription();
    public abstract List<Component> getPostDescription();
    public abstract SpellSlot[] getSpellSlots();
    public abstract int getSpellSlotAmount();
    public abstract int getActiveSpell();

    @Override
    public void releaseUsing(@NotNull ItemStack stack, @Nullable Level level, @NotNull LivingEntity user, int p_41415_) {
        Spell spell = this.spellSlots[this.getActiveSpell()].getSpell();
        if (spell.TYPE == Spells.RELEASE) {
            spell.execute(user, stack);
        }

    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        Spell spell = this.spellSlots[this.getActiveSpell()].getSpell();
        if (spell.TYPE == Spells.CYCLE) {
            spell.execute(player, stack);
        }
    }
}
