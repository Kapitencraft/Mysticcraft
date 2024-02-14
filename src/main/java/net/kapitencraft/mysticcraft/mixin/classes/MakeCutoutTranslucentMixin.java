package net.kapitencraft.mysticcraft.mixin.classes;

import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Objects;

@Mixin(RenderType.class)
public abstract class MakeCutoutTranslucentMixin {

    @ModifyVariable(method = "create(Ljava/lang/String;Lcom/mojang/blaze3d/vertex/VertexFormat;Lcom/mojang/blaze3d/vertex/VertexFormat$Mode;IZZLnet/minecraft/client/renderer/RenderType$CompositeState;)Lnet/minecraft/client/renderer/RenderType$CompositeRenderType;", at = @At("HEAD"), argsOnly = true)
    private static RenderType.CompositeState modifyState(RenderType.CompositeState state, String name, VertexFormat p_173217_, VertexFormat.Mode p_173218_, int p_173219_, boolean p_173220_, boolean p_173221_, RenderType.CompositeState p_173222_) {
        if (Objects.equals(name, "cutout")) {
            return createNewCutout();
        }
        return state;
    }

    @SuppressWarnings("all")
    private static RenderType.CompositeState createNewCutout() {
        return RenderType.CompositeState.builder().setLightmapState(RenderStateShard.LIGHTMAP).setShaderState(RenderStateShard.RENDERTYPE_CUTOUT_SHADER).setTextureState(RenderStateShard.BLOCK_SHEET).setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY).createCompositeState(true);
    }
}
