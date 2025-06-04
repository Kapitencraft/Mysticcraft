package net.kapitencraft.mysticcraft.item.combat.armor.client.model;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.kapitencraft.kap_lib.client.armor.ArmorModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class CrimsonArmorModel extends ArmorModel {

	public CrimsonArmorModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		partdefinition.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);

		PartDefinition armorHead = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -9.0F, -5.0F, 8.0F, 1.0F, 10.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right = armorHead.addOrReplaceChild("right", CubeListBuilder.create().texOffs(24, 57).addBox(-5.0F, -9.0F, -15.0F, 1.0F, 9.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(14, 58).addBox(-5.0F, -9.0F, -13.0F, 1.0F, 8.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(8, 63).addBox(-5.0F, -9.0F, -12.0F, 1.0F, 7.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(40, 58).addBox(-5.0F, -9.0F, -11.0F, 1.0F, 6.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(63, 8).addBox(-5.0F, -9.0F, -9.0F, 1.0F, 7.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(54, 61).addBox(-5.0F, -9.0F, -8.0F, 1.0F, 8.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(17, 66).addBox(-5.0F, -9.0F, -6.0F, 1.0F, 6.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(64, 0).addBox(-5.0F, -9.0F, -7.0F, 1.0F, 7.0F, 1.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 0.0F, 10.0F));

		PartDefinition left = armorHead.addOrReplaceChild("left", CubeListBuilder.create().texOffs(18, 55).addBox(4.0F, -9.0F, 7.0F, 1.0F, 9.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(30, 62).addBox(4.0F, -9.0F, 9.0F, 1.0F, 8.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(66, 15).addBox(4.0F, -9.0F, 10.0F, 1.0F, 7.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(48, 59).addBox(4.0F, -9.0F, 11.0F, 1.0F, 6.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(65, 39).addBox(4.0F, -9.0F, 13.0F, 1.0F, 7.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(58, 61).addBox(4.0F, -9.0F, 14.0F, 1.0F, 8.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(34, 64).addBox(4.0F, -9.0F, 15.0F, 1.0F, 7.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(38, 66).addBox(4.0F, -9.0F, 16.0F, 1.0F, 6.0F, 1.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 0.0F, -12.0F));

		PartDefinition back = armorHead.addOrReplaceChild("back", CubeListBuilder.create().texOffs(67, 7).addBox(-4.0F, -5.0F, -0.5F, 1.0F, 6.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(66, 47).addBox(-3.0F, -5.0F, -0.5F, 1.0F, 5.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(66, 58).addBox(-2.0F, -5.0F, -0.5F, 1.0F, 6.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(60, 17).addBox(-1.0F, -5.0F, -0.5F, 2.0F, 7.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(42, 66).addBox(1.0F, -5.0F, -0.5F, 1.0F, 6.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(12, 67).addBox(2.0F, -5.0F, -0.5F, 1.0F, 5.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(65, 66).addBox(3.0F, -5.0F, -0.5F, 1.0F, 6.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -3.0F, 4.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition front = armorHead.addOrReplaceChild("front", CubeListBuilder.create().texOffs(54, 35).addBox(-3.0F, -5.0F, -0.5F, 6.0F, 3.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(56, 9).addBox(-1.0F, -2.0F, -0.5F, 2.0F, 2.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(0, 0).addBox(2.0F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(50, 9).addBox(1.0F, 1.0F, -0.5F, 2.0F, 2.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(6, 41).addBox(-3.0F, 1.0F, -0.5F, 2.0F, 2.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(0, 8).addBox(-1.0F, 2.0F, -0.5F, 2.0F, 1.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(6, 0).addBox(-3.0F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(4, 63).addBox(3.0F, -5.0F, -0.5F, 1.0F, 8.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(62, 58).addBox(-4.0F, -5.0F, -0.5F, 1.0F, 8.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, -3.0F, -4.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition armorBody = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(20, 26).addBox(-5.0F, 0.0F, -2.0F, 1.0F, 12.0F, 4.0F, CubeDeformation.NONE)
		.texOffs(10, 25).addBox(4.0F, 0.0F, -2.0F, 1.0F, 12.0F, 4.0F, CubeDeformation.NONE), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition chest_front = armorBody.addOrReplaceChild("chest_front", CubeListBuilder.create().texOffs(30, 36).addBox(-2.0F, -5.0F, -0.5F, 4.0F, 12.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(0, 54).addBox(-3.0F, -8.0F, -0.5F, 1.0F, 14.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(30, 49).addBox(-5.0F, -8.0F, -0.5F, 2.0F, 12.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(36, 49).addBox(2.0F, -8.0F, -0.5F, 1.0F, 14.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(50, 45).addBox(3.0F, -8.0F, -0.5F, 2.0F, 12.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(20, 26).addBox(-4.0F, 4.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(10, 25).addBox(3.0F, 4.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(26, 26).addBox(-2.0F, -6.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(10, 27).addBox(1.0F, -6.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 8.0F, -2.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition chest_back = armorBody.addOrReplaceChild("chest_back", CubeListBuilder.create().texOffs(0, 25).addBox(-2.0F, -8.0F, -0.5F, 4.0F, 15.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(26, 42).addBox(-3.0F, -8.0F, -0.5F, 1.0F, 14.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(40, 45).addBox(-5.0F, -8.0F, -0.5F, 2.0F, 12.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(46, 45).addBox(2.0F, -8.0F, -0.5F, 1.0F, 14.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(20, 42).addBox(3.0F, -8.0F, -0.5F, 2.0F, 12.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(6, 8).addBox(-4.0F, 4.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(18, 22).addBox(3.0F, 4.0F, -0.5F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 8.0F, 2.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition armorLeftArm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offset(5.0F, 2.0F, 0.0F));

		PartDefinition armorLeftArm_r1 = armorLeftArm.addOrReplaceChild("armorLeftArm_r1", CubeListBuilder.create().texOffs(18, 19).addBox(-9.0F, -25.0F, -3.0F, 5.0F, 1.0F, 6.0F, CubeDeformation.NONE)
		.texOffs(18, 11).addBox(-9.0F, -24.0F, -3.0F, 1.0F, 5.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(34, 19).addBox(-9.0F, -24.0F, 2.0F, 1.0F, 5.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(16, 26).addBox(-9.0F, -20.0F, -1.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(16, 41).addBox(-8.0F, -20.0F, -3.0F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(0, 41).addBox(-6.0F, -20.0F, -3.0F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(16, 43).addBox(-6.0F, -20.0F, 2.0F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(50, 17).addBox(-9.0F, -24.0F, -2.0F, 1.0F, 4.0F, 4.0F, CubeDeformation.NONE)
		.texOffs(58, 30).addBox(-8.0F, -24.0F, -3.0F, 4.0F, 4.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(58, 53).addBox(-8.0F, -24.0F, 2.0F, 4.0F, 4.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(0, 43).addBox(-8.0F, -20.0F, 2.0F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-5.0F, 22.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition armorRightArm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition armorRightArm_r1 = armorRightArm.addOrReplaceChild("armorRightArm_r1", CubeListBuilder.create().texOffs(18, 12).addBox(4.0F, -25.0F, -3.0F, 5.0F, 1.0F, 6.0F, CubeDeformation.NONE)
		.texOffs(18, 19).addBox(8.0F, -20.0F, -1.0F, 1.0F, 1.0F, 2.0F, CubeDeformation.NONE)
		.texOffs(26, 28).addBox(7.0F, -20.0F, 2.0F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(34, 11).addBox(5.0F, -20.0F, 2.0F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(40, 0).addBox(5.0F, -20.0F, -3.0F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(40, 2).addBox(7.0F, -20.0F, -3.0F, 1.0F, 1.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(0, 11).addBox(8.0F, -24.0F, -3.0F, 1.0F, 5.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(0, 18).addBox(8.0F, -24.0F, 2.0F, 1.0F, 5.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(4, 58).addBox(4.0F, -24.0F, -3.0F, 4.0F, 4.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(58, 25).addBox(4.0F, -24.0F, 2.0F, 4.0F, 4.0F, 1.0F, CubeDeformation.NONE)
		.texOffs(0, 0).addBox(8.0F, -24.0F, -2.0F, 1.0F, 4.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(5.0F, 22.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

		PartDefinition armorRightLeg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(10, 41).addBox(2.0F, 0.0F, -2.0F, 1.0F, 9.0F, 4.0F, CubeDeformation.NONE)
		.texOffs(0, 41).addBox(-3.0F, 0.0F, -2.0F, 1.0F, 9.0F, 4.0F, CubeDeformation.NONE), PartPose.offset(-2.0F, 12.0F, 0.0F));

		PartDefinition armorRightLeg_r1 = armorRightLeg.addOrReplaceChild("armorRightLeg_r1", CubeListBuilder.create().texOffs(44, 26).addBox(-3.0F, -5.0F, -0.5F, 6.0F, 8.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 5.0F, -2.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition armorRightLeg_r2 = armorRightLeg.addOrReplaceChild("armorRightLeg_r2", CubeListBuilder.create().texOffs(30, 26).addBox(-3.0F, -5.0F, -0.5F, 6.0F, 9.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 5.0F, 2.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition armorRightBoot = partdefinition.addOrReplaceChild("right_boot", CubeListBuilder.create().texOffs(0, 18).addBox(-3.0F, 11.0F, -3.0F, 6.0F, 1.0F, 6.0F, CubeDeformation.NONE), PartPose.offset(-2.0F, 12.0F, 0.0F));

		PartDefinition armorRightBoot_r1 = armorRightBoot.addOrReplaceChild("armorRightBoot_r1", CubeListBuilder.create().texOffs(56, 46).addBox(-0.5F, -2.0F, -2.0F, 1.0F, 3.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-2.5F, 10.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition armorRightBoot_r2 = armorRightBoot.addOrReplaceChild("armorRightBoot_r2", CubeListBuilder.create().texOffs(50, 0).addBox(-3.0F, -3.0F, -0.5F, 6.0F, 4.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 10.0F, -2.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition armorRightBoot_r3 = armorRightBoot.addOrReplaceChild("armorRightBoot_r3", CubeListBuilder.create().texOffs(4, 54).addBox(-3.0F, -2.0F, -0.5F, 6.0F, 3.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 10.0F, 2.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition armorRightBoot_r4 = armorRightBoot.addOrReplaceChild("armorRightBoot_r4", CubeListBuilder.create().texOffs(55, 39).addBox(-0.5F, -2.0F, -2.0F, 1.0F, 3.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(2.5F, 10.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition armorLeftLeg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(40, 13).addBox(-3.0F, 0.0F, -2.0F, 1.0F, 9.0F, 4.0F, CubeDeformation.NONE)
		.texOffs(40, 0).addBox(2.0F, 0.0F, -2.0F, 1.0F, 9.0F, 4.0F, CubeDeformation.NONE), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition armorLeftLeg_r1 = armorLeftLeg.addOrReplaceChild("armorLeftLeg_r1", CubeListBuilder.create().texOffs(26, 0).addBox(-3.0F, -5.0F, -0.5F, 6.0F, 9.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 5.0F, 2.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition armorLeftLeg_r2 = armorLeftLeg.addOrReplaceChild("armorLeftLeg_r2", CubeListBuilder.create().texOffs(40, 36).addBox(-3.0F, -5.0F, -0.5F, 6.0F, 8.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 5.0F, -2.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition armorLeftBoot = partdefinition.addOrReplaceChild("left_boot", CubeListBuilder.create().texOffs(0, 11).addBox(-3.0F, 11.0F, -3.0F, 6.0F, 1.0F, 6.0F, CubeDeformation.NONE), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition armorLeftBoot_r1 = armorLeftBoot.addOrReplaceChild("armorLeftBoot_r1", CubeListBuilder.create().texOffs(34, 10).addBox(-0.5F, -2.0F, -2.0F, 1.0F, 3.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(-2.5F, 10.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition armorLeftBoot_r2 = armorLeftBoot.addOrReplaceChild("armorLeftBoot_r2", CubeListBuilder.create().texOffs(52, 54).addBox(-0.5F, -2.0F, -2.0F, 1.0F, 3.0F, 4.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(2.5F, 10.0F, 0.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition armorLeftBoot_r3 = armorLeftBoot.addOrReplaceChild("armorLeftBoot_r3", CubeListBuilder.create().texOffs(50, 5).addBox(-3.0F, -2.0F, -0.5F, 6.0F, 3.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 10.0F, 2.5F, 0.0F, 3.1416F, 0.0F));

		PartDefinition armorLeftBoot_r4 = armorLeftBoot.addOrReplaceChild("armorLeftBoot_r4", CubeListBuilder.create().texOffs(49, 12).addBox(-3.0F, -3.0F, -0.5F, 6.0F, 4.0F, 1.0F, CubeDeformation.NONE), PartPose.offsetAndRotation(0.0F, 10.0F, -2.5F, 0.0F, 3.1416F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}