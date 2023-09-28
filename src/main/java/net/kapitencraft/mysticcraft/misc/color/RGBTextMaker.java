package net.kapitencraft.mysticcraft.misc.color;

import net.kapitencraft.mysticcraft.config.ClientModConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

import java.util.ArrayList;
import java.util.List;

public class RGBTextMaker {
    private static final List<RGB255Color> colors = new ArrayList<>();

    private static int cur = 0;

    private static void generateColors() {
        int r = 255;
        int g = 0;
        int b = 0;
        while (r > 0) {
            r-= ClientModConfig.rgbSpeed;
            g+= ClientModConfig.rgbSpeed;
            if (r < 0) {
                r = 0;
            }
            if (g > 255) {
                g = 255;
            }
            colors.add(new RGB255Color(r, g, b));
        }
        while (g > 0) {
            g-= ClientModConfig.rgbSpeed;
            b+= ClientModConfig.rgbSpeed;
            if (g < 0) {
                g = 0;
            }
            if (b > 255) {
                b = 255;
            }
            colors.add(new RGB255Color(r, g, b));
        }
        while (b > 0) {
            b-= ClientModConfig.rgbSpeed;
            r+= ClientModConfig.rgbSpeed;
            if (b < 0) {
                b = 0;
            }
            if (r > 255) {
                r = 255;
            }
            colors.add(new RGB255Color(r, g, b));
        }
    }

    public static MutableComponent makeText(Component component, int start) {
        if (colors.isEmpty()) generateColors();
        String text = component.getString();
        MutableComponent toReturn = Component.literal("");
        cur = start - 1;
        for (char s : text.toCharArray()) {
            int color = colors.get(next()).toInt();
            toReturn.append(Component.literal(String.valueOf(s)).withStyle(Style.EMPTY.withColor(color)));
        }
        return toReturn;
    }

    private static int next() {
        if (cur++ >= colors.size()) {
            cur = 0;
        }
        return cur;
    }
}
