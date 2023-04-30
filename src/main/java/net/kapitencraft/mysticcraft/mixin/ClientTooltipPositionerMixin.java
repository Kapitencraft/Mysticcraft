package net.kapitencraft.mysticcraft.mixin;

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
    public Vector2ic positionTooltip(@NotNull Screen p_263072_, int x, int y, int toolTipWidth, int toolTipHeight) {
        return new Vector2i(x, y);
    }
}