package net.kapitencraft.mysticcraft.item.combat.armor.client.model;// Made with Blockbench 5.2.2
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

import net.kapitencraft.kap_lib.item.combat.armor.client.ArmorModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

import javax.print.attribute.standard.PDLOverrideSupported;

public class DruidsBagModel extends ArmorModel {

    public DruidsBagModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

        PartDefinition armorHead = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, 2.0F, 0.0F));

        PartDefinition armorBody = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(24, 8).addBox(4.0F, 10.0F, -4.0F, 3.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = armorBody.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(24, 0).addBox(-10.0F, 0.0F, -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 7.0F, 0.0F, 0.0F, 0.0F, 0.9163F));

        PartDefinition cube_r2 = armorBody.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(16, 32).addBox(-1.0F, -10.0F, -3.0F, 2.0F, 16.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(22, 39).addBox(-1.0F, -10.0F, 2.0F, 2.0F, 15.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 7.0F, 0.0F, 0.0F, 0.0F, -0.7418F));

        PartDefinition armorLeftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));

        PartDefinition armorRightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));

        PartDefinition armorLeftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(2.0F, 12.0F, 0.0F));

        PartDefinition armorRightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-2.0F, 12.0F, 0.0F));

        PartDefinition armorLeftBoot = partdefinition.addOrReplaceChild("left_boot", CubeListBuilder.create(), PartPose.offset(2.0F, 12.0F, 0.0F));

        PartDefinition armorRightBoot = partdefinition.addOrReplaceChild("right_boot", CubeListBuilder.create(), PartPose.offset(-2.0F, 12.0F, 19.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }
}