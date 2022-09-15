package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class SpellItem extends Item {
    public final int spellSlotAmount = 1;
    public SpellSlot[] spellSlots = new SpellSlot[spellSlotAmount];
    private int activeSpell = 0;

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
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
    public int getSpellSlotAmount() { return this.spellSlotAmount;}
    public abstract int getActiveSpell();

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int p_41415_) {
        Spell spell = this.spellSlots[this.getActiveSpell()].getSpell();
        if (spell.TYPE == Spells.RELEASE) {
            spell.execute(user, stack);
        }

    }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        super.onUsingTick(stack, player, count);
    }
}
