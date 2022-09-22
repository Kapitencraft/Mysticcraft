package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public interface ISpellItem {
    List<Component> getItemDescription();
    List<Component> getPostDescription();
    SpellSlot[] getSpellSlots();
    int getActiveSpell();
    int getSpellSlotAmount();

    public default void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag flag) {
        list.addAll(this.getItemDescription());
        list.add(Component.Serializer.fromJson(""));
        list.addAll(this.getSpellSlots()[this.getActiveSpell()].getSpell().getDescription());
        list.add(Component.Serializer.fromJson(""));
        list.addAll(this.getPostDescription());
    }

}
