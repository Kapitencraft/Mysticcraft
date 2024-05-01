package net.kapitencraft.mysticcraft.client.font;

import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;

public class ChromaFontSet extends FontSet {
    public ChromaFontSet(TextureManager pTextureManager, ResourceLocation pName) {
        super(pTextureManager, pName);
    }

    @Override
    public BakedGlyph getGlyph(int pCharacter) {
        return super.getGlyph(pCharacter);
    }
}
