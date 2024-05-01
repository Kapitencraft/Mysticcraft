package net.kapitencraft.mysticcraft.mixin.classes.client;

import net.kapitencraft.mysticcraft.client.shaders.ModRenderTypes;
import net.kapitencraft.mysticcraft.mixin.duck.IChromatic;
import net.minecraft.client.gui.font.FontTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(FontTexture.class)
public abstract class FontTextureMixin extends AbstractTexture implements IChromatic {
    private RenderType chromatic, seeThroughChromatic, polygonOffsetChromatic;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void addRenderTypes(ResourceLocation pName, boolean pColored, CallbackInfo ci) {
        setChromaType(ModRenderTypes.chromatic(pName));
    }

    @Override
    public void setChromaType(RenderType chromaType) {
        chromatic = chromaType;
    }

    @Override
    public RenderType getChromaType() {
        return chromatic;
    }
}
