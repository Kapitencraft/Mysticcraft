package net.kapitencraft.mysticcraft.item.combat.armor.client.model;
// Made with Blockbench 4.8.3


import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class ShadowAssassinArmorModel extends ArmorModel {
	public ShadowAssassinArmorModel(ModelPart root) {
		super(root);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition armorHead = partdefinition.addOrReplaceChild("armorHead", CubeListBuilder.create().texOffs(14, 47).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 8.5F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(46, 15).addBox(-4.5F, -8.5F, 4.5F, 9.0F, 8.5F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(28, 15).addBox(-4.0F, -9.0F, -5.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(27, 7).addBox(-4.0F, -9.0F, 4.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(18, 18).addBox(-5.0F, -8.0F, -4.0F, 1.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(0, 9).addBox(-5.0F, -9.0F, -4.0F, 10.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(28, 18).addBox(-5.0F, -3.0F, -5.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 22).addBox(-5.0F, -8.0F, -5.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 22).addBox(4.0F, -8.0F, -5.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(27, 0).addBox(4.0F, -3.0F, -5.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(4, 0).addBox(4.0F, -8.0F, 4.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, -8.0F, 4.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 18).addBox(4.0F, -8.0F, -4.0F, 1.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
		.texOffs(28, 0).addBox(-4.5F, -8.5F, -4.5F, 0.0F, 5.5F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-4.5F, -8.5F, -4.5F, 9.0F, 0.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(8, 25).addBox(4.5F, -8.5F, -4.5F, 0.0F, 5.5F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

			PartDefinition armorBody = partdefinition.addOrReplaceChild("armorBody", CubeListBuilder.create().texOffs(44, 38).addBox(-4.0F, 0.0F, -3.0F, 8.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(44, 25).addBox(-4.0F, 0.0F, 2.0F, 8.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 34).addBox(-5.0F, 0.0F, -3.0F, 1.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(30, 28).addBox(4.0F, 0.0F, -3.0F, 1.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(63, 3).addBox(-2.0F, 12.0F, -3.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(62, 12).addBox(-2.0F, 12.0F, 2.0F, 4.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 20).addBox(-1.0F, 14.0F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 9).addBox(-1.0F, 14.0F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 18).addBox(-4.0F, 12.0F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 15).addBox(2.0F, 12.0F, -3.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 13).addBox(2.0F, 12.0F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 11).addBox(-4.0F, 12.0F, 2.0F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition armorLeftArm = partdefinition.addOrReplaceChild("armorLeftArm", CubeListBuilder.create().texOffs(40, 8).addBox(-2.0F, -3.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(62, 28).addBox(-2.0F, -2.0F, -3.0F, 5.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(62, 23).addBox(-2.0F, -2.0F, 2.0F, 5.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(57, 4).addBox(2.0F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 2.0F, 0.0F));

		PartDefinition armorRightArm = partdefinition.addOrReplaceChild("armorRightArm", CubeListBuilder.create().texOffs(14, 40).addBox(-3.0F, -3.0F, -3.0F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(58, 56).addBox(-3.0F, -2.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(62, 38).addBox(-3.0F, -2.0F, -3.0F, 5.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(62, 33).addBox(-3.0F, -2.0F, 2.0F, 5.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 2.0F, 0.0F));

		PartDefinition armorLeftLeg = partdefinition.addOrReplaceChild("armorLeftLeg", CubeListBuilder.create().texOffs(10, 50).addBox(-2.5F, 0.0F, -2.5F, 0.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 47).addBox(2.5F, 0.0F, -2.5F, 0.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(30, 56).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(20, 55).addBox(-2.5F, 0.0F, 2.5F, 5.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		PartDefinition armorLeftBoot = partdefinition.addOrReplaceChild("armorLeftBoot", CubeListBuilder.create().texOffs(28, 18).addBox(-3.0F, 11.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(10, 22).addBox(-3.0F, 8.0F, -3.0F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(61, 0).addBox(-3.0F, 9.0F, 2.0F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(62, 49).addBox(-3.0F, 9.0F, -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(62, 43).addBox(2.0F, 9.0F, -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		PartDefinition armorRightBoot = partdefinition.addOrReplaceChild("armorRightBoot", CubeListBuilder.create().texOffs(27, 0).addBox(-3.0F, 11.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(10, 18).addBox(-3.0F, 8.0F, -3.0F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 60).addBox(-3.0F, 9.0F, 2.0F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 61).addBox(-3.0F, 9.0F, -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(50, 60).addBox(2.0F, 9.0F, -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition armorRightLeg = partdefinition.addOrReplaceChild("armorRightLeg", CubeListBuilder.create().texOffs(42, 46).addBox(2.5F, 0.0F, -2.5F, 0.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(32, 42).addBox(-2.5F, 0.0F, -2.5F, 0.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(52, 51).addBox(-2.5F, 0.0F, 2.5F, 5.0F, 9.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(51, 0).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}
}