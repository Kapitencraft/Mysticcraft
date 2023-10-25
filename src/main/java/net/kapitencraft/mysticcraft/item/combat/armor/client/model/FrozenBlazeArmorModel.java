package net.kapitencraft.mysticcraft.item.combat.armor.client.model;// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class FrozenBlazeArmorModel extends ArmorModel {

	public FrozenBlazeArmorModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition armorBody = partdefinition.addOrReplaceChild("armorBody", CubeListBuilder.create().texOffs(66, 13).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(66, 0).addBox(-4.0F, -0.0F, 2.0F, 8.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 32).addBox(-5.0F, -0.0F, -3.0F, 1.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(84, 0).addBox(4.0F, -0.0F, -3.0F, 1.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(77, 11).addBox(-1.0F, 12.0F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(77, 11).addBox(2.0F, 12.0F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(77, 11).addBox(2.0F, 12.0F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(77, 11).addBox(-1.0F, 12.0F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(77, 11).addBox(-4.0F, 12.0F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(77, 11).addBox(-4.0F, 12.0F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));


		PartDefinition armorHead = partdefinition.addOrReplaceChild("armorHead", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
		.texOffs(32, 3).addBox(-5.0F, -8.0F, -4.0F, 1.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(32, 3).mirror().addBox(4.0F, -8.0F, -4.0F, 1.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(0, 11).addBox(-5.0F, -8.0F, 4.0F, 10.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 11).addBox(3.0F, -8.0F, -5.0F, 2.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, -8.0F, -5.0F, 2.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 20).addBox(-3.0F, -8.0F, -5.0F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(30, 0).addBox(-3.0F, -4.0F, -5.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(30, 5).addBox(1.0F, -4.0F, -5.0F, 2.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition helmet_crystal = armorHead.addOrReplaceChild("helmet_crystal", CubeListBuilder.create().texOffs(6, 0).addBox(-5.0F, -13.0F, -5.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition helmet_crystal_r1 = helmet_crystal.addOrReplaceChild("helmet_crystal_r1", CubeListBuilder.create().texOffs(44, 1).addBox(-1.0F, -12.0F, -5.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.48F));

		PartDefinition helmet_crystal_r2 = helmet_crystal.addOrReplaceChild("helmet_crystal_r2", CubeListBuilder.create().texOffs(48, 0).addBox(-7.0F, -10.0F, -5.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.3491F));

		PartDefinition helmet_crystal_r3 = helmet_crystal.addOrReplaceChild("helmet_crystal_r3", CubeListBuilder.create().texOffs(48, 0).addBox(-2.0F, -11.0F, -8.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1745F, 0.5672F, 0.0F));

		PartDefinition helmet_crystal_r4 = helmet_crystal.addOrReplaceChild("helmet_crystal_r4", CubeListBuilder.create().texOffs(6, 5).addBox(-5.0F, -13.0F, 0.0F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition armorRightArm = partdefinition.addOrReplaceChild("armorRightArm", CubeListBuilder.create().texOffs(0, 50).addBox(-3.0F, -3.0F, -2.0F, 1.0F, 7.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(10, 55).addBox(-3.0F, -3.0F, -3.0F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 11).addBox(-2.0F, -3.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(54, 16).addBox(-3.0F, -3.0F, 2.0F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 2.0F, 0.0F));

		PartDefinition crystalRight = armorRightArm.addOrReplaceChild("crystalRight", CubeListBuilder.create(), PartPose.offset(2.0F, 22.0F, 0.0F));

		PartDefinition crystalRight_r1 = crystalRight.addOrReplaceChild("crystalRight_r1", CubeListBuilder.create().texOffs(37, 1).addBox(-5.0F, -23.0F, 12.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition crystalRight_r2 = crystalRight.addOrReplaceChild("crystalRight_r2", CubeListBuilder.create().texOffs(32, 12).addBox(-10.0F, -23.0F, 8.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.48F, 0.2618F));

		PartDefinition crystalRight_r3 = crystalRight.addOrReplaceChild("crystalRight_r3", CubeListBuilder.create().texOffs(23, 12).addBox(-6.0F, -27.0F, -1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.3491F, 0.0F));

		PartDefinition armorLeftArm = partdefinition.addOrReplaceChild("armorLeftArm", CubeListBuilder.create().texOffs(54, 23)
				.addBox(-2.0F, -3.0F, -3.0F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(10, 50)
				.addBox(-2.0F, -3.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(26, 50)
				.addBox(-2.0F, -3.0F, 2.0F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 0)
				.addBox(2.0F, -3.0F, -2.0F, 1.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 2.0F, 0.0F));

		PartDefinition crystalLeft = armorLeftArm.addOrReplaceChild("crystalLeft",
				CubeListBuilder.create().texOffs(15, 4).addBox(6.0F, -27.0F, -3.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
				PartPose.offset(-4.0F, 22.0F, 0.0F));

		PartDefinition crystalLeft_r1 = crystalLeft.addOrReplaceChild("crystalLeft_r1", CubeListBuilder.create().texOffs(37, 46).addBox(3.0F, -27.0F, -2.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.3054F, 0.1745F));
		PartDefinition crystalLeft_r2 = crystalLeft.addOrReplaceChild("crystalLeft_r2", CubeListBuilder.create().texOffs(19, 4).addBox(7.0F, -26.0F, 3.0F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3054F, -0.1309F, 0.0F));
		PartDefinition crystalLeft_r3 = crystalLeft.addOrReplaceChild("crystalLeft_r3", CubeListBuilder.create().texOffs(15, 3).addBox(-3.0F, -28.0F, 1.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0873F, 0.4363F));

		PartDefinition armorLeftLeg = partdefinition.addOrReplaceChild("armorLeftLeg", CubeListBuilder.create().texOffs(44, 19).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(44, 19).mirror().addBox(-2.0F, 0.0F, 2.0F, 4.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(8, 20).addBox(2.0F, 0.0F, -3.0F, 1.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		PartDefinition armorLeftBoot = partdefinition.addOrReplaceChild("armorLeftBoot", CubeListBuilder.create().texOffs(16, 20).addBox(-2.0F, 8.0F, -3.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(16, 20).addBox(-2.0F, 8.0F, 2.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 19).addBox(2.0F, 8.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(22, 29).addBox(-2.0F, 11.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		PartDefinition armorRightLeg = partdefinition.addOrReplaceChild("armorRightLeg", CubeListBuilder.create().texOffs(8, 20).mirror().addBox(-3.0F, 0.0F, -3.0F, 1.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(44, 19).addBox(-2.0F, 0.0F, 2.0F, 4.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(44, 19).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition armorRightBoot = partdefinition.addOrReplaceChild("armorRightBoot", CubeListBuilder.create().texOffs(16, 20).addBox(-2.0F, 8.0F, 2.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 19).mirror().addBox(-3.0F, 8.0F, -3.0F, 1.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
		.texOffs(16, 20).addBox(-2.0F, 8.0F, -3.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 29).addBox(-2.0F, 11.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}