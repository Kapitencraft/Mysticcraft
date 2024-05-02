package net.kapitencraft.mysticcraft.mixin.classes.client;

import com.mojang.blaze3d.font.SheetGlyphInfo;
import net.kapitencraft.mysticcraft.mixin.duck.IChromatic;
import net.minecraft.client.gui.font.FontSet;
import net.minecraft.client.gui.font.FontTexture;
import net.minecraft.client.gui.font.glyphs.BakedGlyph;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FontSet.class)
public class FontSetMixin {

    @Redirect(method = "stitch", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/font/FontTexture;add(Lcom/mojang/blaze3d/font/SheetGlyphInfo;)Lnet/minecraft/client/gui/font/glyphs/BakedGlyph;"))
    private BakedGlyph addChromaToGlyph(FontTexture instance, SheetGlyphInfo pGlyphInfo) {
        BakedGlyph glyph = instance.add(pGlyphInfo);
        if (glyph != null) ((IChromatic) glyph).setChromaType(((IChromatic) instance).getChromaType());
        return glyph;
    }
}
