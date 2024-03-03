package net.kapitencraft.mysticcraft.item.item_bonus;

import net.kapitencraft.mysticcraft.helpers.CollectorHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IArmorBonusItem extends IItemBonusItem {

    List<MultiPieceBonus> getPieceBonni();

    PieceBonus getPieceBonusForSlot(EquipmentSlot slot);

    @Nullable ExtraBonus getExtraBonus(EquipmentSlot slot);

    @Override
    @Nullable default ExtraBonus getExtraBonus() {
        return null;
    }

    @Override
    @Nullable default PieceBonus getBonus() {
        return null;
    }

    @Override
    default void addDisplay(List<Component> toolTip, EquipmentSlot slot) {
        toolTip.addAll(this.getPieceBonni().stream().map(Bonus::makeDisplay).collect(CollectorHelper.merge()).toList());
        toolTip.addAll(MiscHelper.ifNonNullOrDefault(this.getPieceBonusForSlot(slot), Bonus::makeDisplay, List::of));
        toolTip.addAll(MiscHelper.ifNonNullOrDefault(this.getExtraBonus(slot), Bonus::makeDisplay, List::of));
    }
}