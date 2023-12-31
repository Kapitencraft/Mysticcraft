package net.kapitencraft.mysticcraft.entity.client.model;


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.kapitencraft.mysticcraft.entity.vampire.VampireBat;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class VampireBatModel extends EntityModel<VampireBat> {
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart rightWing;
	private final ModelPart leftWing;
	private final ModelPart rightWingTip;
	private final ModelPart leftWingTip;

	public VampireBatModel(ModelPart root) {
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.rightWing = this.body.getChild("rightWing");
		this.leftWing = this.body.getChild("leftWing");
		this.rightWingTip = this.rightWing.getChild("rightWingTip");
		this.leftWingTip = this.leftWing.getChild("leftWingTip");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -3.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.1047F, 0.0873F, 0.0F));

		PartDefinition rightEar = head.addOrReplaceChild("rightEar", CubeListBuilder.create().texOffs(24, 0).addBox(-4.0F, -6.0F, -2.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leftEar = head.addOrReplaceChild("leftEar", CubeListBuilder.create().texOffs(24, 0).mirror().addBox(1.0F, -6.0F, -2.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(27, 34).addBox(-2.0F, -1.0F, -4.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(1, 1).addBox(-2.0F, 2.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(1, 1).addBox(1.0F, 2.0F, -4.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-3.0F, 4.0F, -3.0F, 6.0F, 12.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(1, 35).addBox(-5.0F, 16.0F, 1.0F, 10.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

		PartDefinition rightWing = body.addOrReplaceChild("rightWing", CubeListBuilder.create().texOffs(43, 1).addBox(-12.0F, 1.0F, 2.5F, 10.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.1745F, 0.0F));

		PartDefinition rightWingTip = rightWing.addOrReplaceChild("rightWingTip", CubeListBuilder.create().texOffs(25, 17).addBox(-7.7F, 1.0F, 1.0F, 8.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, 1.0F, 1.5F, 0.0F, -0.2618F, 0.0F));

		PartDefinition leftWing = body.addOrReplaceChild("leftWing", CubeListBuilder.create().texOffs(43, 1).mirror().addBox(2.0F, 1.0F, 2.5F, 10.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.1745F, 0.0F));

		PartDefinition leftWingTip = leftWing.addOrReplaceChild("leftWingTip", CubeListBuilder.create().texOffs(25, 17).mirror().addBox(-0.3F, 1.0F, 1.0F, 8.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(12.0F, 1.0F, 1.5F, 0.0F, 0.2618F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(VampireBat entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.head.xRot = headPitch * ((float)Math.PI / 180F);
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		this.head.zRot = 0.0F;
		this.head.setPos(0.0F, 0.0F, 0.0F);
		this.rightWing.setPos(0.0F, 0.0F, 0.0F);
		this.leftWing.setPos(0.0F, 0.0F, 0.0F);
		this.body.xRot = ((float)Math.PI / 4F) + Mth.cos(ageInTicks * 0.1F) * 0.15F;
		this.body.yRot = 0.0F;
		this.rightWing.yRot = Mth.cos(ageInTicks * 74.48451F * ((float)Math.PI / 180F)) * (float)Math.PI * 0.25F;
		this.leftWing.yRot = -this.rightWing.yRot;
		this.rightWingTip.yRot = this.rightWing.yRot * 0.5F;
		this.leftWingTip.yRot = -this.rightWing.yRot * 0.5F;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}