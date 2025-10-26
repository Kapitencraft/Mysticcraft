package net.kapitencraft.mysticcraft.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.client.IArmPoseTransformer;

public class ModArmPoseProxies {
    private static final IArmPoseTransformer TRANSFORMER = (model, entity, arm) -> {
        if (arm == HumanoidArm.LEFT) {
            model.leftArm.xRot = (-(float) Math.PI / 2);
        } else {
            model.rightArm.xRot = (-(float) Math.PI / 2);
        }
    };

    public static final EnumProxy<HumanoidModel.ArmPose> CAST_SPELL = new EnumProxy<>(HumanoidModel.ArmPose.class,
            false, TRANSFORMER
    );
}
