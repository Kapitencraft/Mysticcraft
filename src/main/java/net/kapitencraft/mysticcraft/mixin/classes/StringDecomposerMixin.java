package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.client.font.effect.GlyphEffect;
import net.kapitencraft.mysticcraft.config.ClientModConfig;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModGlyphEffects;
import net.kapitencraft.mysticcraft.misc.visuals.Pingable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.FormattedCharSink;
import net.minecraft.util.StringDecomposer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(StringDecomposer.class)
public class StringDecomposerMixin {

    /**
     * @author Kapitencraft
     * @reason custom display effect application
     */
    @Overwrite
    public static boolean iterateFormatted(String s, int length, Style style, Style style2, FormattedCharSink sink) {
        int i = s.length();
        Style formattedStyle = style;

        TextColor nonPingColor = null;
        boolean pinged = false;
        for(int j = length; j < i; ++j) {

            char c0 = s.charAt(j);
            //Ping start
            if (c0 == '@') {
                int k = j + 1;
                while (s.length() > k && s.charAt(k) != ' ') k++;
                String name = s.substring(j + 1, k);
                if (Pingable.isPinged(name)) {
                    nonPingColor = formattedStyle.getColor();
                    formattedStyle = formattedStyle.withColor(ClientModConfig.getPingColor());
                    pinged = true;
                }
            }
            if (c0 == ' ' && pinged) {
                formattedStyle = formattedStyle.withColor(nonPingColor);
                pinged = false;
            }
            //Ping end
            if (c0 == 167) {
                j++;
                if (j >= i) {
                    break;
                }

                char c1 = s.charAt(j);
                ChatFormatting chatformatting = ChatFormatting.getByCode(c1);
                if (chatformatting != null) {
                    formattedStyle = chatformatting == ChatFormatting.RESET ? style2 : formattedStyle.applyLegacyFormat(chatformatting);
                } else if (ModGlyphEffects.effectsForKey().containsKey(c1)) {
                    GlyphEffect effect = ModGlyphEffects.effectsForKey().get(c1);
                    formattedStyle = MiscHelper.withSpecial(formattedStyle, effect);
                }
            } else if (Character.isHighSurrogate(c0)) {
                if (j + 1 >= i) {
                    if (!sink.accept(j, formattedStyle, 65533)) {
                        return false;
                    }
                    break;
                }

                char c2 = s.charAt(j + 1);
                if (Character.isLowSurrogate(c2)) {
                    if (!sink.accept(j, formattedStyle, Character.toCodePoint(c0, c2))) {
                        return false;
                    }

                    ++j;
                } else if (!sink.accept(j, formattedStyle, 65533)) {
                    return false;
                }
            } else if (!feedChar(formattedStyle, sink, j, c0)) {
                return false;
            }
        }

        return true;
    }

    @Shadow
    private static boolean feedChar(Style style, FormattedCharSink sink, int j, char c) {return false;}
}