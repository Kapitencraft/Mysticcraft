package net.kapitencraft.mysticcraft.mixin.classes;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DefaultTooltipPositioner.class)
public class ClientTooltipPositionerMixin implements ClientTooltipPositioner {

    @Override
    public @NotNull Vector2ic positionTooltip(@NotNull Screen screen, int x, int y, int toolTipWidth, int toolTipHeight) {
        if (x + toolTipWidth > screen.width) {
            x = Math.max(x - 24 - toolTipWidth, 4);
        }

        return new Vector2i(x, y);
    }
}