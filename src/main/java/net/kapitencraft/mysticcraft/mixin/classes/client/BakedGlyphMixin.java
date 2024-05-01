package net.kapitencraft.mysticcraft.mixin.classes.client;

import net.kapitencraft.mysticcraft.mixin.duck.IChromatic;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BakedGlyph.class)
public class BakedGlyphMixin implements IChromatic {
    private RenderType chromatic;

    @Override
    public RenderType getChromaType() {
        return chromatic;
    }

    @Override
    public void setChromaType(RenderType chromaType) {
        chromatic = chromaType;
    }
}
