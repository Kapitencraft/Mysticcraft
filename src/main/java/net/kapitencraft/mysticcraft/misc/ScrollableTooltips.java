package net.kapitencraft.mysticcraft.misc;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;

import java.util.List;

public class ScrollableTooltips {
    public static float scrollX;
    public static float scrollY;
    public static float zoomFactor = 1;
    public static boolean allowScrolling;

    public static void drawHoveringText(PoseStack poseStack, List<String> textLines, int screenHeight, int tooltipY, int tooltipHeight) {
        allowScrolling = tooltipY < 0;
        int eventDWheel = 0;
        if (Screen.hasControlDown()) {
            zoomFactor *= (1.0 + 0.1 * Integer.signum(eventDWheel));
        } else if (Screen.hasShiftDown()) {
            if (eventDWheel < 0) {
                scrollX += 10;
            } else if (eventDWheel > 0) {
                //Scrolling to access higher stuff
                scrollX -= 10;
            }
        } else {
            if (eventDWheel < 0) {
                scrollY -= 10;
            } else if (eventDWheel > 0) {
                //Scrolling to access higher stuff
                scrollY += 10;
            }
        }
        if (scrollY + tooltipY > 6) {
            scrollY = -tooltipY + 6;
        } else if (scrollY + tooltipY + tooltipHeight + 6 < screenHeight) {
            scrollY = screenHeight - 6 - tooltipY - tooltipHeight;
        }
        poseStack.translate(scrollX, scrollY, 0);
        poseStack.scale(zoomFactor, zoomFactor, 0.0f);
    }
}
