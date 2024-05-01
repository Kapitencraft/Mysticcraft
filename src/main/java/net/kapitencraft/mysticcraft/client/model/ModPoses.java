package net.kapitencraft.mysticcraft.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.HumanoidArm;

public interface ModPoses {

    HumanoidModel.ArmPose CAST_SPELL = HumanoidModel.ArmPose.create("CAST_SPELL", false, (model, entity, arm) -> {
        if (arm == HumanoidArm.LEFT) {
            model.leftArm.xRot = (-(float) Math.PI / 2);
        } else {
            model.rightArm.xRot = (-(float) Math.PI / 2);
        }
    });

}
