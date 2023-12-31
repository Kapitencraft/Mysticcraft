package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.client.font.effect.BaseGlyphEffect;
import net.kapitencraft.mysticcraft.client.font.effect.EffectSettings;
import net.kapitencraft.mysticcraft.client.font.effect.EffectsStyle;
import net.kapitencraft.mysticcraft.helpers.CollectionHelper;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.stream.Stream;

@Mixin(Font.StringRenderOutput.class)
public abstract class StringRenderOutputMixin {

    @Shadow
    public float r;

    @Shadow
    public float g;

    @Shadow
    public float b;

    @Shadow
    public
    float x;

    @Shadow
    public
    float y;

    @Accessor
    abstract boolean isDropShadow();

    @Inject(method = "accept", at = @At("HEAD"))
    public void accept(int index, Style style, int i, CallbackInfoReturnable<Boolean> returnable) {
        EffectsStyle effects = (EffectsStyle) style;
        if (effects.getEffects() != null && !effects.getEffects().isEmpty()) {
            EffectSettings settings = new EffectSettings();
            float r,g,b;
            r = this.r;
            g = this.g;
            b = this.b;
            settings.r = this.r;
            settings.g = this.g;
            settings.b = this.b;
            settings.x = this.x;
            settings.y = this.y;
            settings.isShadow = isDropShadow();
            settings.index = index;

            Stream<BaseGlyphEffect> effectStream = effects.getEffects().stream();
            CollectionHelper.sync(effectStream, settings, BaseGlyphEffect::apply);
            this.x = settings.x;
            this.y = settings.y;
            if (r != settings.r || g != settings.g || b != settings.b) {
                this.r = settings.r;
                this.g = settings.g;
                this.b = settings.b;
                style.color = null;
            }
        }
    }
}