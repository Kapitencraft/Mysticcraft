package net.kapitencraft.mysticcraft.client.model;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModPoses {

    public static HumanoidModel.ArmPose CAST_SPELL;


    @SubscribeEvent
    public static void registerArmPoses(FMLClientSetupEvent event) {
        event.enqueueWork(()-> {
            CAST_SPELL = HumanoidModel.ArmPose.create("CAST_SPELL", false, (model, entity, arm) -> {
                if (arm == HumanoidArm.LEFT) {
                    model.leftArm.xRot = (-(float) Math.PI / 2);
                } else {
                    model.rightArm.xRot = (-(float) Math.PI / 2);
                }
            });
        });
    }
}
