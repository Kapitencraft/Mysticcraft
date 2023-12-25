package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.client.font.effect.BaseGlyphEffect;
import net.kapitencraft.mysticcraft.client.font.effect.EffectSettings;
import net.kapitencraft.mysticcraft.client.font.effect.EffectsStyle;
import net.kapitencraft.mysticcraft.helpers.CollectionHelper;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.Stream;

@Mixin(Font.StringRenderOutput.class)
public abstract class StringRenderOutputMixin {

    @Accessor
    abstract float getR();
    @Accessor
    abstract float getG();
    @Accessor
    abstract float getB();

    @Accessor
    abstract float getX();
    @Accessor
    abstract float getY();
    @Accessor
    abstract boolean isDropShadow();
    @Accessor
    abstract void setX(float x);
    @Accessor
    abstract void setY(float y);

    @Inject(method = "accept", at = @At("HEAD"))
    public void accept(int index, Style style, int i, CallbackInfoReturnable<Boolean> returnable) {
        EffectsStyle effects = (EffectsStyle) style;
        if (effects.getEffects() != null) {
            EffectSettings settings = new EffectSettings();
            float r,g,b;
            r = getR();
            g = getG();
            b = getB();
            settings.r = getR();
            settings.g = getG();
            settings.b = getB();
            settings.x = getX();
            settings.y = getY();
            settings.isShadow = isDropShadow();
            settings.index = index;

            Stream<BaseGlyphEffect> effectStream = effects.getEffects().stream();
            CollectionHelper.sync(effectStream, settings, BaseGlyphEffect::apply);
            setX(settings.x);
            setY(settings.y);
            if (r != settings.r || g != settings.g || b != settings.b) {
                TextColor color = TextColor.fromRgb(MathHelper.RGBtoInt(settings.r, settings.g, settings.b));
                style = style.withColor(color);
            }
        }
    }
}