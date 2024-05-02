package net.kapitencraft.mysticcraft.client.shaders;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Objects;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModShaders {

    @Nullable
    private static ShaderInstance rendertypeChromaShader;

    public static ShaderInstance getRendertypeChromaShader() {
        return Objects.requireNonNull(rendertypeChromaShader, "attempted to get Chroma shader before load");
    }

    @SubscribeEvent
    public static void createShaders(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceProvider(), MysticcraftMod.res("rendertype_chroma"), DefaultVertexFormat.POSITION_COLOR_TEX_LIGHTMAP), shaderInstance -> rendertypeChromaShader = shaderInstance);
    }
}