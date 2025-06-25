package net.kapitencraft.mysticcraft.client.shader;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import org.lwjgl.opengl.GL11;

import java.util.OptionalDouble;

public class ModRenderTypes extends RenderType {
    public ModRenderTypes(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
        super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
    }

    public static final RenderType OVERLAY_LINES = create("overlay_lines",
            DefaultVertexFormat.POSITION_COLOR,
            VertexFormat.Mode.LINES,
            GL11.GL_LINES,
            false,
            false,
            RenderType.CompositeState.builder()
                    .setLineState(new LineStateShard(OptionalDouble.of(2)))
                    .setShaderState(ShaderStateShard.RENDERTYPE_LINES_SHADER)
                    .setDepthTestState(RenderStateShard.EQUAL_DEPTH_TEST)
                    .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                    .createCompositeState(false)
    );
}
