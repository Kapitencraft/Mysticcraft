package net.kapitencraft.mysticcraft.item.item_bonus;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;

import javax.annotation.Nullable;
import java.util.List;

public interface IArmorBonusItem {

    FullSetBonus getFullSetBonus();

    PieceBonus getPieceBonusForSlot(EquipmentSlot slot);

    @Nullable ExtraBonus getExtraBonus(EquipmentSlot slot);

    default void addDisplay(List<Component> toolTip, EquipmentSlot slot) {
        if (this.getFullSetBonus() != null) {
            toolTip.add(Component.literal("Full Set Bonus: " + this.getFullSetBonus().name).withStyle(ChatFormatting.GOLD).withStyle(ChatFormatting.BOLD));
            toolTip.addAll(this.getFullSetBonus().getDisplay());
            toolTip.add(Component.literal(""));
        }
        if (this.getPieceBonusForSlot(slot) != null) {
            toolTip.addAll(this.getPieceBonusForSlot(slot).getDisplay());
        }
        if (this.getExtraBonus(slot) != null) {
            toolTip.addAll(this.getExtraBonus(slot).getDisplay());
        }
    }
}