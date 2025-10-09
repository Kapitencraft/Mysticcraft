package net.kapitencraft.mysticcraft.client.shader;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Objects;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModShaders {

    @Nullable
    private static ShaderInstance rendertypeChromaticCutoutShader;
    private static ShaderInstance rendertypeChromaticCutoutEntityShader;
    private static ShaderInstance rendertypeChromaticCutoutNoiseShader;
    private static ShaderInstance rendertypeChromaticCutoutNoiseEntityShader;

    public static ShaderInstance getRendertypeChromaticCutoutShader() {
        return Objects.requireNonNull(rendertypeChromaticCutoutShader, "attempted to get Rendertype Chroma before load");
    }

    public static ShaderInstance getRendertypeChromaticCutoutEntityShader() {
        return Objects.requireNonNull(rendertypeChromaticCutoutEntityShader, "attempted to get Rendertype Chroma Entity before load");
    }

    public static ShaderInstance getRendertypeChromaticCutoutNoiseShader() {
        return Objects.requireNonNull(rendertypeChromaticCutoutNoiseShader, "attempted to get Rendertype Chroma Noise before load");
    }

    public static ShaderInstance getRendertypeChromaticCutoutNoiseEntityShader() {
        return Objects.requireNonNull(rendertypeChromaticCutoutNoiseEntityShader, "attempted to get Rendertype Chroma Noise Entity before load");
    }

    @SubscribeEvent
    public static void createShaders(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceProvider(), MysticcraftMod.res("chromatic/cutout"), DefaultVertexFormat.BLOCK), shaderInstance -> rendertypeChromaticCutoutShader = shaderInstance);
        event.registerShader(new ShaderInstance(event.getResourceProvider(), MysticcraftMod.res("chromatic/cutout_entity"), DefaultVertexFormat.NEW_ENTITY), shaderInstance -> rendertypeChromaticCutoutEntityShader = shaderInstance);
        event.registerShader(new ShaderInstance(event.getResourceProvider(), MysticcraftMod.res("chromatic/cutout_noise"), DefaultVertexFormat.BLOCK), shaderInstance -> rendertypeChromaticCutoutNoiseShader = shaderInstance);
        event.registerShader(new ShaderInstance(event.getResourceProvider(), MysticcraftMod.res("chromatic/cutout_noise_entity"), DefaultVertexFormat.NEW_ENTITY), shaderInstance -> rendertypeChromaticCutoutNoiseEntityShader = shaderInstance);
    }
}