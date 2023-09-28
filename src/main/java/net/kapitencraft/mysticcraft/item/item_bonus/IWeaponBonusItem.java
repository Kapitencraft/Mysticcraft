package net.kapitencraft.mysticcraft.item.item_bonus;

import org.jetbrains.annotations.Nullable;

public interface IWeaponBonusItem {

    @Nullable
    PieceBonus getBonus();
    @Nullable
    ExtraBonus getExtraBonus();
}
