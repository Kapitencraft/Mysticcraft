package net.kapitencraft.mysticcraft.item.combat.armor.client.model;// Made with Blockbench 4.8.3

import net.kapitencraft.kap_lib.item.combat.armor.client.model.ArmorModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class WizardCloakModel extends ArmorModel {
	public WizardCloakModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition armorHead = partdefinition.addOrReplaceChild("armorHead", CubeListBuilder.create().texOffs(52, 0).addBox(-4.0F, -8.0F, 4.0F, 8.0F, 8.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(24, 8).addBox(4.0F, -8.0F, -4.0F, 1.0F, 8.0F, 8.0F, CubeDeformation.NONE)
		.texOffs(38, 78).addBox(-4.0F, -9.0F, -4.0F, 8.0F, 1.0F, 8.0F, CubeDeformation.NONE)
		.texOffs(0, 16).addBox(-4.0F, 0.0F, -4.0F, 8.0F, 1.0F, 8.0F, CubeDeformation.NONE)
		.texOffs(0, 0).addBox(3.5F, -7.0F, -5.0F, 1.0F, 7.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(0, 0).addBox(-4.5F, -7.0F, -5.0F, 1.0F, 7.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(60, 28).addBox(-4.5F, -8.0F, -5.0F, 9.0F, 1.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(4, 0).addBox(-5.0F, -7.0F, 4.0F, 1.0F, 6.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(4, 0).addBox(4.0F, -7.0F, 4.0F, 1.0F, 6.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(95, 16).addBox(-3.5F, -7.0F, -5.0F, 1.0F, 5.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(92, 27).addBox(-2.5F, -7.0F, -5.0F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(83, 56).addBox(1.5F, -7.0F, -5.0F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(84, 8).addBox(2.5F, -7.0F, -5.0F, 1.0F, 5.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(4, 0).addBox(-11.0F, -1.0F, 4.0F, 1.0F, 6.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(52, 0).addBox(-4.0F, -8.0F, 4.0F, 8.0F, 8.0F, 1.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition armorBody = partdefinition.addOrReplaceChild("armorBody", CubeListBuilder.create().texOffs(34, 0).addBox(-4.0F, -2.0F, 2.0F, 8.0F, 14.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(34, 0).addBox(-4.0F, -2.0F, -3.0F, 8.0F, 14.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(24, 25).addBox(4.0F, -3.0F, -3.0F, 1.0F, 15.0F, 6.0F, CubeDeformation.NONE)
		.texOffs(24, 25).addBox(-5.0F, -3.0F, -3.0F, 1.0F, 15.0F, 6.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 2.0F, 0.0F));

		PartDefinition armorLeftArm = partdefinition.addOrReplaceChild("armorLeftArm", CubeListBuilder.create().texOffs(10, 62).addBox(-1.0F, -2.0F, -3.0F, 4.0F, 11.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(10, 62).addBox(-1.0F, -2.0F, 2.0F, 4.0F, 11.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(46, 9).addBox(-1.0F, -3.0F, -3.0F, 5.0F, 1.0F, 6.0F, CubeDeformation.NONE), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition cube_r3 = armorLeftArm.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(54, 16).addBox(-3.0F, -2.0F, 1.0F, 6.0F, 11.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(54, 16).addBox(-3.0F, -2.0F, -4.0F, 6.0F, 11.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition armorRightArm = partdefinition.addOrReplaceChild("armorRightArm", CubeListBuilder.create().texOffs(0, 57).addBox(-3.0F, -2.0F, -3.0F, 4.0F, 11.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(56, 40).addBox(-3.0F, -2.0F, 2.0F, 4.0F, 11.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(48, 46).addBox(-4.0F, -2.0F, -3.0F, 1.0F, 11.0F, 6.0F, CubeDeformation.NONE)
		.texOffs(48, 46).addBox(1.0F, -2.0F, -3.0F, 1.0F, 11.0F, 6.0F, CubeDeformation.NONE)
		.texOffs(46, 9).addBox(-4.0F, -3.0F, -3.0F, 5.0F, 1.0F, 6.0F, CubeDeformation.NONE), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition armorLeftLeg = partdefinition.addOrReplaceChild("armorLeftLeg", CubeListBuilder.create().texOffs(54, 28).addBox(2.0F, -1.0F, 0.0F, 1.0F, 9.0F, 4.0F, CubeDeformation.NONE)
		.texOffs(19, 78).addBox(-2.0F, -1.0F, -1.0F, 5.0F, 9.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(20, 62).addBox(-2.0F, -1.0F, 4.0F, 5.0F, 9.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(32, 52).addBox(-3.0F, -1.0F, -1.0F, 1.0F, 9.0F, 6.0F, CubeDeformation.NONE), PartPose.offset(2.0F, 12.0F, -2.0F));

		PartDefinition armorRightLeg = partdefinition.addOrReplaceChild("armorRightLeg", CubeListBuilder.create().texOffs(54, 28).addBox(-3.0F, -1.0F, -2.0F, 1.0F, 9.0F, 4.0F, CubeDeformation.NONE)
		.texOffs(20, 62).addBox(-3.0F, -1.0F, 2.0F, 5.0F, 9.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(32, 52).addBox(2.0F, -1.0F, -3.0F, 1.0F, 9.0F, 6.0F, CubeDeformation.NONE)
		.texOffs(20, 62).addBox(-3.0F, -1.0F, -3.0F, 5.0F, 9.0F, 1.0F, CubeDeformation.NONE), PartPose.offset(-2.0F, 12.0F, 0.0F));

		PartDefinition armorLeftBoot = partdefinition.addOrReplaceChild("armorLeftBoot", CubeListBuilder.create().texOffs(24, 0).addBox(-2.0F, 7.0F, 2.0F, 4.0F, 5.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(76, 73).addBox(-2.0F, 7.0F, -3.0F, 4.0F, 5.0F, 1.0F, CubeDeformation.NONE), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition cube_r4 = armorLeftBoot.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(91, 71).addBox(-3.0F, 7.0F, 2.0F, 6.0F, 5.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(80, 91).addBox(-3.0F, 7.0F, -3.0F, 6.0F, 5.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition armorRightBoot = partdefinition.addOrReplaceChild("armorRightBoot", CubeListBuilder.create().texOffs(52, 98).addBox(-2.0F, 7.0F, -3.0F, 4.0F, 5.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(24, 0).addBox(-2.0F, 7.0F, 2.0F, 4.0F, 5.0F, 1.0F, CubeDeformation.NONE), PartPose.offset(-2.0F, 12.0F, 0.0F));

		PartDefinition cube_r5 = armorRightBoot.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(62, 9).addBox(-3.0F, 7.0F, 2.0F, 6.0F, 5.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(112, 10).addBox(-3.0F, 7.0F, -3.0F, 6.0F, 5.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}