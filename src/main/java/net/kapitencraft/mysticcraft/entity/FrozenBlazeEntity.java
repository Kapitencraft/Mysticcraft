package net.kapitencraft.mysticcraft.entity;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

public class FrozenBlazeEntity extends Blaze {
    public FrozenBlazeEntity(EntityType<FrozenBlazeEntity> entityType, Level p_33003_) {
        super(entityType, p_33003_);
        this.xpReward = 15;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.ATTACK_DAMAGE, 6.0D).add(Attributes.MOVEMENT_SPEED, 0.23).add(Attributes.FOLLOW_RANGE, 48.0D).add(ModAttributes.FEROCITY.get(), 60);
    }
}
