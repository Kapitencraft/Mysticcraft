package net.kapitencraft.mysticcraft.mixin.classes.client;

import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
import org.joml.Vector2i;
import org.joml.Vector2ic;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(DefaultTooltipPositioner.class)
public abstract class ClientTooltipPositionerMixin implements ClientTooltipPositioner {

    @Override
    public Vector2ic positionTooltip(int pScreenWidth, int pScreenHeight, int pMouseX, int pMouseY, int pTooltipWidth, int pTooltipHeight) {
        if (pMouseX + pTooltipWidth > pScreenWidth) {
            pMouseX = Math.max(pMouseX - 24 - pTooltipWidth, 4);
        }

        return new Vector2i(pMouseX, pMouseY);
    }
}