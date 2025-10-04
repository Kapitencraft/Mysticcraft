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

    public static ShaderInstance getRendertypeChromaticCutoutShader() {
        return Objects.requireNonNull(rendertypeChromaticCutoutShader, "attempted to get Rendertype Chroma before load");
    }

    @SubscribeEvent
    public static void createShaders(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceProvider(), MysticcraftMod.res("rendertype_chromatic_cutout"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), shaderInstance -> rendertypeChromaticCutoutShader = shaderInstance);
    }
}