package net.kapitencraft.mysticcraft.gui.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kapitencraft.mysticcraft.helpers.MathHelper;

import java.util.function.Predicate;

public class SelectColorWidget extends PositionedWidget {
    private static final int COLOR_SIZE = 5;
    private static final Predicate<String> RGBA_PREDICATE = MathHelper.checkForInteger(0, 255),
            HUE_PREDICATE = MathHelper.checkForInteger(0, 300),
            SV_PREDICATE = MathHelper.checkForInteger(0, 100);

    protected SelectColorWidget(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {

    }
}
