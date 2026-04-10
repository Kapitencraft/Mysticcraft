package net.kapitencraft.mysticcraft.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;
import net.kapitencraft.kap_lib.mana.ManaAttributes;
import net.kapitencraft.kap_lib.mana.ManaHandler;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

public class ManaSupplyOverlay {
    private static final ResourceLocation MANA_BAR_BACKGROUND_SPRITE = MysticcraftMod.res("mana_bar_empty");
    private static final ResourceLocation MANA_BAR_PROGRESS_SPRITE = MysticcraftMod.res("mana_bar_full");

    private final Minecraft minecraft;
    private int manaBarVisibleDuration = 0;

    public ManaSupplyOverlay() {
        this.minecraft = Minecraft.getInstance();
        NeoForge.EVENT_BUS.register(this);
    }

    @SuppressWarnings("DataFlowIssue")
    public void maybeRenderManaSupplyBar(GuiGraphics graphics, DeltaTracker tracker) {
        int x = graphics.guiWidth() / 2 - 93;
        if (shouldShowBar()) { //some condition
            this.minecraft.getProfiler().push("expBar");
            int i = (int) this.minecraft.player.getAttributeValue(ManaAttributes.MAX_MANA);
            if (i > 0) {
                int k = (int)(ManaHandler.getMana(this.minecraft.player) * 183.0F / i);
                int l = graphics.guiHeight() - 32 + 3;
                RenderSystem.enableBlend();
                graphics.blitSprite(MANA_BAR_BACKGROUND_SPRITE, x, l, 186, 5);
                if (k > 0) {
                    graphics.blitSprite(MANA_BAR_PROGRESS_SPRITE, 186, 5, 0, 0, x, l, k, 5);
                }

                RenderSystem.disableBlend();
            }

            this.minecraft.getProfiler().pop();
        }
    }

    @SubscribeEvent
    public void onClientTick(ClientTickEvent.Post event) {
        if (manaBarVisibleDuration > 0)
            manaBarVisibleDuration--;
    }

    @SuppressWarnings("DataFlowIssue")
    private boolean shouldShowBar() {
        double mana = ManaHandler.getMana(this.minecraft.player);
        double maxMana = this.minecraft.player.getAttributeValue(ManaAttributes.MAX_MANA);
        return mana < maxMana; //regenerating
    }
}
