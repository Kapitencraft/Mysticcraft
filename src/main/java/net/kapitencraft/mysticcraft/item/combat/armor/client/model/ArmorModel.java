package net.kapitencraft.mysticcraft.item.combat.armor.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class ArmorModel extends EntityModel<LivingEntity> {
    protected static final CubeDeformation NULL_DEFORM = new CubeDeformation(0);
    public final ModelPart armorHead;
    public final ModelPart armorChest;
    public final ModelPart armorRightArm;
    public final ModelPart armorLeftArm;
    public final ModelPart armorRightLeg;
    public final ModelPart armorLeftLeg;
    public final ModelPart armorRightBoot;
    public final ModelPart armorLeftBoot;



    public ArmorModel(ModelPart root) {
        armorHead = root.getChild("armorHead");
        armorChest = root.getChild("armorBody");
        armorRightArm = root.getChild("armorRightArm");
        armorLeftArm = root.getChild("armorLeftArm");
        armorRightLeg = root.getChild("armorRightLeg");
        armorLeftLeg = root.getChild("armorLeftLeg");
        armorRightBoot = root.getChild("armorRightBoot");
        armorLeftBoot = root.getChild("armorLeftBoot");
    }

    @Override
    public void setupAnim(@NotNull LivingEntity p_102618_, float p_102619_, float p_102620_, float p_102621_, float p_102622_, float p_102623_) {

    }

    @Override
    public void renderToBuffer(@NotNull PoseStack stack, @NotNull VertexConsumer consumer, int packedLight, int packedOverlay, float r, float g, float b, float a) {
        armorHead.render(stack, consumer, packedLight, packedOverlay, r, g, b, a);
        armorChest.render(stack, consumer, packedLight, packedOverlay, r, g, b, a);
        armorRightArm.render(stack, consumer, packedLight, packedOverlay, r, g, b, a);
        armorLeftArm.render(stack, consumer, packedLight, packedOverlay, r, g, b, a);
        armorRightLeg.render(stack, consumer, packedLight, packedOverlay, r, g, b, a);
        armorLeftLeg.render(stack, consumer, packedLight, packedOverlay, r, g, b, a);
        armorRightBoot.render(stack, consumer, packedLight, packedOverlay, r, g, b, a);
        armorLeftBoot.render(stack, consumer, packedLight, packedOverlay, r, g, b, a);
    }

    public void makeInvisible(boolean invisible) {
        armorHead.visible = !invisible;
        armorChest.visible = !invisible;
        armorRightArm.visible = !invisible;
        armorLeftArm.visible = !invisible;
        armorRightLeg.visible = !invisible;
        armorLeftLeg.visible = !invisible;
        armorRightBoot.visible = !invisible;
        armorLeftBoot.visible = !invisible;
    }
}
