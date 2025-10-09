package net.kapitencraft.mysticcraft.client.shader;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;

import java.util.OptionalDouble;

public class ModRenderTypes extends RenderType {
    public ModRenderTypes(String pName, VertexFormat pFormat, VertexFormat.Mode pMode, int pBufferSize, boolean pAffectsCrumbling, boolean pSortOnUpload, Runnable pSetupState, Runnable pClearState) {
        super(pName, pFormat, pMode, pBufferSize, pAffectsCrumbling, pSortOnUpload, pSetupState, pClearState);
    }

    public static final RenderType OVERLAY_LINES = create("mysticcraft:overlay_lines",
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

    //thanks to BlakeBr0 for the rendertype under the MIT licence
    //https://github.com/BlakeBr0/Cucumber/blob/1.2!/src/main/java/com/blakebr0/cucumber/client/ModRenderTypes.java
    private static final RenderType.TransparencyStateShard GHOST_TRANSPARENCY = new RenderStateShard.TransparencyStateShard("ghost_transparency",
            () -> {
                RenderSystem.enableBlend();
                RenderSystem.blendFunc(GlStateManager.SourceFactor.CONSTANT_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
                GL14.glBlendColor(1.0F, 1.0F, 1.0F, 0.5F);
            },
            () -> {
                GL14.glBlendColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.disableBlend();
                RenderSystem.defaultBlendFunc();
            });

    public static final RenderType GHOST = RenderType.create(
            "mysticcraft:ghost",
            DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 2097152, true, false,
            RenderType.CompositeState.builder()
                    .setShaderState(RenderType.POSITION_COLOR_TEX_LIGHTMAP_SHADER)
                    .setTextureState(RenderType.BLOCK_SHEET)
                    .setTransparencyState(GHOST_TRANSPARENCY)
                    .createCompositeState(false)
    );
    private static final ShaderStateShard CHROMATIC_CUTOUT_SHARD = new ShaderStateShard(ModShaders::getRendertypeChromaticCutoutShader);
    private static final ShaderStateShard CHROMATIC_CUTOUT_ENTITY_SHARD = new ShaderStateShard(ModShaders::getRendertypeChromaticCutoutEntityShader);
    private static final ShaderStateShard CHROMATIC_CUTOUT_NOISE_SHARD = new ShaderStateShard(ModShaders::getRendertypeChromaticCutoutNoiseShader);
    private static final ShaderStateShard CHROMATIC_CUTOUT_NOISE_ENTITY_SHARD = new ShaderStateShard(ModShaders::getRendertypeChromaticCutoutNoiseEntityShader);

    public static final RenderType CHROMATIC_CUTOUT = RenderType.create(
            "mysticcraft:chromatic_cutout",
            DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 2097152, true, false,
            RenderType.CompositeState.builder()
                    .setShaderState(CHROMATIC_CUTOUT_SHARD)
                    .setTextureState(RenderType.BLOCK_SHEET)
                    .setTransparencyState(RenderStateShard.NO_TRANSPARENCY)
                    .createCompositeState(false)
    );

    public static final RenderType CHROMATIC_CUTOUT_ENTITY = RenderType.create(
            "mysticcraft:chromatic_cutout_entity",
            DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setShaderState(CHROMATIC_CUTOUT_ENTITY_SHARD)
                    .setTextureState(RenderType.BLOCK_SHEET)
                    .setTransparencyState(RenderStateShard.NO_TRANSPARENCY)
                    .createCompositeState(true)
    );

    public static final RenderType CHROMATIC_CUTOUT_NOISE = RenderType.create(
            "mysticcraft:chromatic_cutout_noise",
            DefaultVertexFormat.BLOCK, VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setShaderState(CHROMATIC_CUTOUT_NOISE_SHARD)
                    .setTextureState(RenderType.BLOCK_SHEET)
                    .setTransparencyState(RenderStateShard.NO_TRANSPARENCY)
                    .createCompositeState(false)
    );

    public static final RenderType CHROMATIC_CUTOUT_ENTITY_NOISE = RenderType.create(
            "mysticcraft:chromatic_cutout_noise_entity",
            DefaultVertexFormat.NEW_ENTITY, VertexFormat.Mode.QUADS, 256, true, false,
            RenderType.CompositeState.builder()
                    .setShaderState(CHROMATIC_CUTOUT_NOISE_ENTITY_SHARD)
                    .setTextureState(RenderType.BLOCK_SHEET)
                    .setTransparencyState(RenderStateShard.NO_TRANSPARENCY)
                    .createCompositeState(false)
    );
}