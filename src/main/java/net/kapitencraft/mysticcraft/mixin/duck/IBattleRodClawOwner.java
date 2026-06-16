package net.kapitencraft.mysticcraft.mixin.duck;

import net.kapitencraft.mysticcraft.entity.BattleRodClawEntity;
import org.jetbrains.annotations.Nullable;

public interface IBattleRodClawOwner {

    @Nullable BattleRodClawEntity getClaw();

    void setClaw(BattleRodClawEntity entity);
}
