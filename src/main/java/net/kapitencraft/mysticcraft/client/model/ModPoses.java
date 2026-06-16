package net.kapitencraft.mysticcraft.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ModPoses {

    public static HumanoidModel.ArmPose CAST_SPELL = HumanoidModel.ArmPose.valueOf("mysticcraft$CAST_SPELL");
}
