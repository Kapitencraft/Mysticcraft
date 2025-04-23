package net.kapitencraft.mysticcraft.entity;

import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class FrozenBlazeEntity extends Blaze {
    public FrozenBlazeEntity(EntityType<FrozenBlazeEntity> entityType, Level p_33003_) {
        super(entityType, p_33003_);
        this.xpReward = 15;
    }


    public static boolean checkFrozenBlazeSpawnRules(EntityType<FrozenBlazeEntity> type, LevelAccessor accessor, MobSpawnType type1, BlockPos pos, RandomSource source) {
        return accessor.getDifficulty() != Difficulty.PEACEFUL && checkMobSpawnRules(type, accessor, type1, pos, source);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23)
                .add(Attributes.FOLLOW_RANGE, 48.0D)
                .add(ExtraAttributes.FEROCITY.get(), 60);
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_21434_, DifficultyInstance p_21435_, MobSpawnType p_21436_, @Nullable SpawnGroupData p_21437_, @Nullable CompoundTag p_21438_) {
        return super.finalizeSpawn(p_21434_, p_21435_, p_21436_, p_21437_, p_21438_);
    }
}
