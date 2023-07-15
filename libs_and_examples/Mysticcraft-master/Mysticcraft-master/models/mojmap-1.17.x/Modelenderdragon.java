// Made with Blockbench 4.0.5
// Exported for Minecraft version 1.17 with Mojang mappings
// Paste this class into your mod and generate all required imports

public class Modelenderdragon<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in
	// the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
			new ResourceLocation("modid", "enderdragon"), "main");
	private final ModelPart neck;
	private final ModelPart body;
	private final ModelPart wing;
	private final ModelPart wing1;
	private final ModelPart rearleg;
	private final ModelPart rearleg1;
	private final ModelPart frontleg;
	private final ModelPart frontleg1;
	private final ModelPart tail;

	public Modelenderdragon(ModelPart root) {
		this.neck = root.getChild("neck");
		this.body = root.getChild("body");
		this.wing = root.getChild("wing");
		this.wing1 = root.getChild("wing1");
		this.rearleg = root.getChild("rearleg");
		this.rearleg1 = root.getChild("rearleg1");
		this.frontleg = root.getChild("frontleg");
		this.frontleg1 = root.getChild("frontleg1");
		this.tail = root.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition neck = partdefinition.addOrReplaceChild("neck",
				CubeListBuilder.create().texOffs(192, 104)
						.addBox(-5.0F, -5.0F, -10.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(48, 0)
						.addBox(-1.0F, -9.0F, -8.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 17.0F, -8.0F, -0.0873F, 0.0F, 0.0F));

		PartDefinition neck2 = neck.addOrReplaceChild("neck2",
				CubeListBuilder.create().texOffs(192, 104)
						.addBox(-5.0F, -5.0F, -10.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(48, 0)
						.addBox(-1.0F, -9.0F, -8.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition neck3 = neck2.addOrReplaceChild("neck3",
				CubeListBuilder.create().texOffs(192, 104)
						.addBox(-5.0F, -5.0F, -10.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(48, 0)
						.addBox(-1.0F, -9.0F, -8.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition neck4 = neck3.addOrReplaceChild("neck4",
				CubeListBuilder.create().texOffs(192, 104)
						.addBox(-5.0F, -5.0F, -10.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(48, 0)
						.addBox(-1.0F, -9.0F, -8.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition neck5 = neck4.addOrReplaceChild("neck5",
				CubeListBuilder.create().texOffs(192, 104)
						.addBox(-5.0F, -5.0F, -10.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(48, 0)
						.addBox(-1.0F, -9.0F, -8.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition head = neck5.addOrReplaceChild("head", CubeListBuilder.create().texOffs(176, 44)
				.addBox(-6.0F, -1.0F, -30.0F, 12.0F, 5.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(112, 30)
				.addBox(-8.0F, -8.0F, -16.0F, 16.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).texOffs(0, 0).mirror()
				.addBox(-5.0F, -12.0F, -10.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(112, 0).mirror().addBox(-5.0F, -3.0F, -28.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
				.mirror(false).texOffs(0, 0).addBox(3.0F, -12.0F, -10.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(112, 0).addBox(3.0F, -3.0F, -28.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition jaw = head.addOrReplaceChild("jaw",
				CubeListBuilder.create().texOffs(176, 65).addBox(-6.0F, 0.0F, -17.0F, 12.0F, 4.0F, 16.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 4.0F, -13.0F, 0.2618F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body",
				CubeListBuilder.create().texOffs(0, 0)
						.addBox(-12.0F, 0.0F, -16.0F, 24.0F, 24.0F, 64.0F, new CubeDeformation(0.0F)).texOffs(220, 53)
						.addBox(-1.0F, -6.0F, -10.0F, 2.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(220, 53)
						.addBox(-1.0F, -6.0F, 10.0F, 2.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)).texOffs(220, 53)
						.addBox(-1.0F, -6.0F, 30.0F, 2.0F, 6.0F, 12.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 4.0F, 8.0F));

		PartDefinition wing = partdefinition.addOrReplaceChild("wing",
				CubeListBuilder.create().texOffs(112, 88)
						.addBox(-56.0F, -4.0F, -4.0F, 56.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).texOffs(-56, 88)
						.addBox(-56.0F, 0.0F, 2.0F, 56.0F, 0.0F, 56.0F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(-12.0F, 5.0F, 2.0F, 0.0F, 0.1745F, 0.1745F));

		PartDefinition wingtip = wing.addOrReplaceChild("wingtip",
				CubeListBuilder.create().texOffs(112, 136)
						.addBox(-56.0F, -2.0F, 0.0F, 56.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).texOffs(-56, 144)
						.addBox(-56.0F, 0.0F, 4.0F, 56.0F, 0.0F, 56.0F, new CubeDeformation(0.01F)),
				PartPose.offsetAndRotation(-56.0F, 0.0F, -2.0F, 0.0F, 0.0F, -0.3491F));

		PartDefinition wing1 = partdefinition.addOrReplaceChild("wing1",
				CubeListBuilder.create().texOffs(112, 88).mirror()
						.addBox(0.0F, -4.0F, -4.0F, 56.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false)
						.texOffs(-56, 88).mirror()
						.addBox(0.0F, 0.0F, 2.0F, 56.0F, 0.0F, 56.0F, new CubeDeformation(0.01F)).mirror(false),
				PartPose.offsetAndRotation(12.0F, 5.0F, 2.0F, 0.0F, -0.1745F, -0.1745F));

		PartDefinition wingtip1 = wing1.addOrReplaceChild("wingtip1",
				CubeListBuilder.create().texOffs(112, 136).mirror()
						.addBox(0.0F, -2.0F, 0.0F, 56.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
						.texOffs(-56, 144).mirror()
						.addBox(0.0F, 0.0F, 4.0F, 56.0F, 0.0F, 56.0F, new CubeDeformation(0.01F)).mirror(false),
				PartPose.offsetAndRotation(56.0F, 0.0F, -2.0F, 0.0F, 0.0F, 0.3491F));

		PartDefinition rearleg = partdefinition.addOrReplaceChild("rearleg",
				CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -4.0F, -8.0F, 16.0F, 32.0F, 16.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-16.0F, 16.0F, 42.0F, 1.0472F, 0.0F, 0.0F));

		PartDefinition rearlegtip = rearleg
				.addOrReplaceChild("rearlegtip",
						CubeListBuilder.create().texOffs(196, 0).addBox(-6.0F, 0.0F, -7.0F, 12.0F, 32.0F, 12.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 28.0F, 1.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition rearfoot = rearlegtip.addOrReplaceChild("rearfoot",
				CubeListBuilder.create().texOffs(112, 0).addBox(-9.0F, 0.0F, -20.0F, 18.0F, 6.0F, 24.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 32.0F, -2.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition rearleg1 = partdefinition.addOrReplaceChild("rearleg1",
				CubeListBuilder.create().texOffs(0, 0).mirror()
						.addBox(-8.0F, -4.0F, -8.0F, 16.0F, 32.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(16.0F, 16.0F, 42.0F, 1.0472F, 0.0F, 0.0F));

		PartDefinition rearlegtip1 = rearleg1.addOrReplaceChild("rearlegtip1",
				CubeListBuilder.create().texOffs(196, 0).mirror()
						.addBox(-6.0F, 0.0F, -7.0F, 12.0F, 32.0F, 12.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(0.0F, 28.0F, 1.0F, 0.4363F, 0.0F, 0.0F));

		PartDefinition rearfoot1 = rearlegtip1.addOrReplaceChild("rearfoot1",
				CubeListBuilder.create().texOffs(112, 0).mirror()
						.addBox(-9.0F, 0.0F, -20.0F, 18.0F, 6.0F, 24.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(0.0F, 32.0F, -2.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition frontleg = partdefinition.addOrReplaceChild("frontleg",
				CubeListBuilder.create().texOffs(112, 104).addBox(-4.0F, -4.0F, -4.0F, 8.0F, 24.0F, 8.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(-12.0F, 20.0F, 2.0F, 1.1345F, 0.0F, 0.0F));

		PartDefinition frontlegtip = frontleg.addOrReplaceChild("frontlegtip",
				CubeListBuilder.create().texOffs(226, 138).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 24.0F, 6.0F,
						new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 20.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

		PartDefinition frontfoot = frontlegtip
				.addOrReplaceChild("frontfoot",
						CubeListBuilder.create().texOffs(144, 104).addBox(-4.0F, 0.0F, -12.0F, 8.0F, 4.0F, 16.0F,
								new CubeDeformation(0.0F)),
						PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition frontleg1 = partdefinition.addOrReplaceChild("frontleg1",
				CubeListBuilder.create().texOffs(112, 104).mirror()
						.addBox(-4.0F, -4.0F, -4.0F, 8.0F, 24.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(12.0F, 20.0F, 2.0F, 1.1345F, 0.0F, 0.0F));

		PartDefinition frontlegtip1 = frontleg1.addOrReplaceChild("frontlegtip1",
				CubeListBuilder.create().texOffs(226, 138).mirror()
						.addBox(-3.0F, -1.0F, -3.0F, 6.0F, 24.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(0.0F, 20.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

		PartDefinition frontfoot1 = frontlegtip1.addOrReplaceChild("frontfoot1",
				CubeListBuilder.create().texOffs(144, 104).mirror()
						.addBox(-4.0F, 0.0F, -12.0F, 8.0F, 4.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false),
				PartPose.offsetAndRotation(0.0F, 22.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail",
				CubeListBuilder.create().texOffs(192, 104)
						.addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(48, 0)
						.addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offset(0.0F, 10.0F, 56.0F));

		PartDefinition tail2 = tail.addOrReplaceChild("tail2",
				CubeListBuilder.create().texOffs(192, 104)
						.addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(48, 0)
						.addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0175F, 0.0F, 0.0F));

		PartDefinition tail3 = tail2.addOrReplaceChild("tail3",
				CubeListBuilder.create().texOffs(192, 104)
						.addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(48, 0)
						.addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0175F, 0.0F, 0.0F));

		PartDefinition tail4 = tail3.addOrReplaceChild("tail4",
				CubeListBuilder.create().texOffs(192, 104)
						.addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(48, 0)
						.addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0175F, 0.0F, 0.0F));

		PartDefinition tail5 = tail4.addOrReplaceChild("tail5",
				CubeListBuilder.create().texOffs(192, 104)
						.addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(48, 0)
						.addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0349F, 0.0F, 0.0F));

		PartDefinition tail6 = tail5.addOrReplaceChild("tail6",
				CubeListBuilder.create().texOffs(192, 104)
						.addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(48, 0)
						.addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0524F, 0.0F, 0.0F));

		PartDefinition tail7 = tail6.addOrReplaceChild("tail7",
				CubeListBuilder.create().texOffs(192, 104)
						.addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(48, 0)
						.addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0524F, 0.0F, 0.0F));

		PartDefinition tail8 = tail7.addOrReplaceChild("tail8",
				CubeListBuilder.create().texOffs(192, 104)
						.addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(48, 0)
						.addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, 0.0175F, 0.0F, 0.0F));

		PartDefinition tail9 = tail8.addOrReplaceChild("tail9",
				CubeListBuilder.create().texOffs(192, 104)
						.addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(48, 0)
						.addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, -0.0175F, 0.0F, 0.0F));

		PartDefinition tail10 = tail9.addOrReplaceChild("tail10",
				CubeListBuilder.create().texOffs(192, 104)
						.addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(48, 0)
						.addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, -0.0349F, 0.0F, 0.0F));

		PartDefinition tail11 = tail10.addOrReplaceChild("tail11",
				CubeListBuilder.create().texOffs(192, 104)
						.addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(48, 0)
						.addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, -0.0524F, 0.0F, 0.0F));

		PartDefinition tail12 = tail11.addOrReplaceChild("tail12",
				CubeListBuilder.create().texOffs(192, 104)
						.addBox(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).texOffs(48, 0)
						.addBox(-1.0F, -9.0F, 2.0F, 2.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)),
				PartPose.offsetAndRotation(0.0F, 0.0F, 10.0F, -0.0524F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 256);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay,
			float red, float green, float blue, float alpha) {
		neck.render(poseStack, buffer, packedLight, packedOverlay);
		body.render(poseStack, buffer, packedLight, packedOverlay);
		wing.render(poseStack, buffer, packedLight, packedOverlay);
		wing1.render(poseStack, buffer, packedLight, packedOverlay);
		rearleg.render(poseStack, buffer, packedLight, packedOverlay);
		rearleg1.render(poseStack, buffer, packedLight, packedOverlay);
		frontleg.render(poseStack, buffer, packedLight, packedOverlay);
		frontleg1.render(poseStack, buffer, packedLight, packedOverlay);
		tail.render(poseStack, buffer, packedLight, packedOverlay);
	}

	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
			float headPitch) {
		this.rearleg.xRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount;
		this.tail.yRot = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
		this.wing1.zRot = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
		this.neck.yRot = headPitch / (180F / (float) Math.PI);
		this.wing.zRot = Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * limbSwingAmount;
		this.rearleg1.xRot = Mth.cos(limbSwing * 0.6662F) * limbSwingAmount;
	}
}