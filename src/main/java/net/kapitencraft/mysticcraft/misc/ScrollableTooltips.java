package net.kapitencraft.mysticcraft.misc;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Vector2i;

import java.util.List;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class ScrollableTooltips {
    private static final int scrollScale = 5;
    private static int scrollY = 0;
    private static int initY = 0;
    private static int scale = 1;
    private static ItemStack stack = ItemStack.EMPTY;

    @SubscribeEvent
    public static void registerScrollable(RenderTooltipEvent.Pre event) {
        Vector2i toolTipSize = createToolTipBoxSize(event.getComponents(), event.getFont());
        Vector2i screenSize = new Vector2i(event.getScreenWidth(), event.getScreenHeight());
        Vector2i pos = new Vector2i(event.getX(), event.getY());
        boolean isHigherThanScreen = toolTipSize.y > event.getScreenHeight();
        int height = event.getY();
        if (stack != event.getItemStack()) {
            scrollY = 0;
            stack = event.getItemStack();
        }
        if (scrollY == 0 || !isHigherThanScreen) {
            int i = toolTipSize.y + 3;
            if (pos.y + i > screenSize.y) {
                height = screenSize.y - i;
            }
        }
        if (isHigherThanScreen) {
            if (scrollY == 0) initY = height;
            event.setY(initY + scrollY);
            return;
        }
        event.setY(height);
    }

    @SubscribeEvent
    public static void scrollEvent(ScreenEvent.MouseScrolled.Post event) {
        if (stack != ItemStack.EMPTY) {
            if (Screen.hasControlDown()) {
                scale += event.getScrollDelta() * scrollScale;
            } else {
                scrollY += event.getScrollDelta() * scrollScale;
            }
        }
    }

    private static Vector2i createToolTipBoxSize(List<ClientTooltipComponent> components, Font font) {
        int i = 0, j = 0;
        for(ClientTooltipComponent clienttooltipcomponent : components) {
            int k = clienttooltipcomponent.getWidth(font);
            if (k > i) {
                i = k;
            }

            j += clienttooltipcomponent.getHeight();
        }
        return new Vector2i(i, j);
    }
}
