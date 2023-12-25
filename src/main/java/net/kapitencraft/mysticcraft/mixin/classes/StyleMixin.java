package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.client.font.effect.BaseGlyphEffect;
import net.kapitencraft.mysticcraft.client.font.effect.EffectsStyle;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

@Mixin(Style.class)
public abstract class StyleMixin implements EffectsStyle {

    @SuppressWarnings("all")
    private final List<BaseGlyphEffect> effects = new ArrayList<>();

    @SuppressWarnings("all")
    public void addEffect(BaseGlyphEffect effect) {
        effects.add(effect);
    }

    @Unique
    public List<BaseGlyphEffect> getEffects() {
        return effects;
    }
}