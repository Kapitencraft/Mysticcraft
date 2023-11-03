package net.kapitencraft.mysticcraft.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ModBERenderer extends BlockEntityWithoutLevelRenderer {
    private static final ModBERenderer INSTANCE = new ModBERenderer();
    private final ItemModelShaper models = Minecraft.getInstance().getItemRenderer().getItemModelShaper();
    public ModBERenderer() {
        super(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(@NotNull ItemStack p_108830_, ItemTransforms.@NotNull TransformType p_108831_, @NotNull PoseStack p_108832_, @NotNull MultiBufferSource p_108833_, int p_108834_, int p_108835_) {
        super.renderByItem(p_108830_, p_108831_, p_108832_, p_108833_, p_108834_, p_108835_);
    }
}
