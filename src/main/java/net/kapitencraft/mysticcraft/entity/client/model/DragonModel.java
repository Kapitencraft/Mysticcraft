package net.kapitencraft.mysticcraft.entity.client.model;// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.kapitencraft.mysticcraft.entity.dragon.Dragon;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class DragonModel<T extends Dragon> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	private final ModelPart bone;
	private final ModelPart neck;
	private final ModelPart neck2;
	private final ModelPart neck3;
	private final ModelPart neck4;
	private final ModelPart neck5;
	private final ModelPart body;
	private final ModelPart wing;
	private final ModelPart wingtip;
	private final ModelPart rearleg;
	private final ModelPart rearlegtip;
	private final ModelPart rearfoot;
	private final ModelPart rearleg1;
	private final ModelPart rearlegtip1;
	private final ModelPart rearfoot1;
	private final ModelPart frontleg;
	private final ModelPart frontlegtip;
	private final ModelPart frontfoot;
	private final ModelPart frontleg1;
	private final ModelPart frontlegtip1;
	private final ModelPart frontfoot1;
	private final ModelPart tail;
	private final ModelPart tail2;
	private final ModelPart tail3;
	private final ModelPart tail4;
	private final ModelPart tail5;
	private final ModelPart tail6;
	private final ModelPart tail7;
	private final ModelPart tail8;
	private final ModelPart tail9;
	private final ModelPart tail10;
	private final ModelPart tail11;
	private final ModelPart Fin;
	private final ModelPart wing2;
	private final ModelPart wing21;
	private final ModelPart wing20;
	private final ModelPart wing201;
	private final ModelPart wing3;
	private final ModelPart wing31;
	private final ModelPart wing30;
	private final ModelPart wing301;
	private final ModelPart wing4;
	private final ModelPart wing41;
	private final ModelPart wing40;
	private final ModelPart wing401;
	private final ModelPart haelf20;
	private final ModelPart haelf30;
	private final ModelPart haelf2;
	private final ModelPart haelf3;
	private final ModelPart haelf4;
	private final ModelPart haelf40;
	private final ModelPart clawright;
	private final ModelPart clawleft;
	private final ModelPart rearclawright;
	private final ModelPart rearclawleft;
	private final ModelPart head;
	private final ModelPart Horns;
	private final ModelPart jaw;
	private final ModelPart ears;

	public DragonModel(ModelPart root) {
		this.bone = root.getChild("bone");
		this.neck = this.bone.getChild("neck");
		this.neck2 = this.neck.getChild("neck2");
		this.neck3 = this.neck2.getChild("neck3");
		this.neck4 = this.neck3.getChild("neck4");
		this.neck5 = this.neck4.getChild("neck5");
		this.body = this.bone.getChild("body");
		this.wing = this.bone.getChild("wing");
		this.wingtip = this.wing.getChild("wingtip");
		this.rearleg = this.bone.getChild("rearleg");
		this.rearlegtip = this.rearleg.getChild("rearlegtip");
		this.rearfoot = this.rearlegtip.getChild("rearfoot");
		this.rearleg1 = this.bone.getChild("rearleg1");
		this.rearlegtip1 = this.rearleg1.getChild("rearlegtip1");
		this.rearfoot1 = this.rearlegtip1.getChild("rearfoot1");
		this.frontleg = this.bone.getChild("frontleg");
		this.frontlegtip = this.frontleg.getChild("frontlegtip");
		this.frontfoot = this.frontlegtip.getChild("frontfoot");
		this.frontleg1 = this.bone.getChild("frontleg1");
		this.frontlegtip1 = this.frontleg1.getChild("frontlegtip1");
		this.frontfoot1 = this.frontlegtip1.getChild("frontfoot1");
		this.tail = this.bone.getChild("tail");
		this.tail2 = this.tail.getChild("tail2");
		this.tail3 = this.tail2.getChild("tail3");
		this.tail4 = this.tail3.getChild("tail4");
		this.tail5 = this.tail4.getChild("tail5");
		this.tail6 = this.tail5.getChild("tail6");
		this.tail7 = this.tail6.getChild("tail7");
		this.tail8 = this.tail7.getChild("tail8");
		this.tail9 = this.tail8.getChild("tail9");
		this.tail10 = this.tail9.getChild("tail10");
		this.tail11 = this.tail10.getChild("tail11");
		this.Fin = this.tail.getChild("Fin");
		this.wing2 = this.bone.getChild("wing2");
		this.wing21 = this.bone.getChild("wing21");
		this.wing20 = this.bone.getChild("wing20");
		this.wing201 = this.bone.getChild("wing201");
		this.wing3 = this.bone.getChild("wing3");
		this.wing31 = this.bone.getChild("wing31");
		this.wing30 = this.bone.getChild("wing30");
		this.wing301 = this.bone.getChild("wing301");
		this.wing4 = this.bone.getChild("wing4");
		this.wing41 = this.bone.getChild("wing41");
		this.wing40 = this.bone.getChild("wing40");
		this.wing401 = this.bone.getChild("wing401");
		this.haelf20 = this.bone.getChild("haelf20");
		this.haelf30 = this.bone.getChild("haelf30");
		this.haelf2 = this.bone.getChild("haelf2");
		this.haelf3 = this.bone.getChild("haelf3");
		this.haelf4 = this.bone.getChild("haelf4");
		this.haelf40 = this.bone.getChild("haelf40");
		this.clawright = this.bone.getChild("clawright");
		this.clawleft = this.bone.getChild("clawleft");
		this.rearclawright = this.bone.getChild("rearclawright");
		this.rearclawleft = this.bone.getChild("rearclawleft");
		this.head = this.bone.getChild("head");
		this.Horns = this.head.getChild("Horns");
		this.jaw = this.head.getChild("jaw");
		this.ears = this.head.getChild("ears");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition bone = partdefinition.addOrReplaceChild("bone", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

		PartDefinition neck = bone.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(103, 268).addBox(-1.0F, -9.0F, -8.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, -8.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition neck_r1 = neck.addOrReplaceChild("neck_r1", CubeListBuilder.create().texOffs(0, 127).addBox(-11.0F, -17.0F, -20.0F, 22.0F, 20.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 8.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition neck2 = neck.addOrReplaceChild("neck2", CubeListBuilder.create().texOffs(0, 219).addBox(-1.0F, -9.0F, -8.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition neck_r2 = neck2.addOrReplaceChild("neck_r2", CubeListBuilder.create().texOffs(243, 162).addBox(-10.0F, -14.0F, -33.0F, 20.0F, 19.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 18.0F, -0.3491F, 0.0F, 0.0F));

		PartDefinition neck3 = neck2.addOrReplaceChild("neck3", CubeListBuilder.create().texOffs(54, 131).addBox(-1.0F, -9.0F, -8.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition neck_r3 = neck3.addOrReplaceChild("neck_r3", CubeListBuilder.create().texOffs(50, 330).addBox(-9.0F, -7.0F, -44.0F, 18.0F, 18.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 28.0F, -0.5672F, 0.0F, 0.0F));

		PartDefinition neck4 = neck3.addOrReplaceChild("neck4", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition neck_r4 = neck4.addOrReplaceChild("neck_r4", CubeListBuilder.create().texOffs(162, 341).addBox(-8.0F, -3.0F, -54.0F, 16.0F, 16.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 38.0F, -0.6545F, 0.0F, 0.0F));

		PartDefinition neck5 = neck4.addOrReplaceChild("neck5", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition neck_r5 = neck5.addOrReplaceChild("neck_r5", CubeListBuilder.create().texOffs(302, 345).addBox(-7.0F, -3.0F, -64.0F, 14.0F, 15.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 48.0F, -0.6545F, 0.0F, 0.0F));

		PartDefinition body = bone.addOrReplaceChild("body", CubeListBuilder.create().texOffs(243, 162).addBox(-12.0F, 0.0F, -16.0F, 24.0F, 24.0F, 64.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -20.0F, 8.0F));

		PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(243, 195).addBox(-1.0F, -48.0F, 33.0F, 2.0F, 11.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(83, 259).addBox(-1.0F, -44.0F, 24.0F, 2.0F, 11.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(103, 252).addBox(-1.0F, -40.0F, 15.0F, 2.0F, 11.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(155, 260).addBox(-1.0F, -35.0F, 6.0F, 2.0F, 11.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(74, 248).addBox(-1.0F, -30.0F, -3.0F, 2.0F, 9.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-1.0F, -13.0F, -64.0F, 2.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 37).addBox(-1.0F, -15.0F, -53.0F, 2.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(58, 0).addBox(-1.0F, -17.0F, -42.0F, 2.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 82).addBox(-1.0F, -19.0F, -32.0F, 2.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(0, 203).addBox(-1.0F, -21.0F, -22.0F, 2.0F, 8.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(175, 260).addBox(-1.0F, -25.0F, -11.0F, 2.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 20.0F, -8.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition wing = bone.addOrReplaceChild("wing", CubeListBuilder.create(), PartPose.offsetAndRotation(-12.0F, -19.0F, 2.0F, 0.0F, 0.1745F, 0.1745F));

		PartDefinition wingtip = wing.addOrReplaceChild("wingtip", CubeListBuilder.create(), PartPose.offsetAndRotation(-56.0F, 0.0F, -2.0F, 0.0F, 0.0F, -0.3491F));

		PartDefinition rearleg = bone.addOrReplaceChild("rearleg", CubeListBuilder.create(), PartPose.offsetAndRotation(-16.0F, -8.0F, 42.0F, 1.0472F, 0.0F, 0.0F));

		PartDefinition rearleg_r1 = rearleg.addOrReplaceChild("rearleg_r1", CubeListBuilder.create().texOffs(0, 82).addBox(-23.0F, -22.0F, 26.0F, 16.0F, 29.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, 8.0F, -42.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition rearlegtip = rearleg.addOrReplaceChild("rearlegtip", CubeListBuilder.create().texOffs(338, 103).addBox(-6.0F, -13.0F, -8.0F, 12.0F, 27.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 28.0F, 1.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition rearfoot = rearlegtip.addOrReplaceChild("rearfoot", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 32.0F, -2.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition rearfoot_r1 = rearfoot.addOrReplaceChild("rearfoot_r1", CubeListBuilder.create().texOffs(0, 276).addBox(-22.0F, 31.0F, 36.0F, 12.0F, 8.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(16.0F, -52.0F, -41.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition rearleg1 = bone.addOrReplaceChild("rearleg1", CubeListBuilder.create(), PartPose.offsetAndRotation(16.0F, -8.0F, 42.0F, 1.0472F, 0.0F, 0.0F));

		PartDefinition rearleg1_r1 = rearleg1.addOrReplaceChild("rearleg1_r1", CubeListBuilder.create().texOffs(0, 37).addBox(8.0F, -23.0F, 26.0F, 16.0F, 29.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0F, 8.0F, -42.0F, -0.2182F, 0.0F, 0.0F));

		PartDefinition rearlegtip1 = rearleg1.addOrReplaceChild("rearlegtip1", CubeListBuilder.create().texOffs(338, 64).addBox(-6.0F, -13.0F, -8.0F, 12.0F, 27.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 28.0F, 1.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition rearfoot1 = rearlegtip1.addOrReplaceChild("rearfoot1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 32.0F, -2.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition cube_r1 = rearfoot1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(289, 118).addBox(-39.0F, -20.0F, 10.0F, 14.0F, 19.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.4399F, 0.0F, 0.0F));

		PartDefinition cube_r2 = rearfoot1.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(350, 345).addBox(-7.0F, -20.0F, 10.0F, 14.0F, 19.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 1.4835F, 0.0F, 0.0F));

		PartDefinition rearfoot_r2 = rearfoot1.addOrReplaceChild("rearfoot_r2", CubeListBuilder.create().texOffs(206, 260).addBox(10.0F, 31.0F, 36.0F, 12.0F, 8.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-16.0F, -52.0F, -41.0F, -0.0436F, 0.0F, 0.0F));

		PartDefinition frontleg = bone.addOrReplaceChild("frontleg", CubeListBuilder.create(), PartPose.offsetAndRotation(-12.0F, -4.0F, 2.0F, 1.1345F, 0.0F, 0.0F));

		PartDefinition frontlegtip = frontleg.addOrReplaceChild("frontlegtip", CubeListBuilder.create().texOffs(309, 0).addBox(-6.0F, -25.0F, -12.0F, 12.0F, 29.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 20.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

		PartDefinition frontlegtip_r1 = frontlegtip.addOrReplaceChild("frontlegtip_r1", CubeListBuilder.create().texOffs(289, 75).addBox(-16.0F, 6.0F, -11.0F, 9.0F, 34.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, -16.0F, -2.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition frontfoot = frontlegtip.addOrReplaceChild("frontfoot", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition frontfoot_r1 = frontfoot.addOrReplaceChild("frontfoot_r1", CubeListBuilder.create().texOffs(103, 305).addBox(-16.0F, 29.0F, -30.0F, 9.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(12.0F, -38.0F, -2.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition frontleg1 = bone.addOrReplaceChild("frontleg1", CubeListBuilder.create(), PartPose.offsetAndRotation(12.0F, -4.0F, 2.0F, 1.1345F, 0.0F, 0.0F));

		PartDefinition frontleg1_r1 = frontleg1.addOrReplaceChild("frontleg1_r1", CubeListBuilder.create().texOffs(0, 330).addBox(8.0F, -11.0F, -5.0F, 12.0F, 29.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, 4.0F, -2.0F, -0.3491F, 0.0F, 0.0F));

		PartDefinition frontlegtip1 = frontleg1.addOrReplaceChild("frontlegtip1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 20.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

		PartDefinition frontlegtip_r2 = frontlegtip1.addOrReplaceChild("frontlegtip_r2", CubeListBuilder.create().texOffs(214, 345).addBox(9.0F, 6.0F, -11.0F, 9.0F, 33.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, -16.0F, -2.0F, 0.1309F, 0.0F, 0.0F));

		PartDefinition frontfoot1 = frontlegtip1.addOrReplaceChild("frontfoot1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition frontfoot_r2 = frontfoot1.addOrReplaceChild("frontfoot_r2", CubeListBuilder.create().texOffs(48, 276).addBox(9.0F, 29.0F, -30.0F, 9.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, -38.0F, -2.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition tail = bone.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(0, 161).addBox(-12.0F, -5.0F, 0.0F, 24.0F, 19.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -14.0F, 56.0F));

		PartDefinition tail_r1 = tail.addOrReplaceChild("tail_r1", CubeListBuilder.create().texOffs(254, 260).addBox(-1.0F, -50.0F, 42.0F, 2.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -56.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition tail2 = tail.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 244).addBox(-11.0F, -5.0F, 0.0F, 22.0F, 17.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0175F, 0.0F, 0.0F));

		PartDefinition tail_r2 = tail2.addOrReplaceChild("tail_r2", CubeListBuilder.create().texOffs(206, 260).addBox(-1.0F, -54.0F, 51.0F, 2.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -66.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 209).addBox(-10.0F, -5.0F, 4.0F, 20.0F, 15.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0175F, 0.0F, 0.0F));

		PartDefinition tail_r3 = tail3.addOrReplaceChild("tail_r3", CubeListBuilder.create().texOffs(0, 244).addBox(-1.0F, -58.0F, 61.0F, 2.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -76.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition tail4 = tail3.addOrReplaceChild("tail4", CubeListBuilder.create().texOffs(289, 304).addBox(-9.0F, -6.0F, 14.0F, 18.0F, 14.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0175F, 0.0F, 0.0F));

		PartDefinition tail_r4 = tail4.addOrReplaceChild("tail_r4", CubeListBuilder.create().texOffs(0, 127).addBox(-1.0F, -62.0F, 69.0F, 2.0F, 9.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -86.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition tail5 = tail4.addOrReplaceChild("tail5", CubeListBuilder.create().texOffs(103, 260).addBox(-8.0F, -7.0F, 16.0F, 16.0F, 13.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0349F, 0.0F, 0.0F));

		PartDefinition tail_r5 = tail5.addOrReplaceChild("tail_r5", CubeListBuilder.create().texOffs(52, 190).addBox(-1.0F, -65.0F, 79.0F, 2.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -96.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition tail6 = tail5.addOrReplaceChild("tail6", CubeListBuilder.create().texOffs(243, 195).addBox(-7.0F, -6.0F, 25.0F, 14.0F, 12.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0524F, 0.0F, 0.0F));

		PartDefinition tail_r6 = tail6.addOrReplaceChild("tail_r6", CubeListBuilder.create().texOffs(64, 61).addBox(-1.0F, -69.0F, 89.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -106.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition tail7 = tail6.addOrReplaceChild("tail7", CubeListBuilder.create().texOffs(250, 345).addBox(-6.0F, -5.0F, 29.0F, 12.0F, 11.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0524F, 0.0F, 0.0F));

		PartDefinition tail_r7 = tail7.addOrReplaceChild("tail_r7", CubeListBuilder.create().texOffs(64, 50).addBox(-1.0F, -73.0F, 97.0F, 2.0F, 7.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -116.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition tail8 = tail7.addOrReplaceChild("tail8", CubeListBuilder.create().texOffs(108, 341).addBox(-5.0F, -5.0F, 32.0F, 10.0F, 10.0F, 17.0F, new CubeDeformation(0.0F))
		.texOffs(109, 341).addBox(-4.0F, -5.0F, 49.0F, 8.0F, 9.0F, 17.0F, new CubeDeformation(0.0F))
		.texOffs(110, 341).addBox(-3.0F, -5.0F, 66.0F, 6.0F, 8.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0175F, 0.0F, 0.0F));

		PartDefinition tail_r8 = tail8.addOrReplaceChild("tail_r8", CubeListBuilder.create().texOffs(58, 161).addBox(-1.0F, -76.0F, 106.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -126.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition tail9 = tail8.addOrReplaceChild("tail9", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, -0.0175F, 0.0F, 0.0F));

		PartDefinition tail_r9 = tail9.addOrReplaceChild("tail_r9", CubeListBuilder.create().texOffs(64, 104).addBox(-1.0F, -82.0F, 117.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 15.0F, -136.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition tail10 = tail9.addOrReplaceChild("tail10", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, -0.0349F, 0.0F, 0.0F));

		PartDefinition tail_r10 = tail10.addOrReplaceChild("tail_r10", CubeListBuilder.create().texOffs(64, 94).addBox(-1.0F, -86.0F, 127.0F, 2.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -146.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition tail11 = tail10.addOrReplaceChild("tail11", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, -0.0524F, 0.0F, 0.0F));

		PartDefinition tail_r11 = tail11.addOrReplaceChild("tail_r11", CubeListBuilder.create().texOffs(63, 37).addBox(-1.0F, -94.0F, 144.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 15.0F, -164.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition tail_r12 = tail11.addOrReplaceChild("tail_r12", CubeListBuilder.create().texOffs(63, 37).addBox(-1.0F, -94.0F, 144.0F, 2.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 14.0F, -156.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition Fin = tail.addOrReplaceChild("Fin", CubeListBuilder.create().texOffs(-27, 585).addBox(-16.0F, -23.0F, 141.0F, 33.0F, 0.0F, 27.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition wing2 = bone.addOrReplaceChild("wing2", CubeListBuilder.create().texOffs(389, 389).addBox(-82.0F, -20.0F, 3.0F, 73.0F, 0.0F, 77.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition wing21 = bone.addOrReplaceChild("wing21", CubeListBuilder.create().texOffs(273, 260).addBox(-83.0F, -25.0F, -2.0F, 74.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition wing20 = bone.addOrReplaceChild("wing20", CubeListBuilder.create().texOffs(389, 311).addBox(9.0F, -20.0F, 3.0F, 73.0F, 0.0F, 77.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition wing201 = bone.addOrReplaceChild("wing201", CubeListBuilder.create().texOffs(87, 236).addBox(9.0F, -25.0F, -2.0F, 74.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition wing3 = bone.addOrReplaceChild("wing3", CubeListBuilder.create().texOffs(410, 232).addBox(-144.0F, -20.0F, 1.0F, 62.0F, 0.0F, 78.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition wing31 = bone.addOrReplaceChild("wing31", CubeListBuilder.create().texOffs(79, 293).addBox(-145.0F, -23.0F, -1.0F, 63.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition wing30 = bone.addOrReplaceChild("wing30", CubeListBuilder.create().texOffs(410, 155).addBox(82.0F, -20.0F, 1.0F, 62.0F, 0.0F, 78.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition wing301 = bone.addOrReplaceChild("wing301", CubeListBuilder.create().texOffs(265, 292).addBox(83.0F, -23.0F, -1.0F, 63.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition wing4 = bone.addOrReplaceChild("wing4", CubeListBuilder.create().texOffs(443, 7).addBox(144.0F, -20.5F, 0.0F, 47.0F, 0.0F, 75.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition wing41 = bone.addOrReplaceChild("wing41", CubeListBuilder.create().texOffs(289, 276).addBox(136.0F, -22.0F, 0.0F, 46.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition wing40 = bone.addOrReplaceChild("wing40", CubeListBuilder.create().texOffs(447, 82).addBox(-191.1F, -19.6F, 2.0F, 47.0F, 0.0F, 71.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition wing401 = bone.addOrReplaceChild("wing401", CubeListBuilder.create().texOffs(289, 284).addBox(-191.0F, -22.0F, 0.0F, 46.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition haelf20 = bone.addOrReplaceChild("haelf20", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, -71.0F));

		PartDefinition cube_r3 = haelf20.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(109, 54).addBox(115.0F, 1.0F, -72.0F, 3.0F, 4.0F, 100.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-54.0F, -21.0F, 52.0F, 0.0F, -0.6981F, 0.0F));

		PartDefinition haelf30 = bone.addOrReplaceChild("haelf30", CubeListBuilder.create().texOffs(121, 258).addBox(94.0F, -4.0F, 2.0F, 3.0F, 4.0F, 79.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.0F, -19.0F, 2.0F));

		PartDefinition haelf2 = bone.addOrReplaceChild("haelf2", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, -71.0F));

		PartDefinition cube_r4 = haelf2.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(137, 156).addBox(-3.0F, -4.0F, 0.0F, 3.0F, 4.0F, 100.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-81.0F, -16.0F, 71.0F, 0.0F, 0.6981F, 0.0F));

		PartDefinition haelf3 = bone.addOrReplaceChild("haelf3", CubeListBuilder.create().texOffs(206, 264).addBox(-73.0F, -4.0F, 4.0F, 3.0F, 4.0F, 77.0F, new CubeDeformation(0.0F)), PartPose.offset(-12.0F, -19.0F, 2.0F));

		PartDefinition haelf4 = bone.addOrReplaceChild("haelf4", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r5 = haelf4.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(226, 52).addBox(2.0F, -4.0F, 3.0F, 3.0F, 4.0F, 106.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-91.0F, -19.0F, -1.0F, 0.0F, -0.8727F, 0.0F));

		PartDefinition haelf40 = bone.addOrReplaceChild("haelf40", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r6 = haelf40.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(-105, -104).addBox(-2.0F, -4.0F, -1.0F, 3.0F, 4.0F, 106.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(90.0F, -18.0F, 4.0F, 0.0F, 0.8727F, 0.0F));

		PartDefinition clawright = bone.addOrReplaceChild("clawright", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, -58.0F));

		PartDefinition cube_r7 = clawright.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 504).addBox(12.5F, 60.0F, -88.0F, 2.0F, 4.5F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.1395F, -0.0033F, 0.0736F));

		PartDefinition cube_r8 = clawright.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 504).addBox(12.5F, 59.5F, -89.0F, 2.0F, 4.5F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.138F, 0.0F, 0.0F));

		PartDefinition cube_r9 = clawright.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 504).addBox(16.5F, 58.0F, -89.5F, 2.0F, 4.5F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.1427F, -0.0402F, -0.0736F));

		PartDefinition clawleft = bone.addOrReplaceChild("clawleft", CubeListBuilder.create(), PartPose.offset(0.0F, -7.0F, -58.0F));

		PartDefinition cube_r10 = clawleft.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 504).addBox(-12.0F, 59.5F, -90.0F, 2.0F, 4.5F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.1395F, -0.0033F, 0.0736F));

		PartDefinition cube_r11 = clawleft.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 504).addBox(-12.0F, 59.5F, -89.0F, 2.0F, 4.5F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.138F, 0.0F, 0.0F));

		PartDefinition cube_r12 = clawleft.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 504).addBox(-8.0F, 59.5F, -89.0F, 2.0F, 4.5F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 2.1427F, -0.0402F, -0.0736F));

		PartDefinition rearclawright = bone.addOrReplaceChild("rearclawright", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r13 = rearclawright.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(500, 0).addBox(16.0F, 83.0F, 65.0F, 3.0F, 2.0F, 3.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5655F, -0.0468F, 0.0737F));

		PartDefinition cube_r14 = rearclawright.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(500, 0).addBox(15.0F, 84.0F, 65.0F, 3.0F, 2.0F, 3.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition cube_r15 = rearclawright.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(500, 0).addBox(8.0F, 85.5F, 66.5F, 3.0F, 2.0F, 3.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5603F, 0.0934F, -0.074F));

		PartDefinition cube_r16 = rearclawright.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(496, 504).addBox(8.0F, 83.0F, 63.0F, 3.0F, 4.5F, 4.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5603F, 0.0934F, -0.074F));

		PartDefinition cube_r17 = rearclawright.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(496, 504).addBox(15.0F, 81.5F, 62.0F, 3.0F, 4.5F, 4.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition cube_r18 = rearclawright.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(496, 504).addBox(16.0F, 80.5F, 62.0F, 3.0F, 4.5F, 4.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5655F, -0.0468F, 0.0737F));

		PartDefinition rearclawleft = bone.addOrReplaceChild("rearclawleft", CubeListBuilder.create(), PartPose.offset(-13.0F, 15.0F, 80.0F));

		PartDefinition cube_r19 = rearclawleft.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(500, 0).addBox(-2.0F, 28.0F, 6.0F, 3.0F, 2.0F, 3.5F, new CubeDeformation(0.0F))
		.texOffs(496, 504).addBox(-2.0F, 25.5F, 2.0F, 3.0F, 4.5F, 4.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5603F, 0.0934F, -0.074F));

		PartDefinition cube_r20 = rearclawleft.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(500, 0).addBox(-4.0F, 28.0F, 6.0F, 3.0F, 2.0F, 3.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5672F, 0.0F, 0.0F));

		PartDefinition cube_r21 = rearclawleft.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(500, 0).addBox(-8.0F, 28.0F, 6.0F, 3.0F, 2.0F, 3.5F, new CubeDeformation(0.0F))
		.texOffs(496, 504).addBox(-8.0F, 25.5F, 2.0F, 3.0F, 4.5F, 4.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5655F, -0.0468F, 0.0737F));

		PartDefinition cube_r22 = rearclawleft.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(496, 504).addBox(-4.0F, 25.5F, 2.0F, 3.0F, 4.5F, 4.5F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5655F, 0.0F, 0.0F));

		PartDefinition head = bone.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -3.0F, -56.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition cube_r23 = head.addOrReplaceChild("cube_r23", CubeListBuilder.create().texOffs(356, 503).addBox(-1.0F, -41.0F, -1.0F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
		.texOffs(358, 504).addBox(-3.0F, -42.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(418, 505).addBox(-5.5F, -43.0F, -6.0F, 5.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
		.texOffs(445, 506).addBox(-7.5F, -44.0F, -8.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(470, 506).addBox(-10.5F, -45.0F, -11.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7418F, 0.0F));

		PartDefinition head_r1 = head.addOrReplaceChild("head_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -25.0F, -75.0F, 20.0F, 19.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.0F, 58.0F, -0.3491F, 0.0F, 0.0F));

		PartDefinition Horns = head.addOrReplaceChild("Horns", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 58.0F));

		PartDefinition cube_r24 = Horns.addOrReplaceChild("cube_r24", CubeListBuilder.create().texOffs(38, 500).addBox(-1.0F, -21.0F, -48.0F, 2.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, -58.0F, -0.9163F, 0.0F, 0.0F));

		PartDefinition head_r2 = Horns.addOrReplaceChild("head_r2", CubeListBuilder.create().texOffs(33, 190).addBox(23.0F, -55.5F, -33.0F, 4.0F, 5.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(33, 190).addBox(23.0F, -54.5F, -20.0F, 3.0F, 4.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.4276F, 0.0F));

		PartDefinition head_r3 = Horns.addOrReplaceChild("head_r3", CubeListBuilder.create().texOffs(31, 308).addBox(-24.0F, -57.0F, -33.0F, 4.0F, 5.0F, 13.0F, new CubeDeformation(0.0F))
		.texOffs(31, 308).addBox(-23.0F, -56.0F, -20.0F, 3.0F, 4.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2925F, -0.3097F, 0.0355F));

		PartDefinition head_r4 = Horns.addOrReplaceChild("head_r4", CubeListBuilder.create().texOffs(31, 308).addBox(-31.0F, -54.0F, -31.0F, 3.0F, 4.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, -0.4363F, 0.0F));

		PartDefinition head_r5 = Horns.addOrReplaceChild("head_r5", CubeListBuilder.create().texOffs(214, 268).addBox(28.5F, -53.0F, -20.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(33, 190).addBox(28.0F, -54.0F, -33.0F, 3.0F, 4.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.4363F, 0.0F));

		PartDefinition cube_r25 = Horns.addOrReplaceChild("cube_r25", CubeListBuilder.create().texOffs(117, 252).addBox(-6.0F, -32.5F, 31.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.0F, -58.0F, 0.2618F, -0.4363F, 0.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 4.0F, -13.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition cube_r26 = jaw.addOrReplaceChild("cube_r26", CubeListBuilder.create().texOffs(12, 388).addBox(-9.0F, -45.5F, -23.0F, 18.0F, 12.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.6109F, 0.0F, 0.0F));

		PartDefinition jaw_r1 = jaw.addOrReplaceChild("jaw_r1", CubeListBuilder.create().texOffs(309, 42).addBox(-7.0F, -6.0F, -83.0F, 15.0F, 4.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 3.0F, 71.0F, -0.4363F, 0.0F, 0.0F));

		PartDefinition ears = head.addOrReplaceChild("ears", CubeListBuilder.create(), PartPose.offset(0.0F, 7.0F, 58.0F));

		PartDefinition head_r6 = ears.addOrReplaceChild("head_r6", CubeListBuilder.create().texOffs(57, 120).addBox(-28.0F, -34.0F, -45.0F, 3.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1309F, -0.4363F, 0.0F));

		PartDefinition head_r7 = ears.addOrReplaceChild("head_r7", CubeListBuilder.create().texOffs(48, 37).addBox(25.0F, -44.0F, -38.0F, 3.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.4363F, 0.0F));

		return LayerDefinition.create(meshdefinition, 612, 612);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, int color) {
		bone.render(poseStack, vertexConsumer, packedLight, packedOverlay, color);
	}
}