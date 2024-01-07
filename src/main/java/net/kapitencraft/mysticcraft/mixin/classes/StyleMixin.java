package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.client.font.effect.EffectsStyle;
import net.kapitencraft.mysticcraft.client.font.effect.GlyphEffect;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

@Mixin(Style.class)
public abstract class StyleMixin implements EffectsStyle {

    @SuppressWarnings("all")
    private final List<GlyphEffect> effects = new ArrayList<>();

    @SuppressWarnings("all")
    public void addEffect(GlyphEffect effect) {
        effects.add(effect);
    }

    @Unique
    public List<GlyphEffect> getEffects() {
        return effects;
    }
}