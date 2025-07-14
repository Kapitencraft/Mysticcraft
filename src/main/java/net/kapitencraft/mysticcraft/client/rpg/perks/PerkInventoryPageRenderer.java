package net.kapitencraft.mysticcraft.client.rpg.perks;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import net.kapitencraft.kap_lib.inventory.page_renderer.InventoryPageRenderer;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.rpg.perks.Perk;
import net.kapitencraft.mysticcraft.rpg.perks.PerkInventoryPage;
import net.kapitencraft.mysticcraft.rpg.perks.PerkTree;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Map;

public class PerkInventoryPageRenderer implements InventoryPageRenderer, ClientPerks.Listener {
    private static final Component VERY_SAD_LABEL = Component.translatable("advancements.sad_label");
    private static final ResourceLocation BACKGROUND_LOCATION = MysticcraftMod.res("textures/gui/perks/page_background.png");
    private static final ResourceLocation WINDOW_LOCATION = new ResourceLocation("textures/gui/advancements/window.png");
    public static final ResourceLocation TABS_LOCATION = new ResourceLocation("textures/gui/advancements/trees.png");
    private static final Component TITLE = Component.translatable("gui.perks");

    private final Map<Perk, PerkTab> tabs = Maps.newLinkedHashMap();
    private static int tabPage, maxPages;
    PerkTab selectedTab;
    private final PerkInventoryPage page;

    public PerkInventoryPageRenderer(PerkInventoryPage page) {
        this.page = page;
        ClientPerks.getInstance().setListener(this);
    }

    @Override
    public void render(GuiGraphics graphics, Minecraft minecraft, int mouseX, int mouseY, float mouseXOld, float mouseYOld, int leftPos, int topPos) {
        renderInside(graphics, minecraft.font, mouseX, mouseY, leftPos, topPos);
        renderWindow(graphics, minecraft.font, leftPos, topPos);
        renderTooltips(graphics, minecraft.font, mouseX, mouseY, leftPos, topPos);
    }

    private void renderInside(GuiGraphics pGuiGraphics, Font font, int pMouseX, int pMouseY, int pOffsetX, int pOffsetY) {
        PerkTab tab = this.selectedTab;
        if (tab == null) {
            pGuiGraphics.fill(pOffsetX + 9, pOffsetY + 18, pOffsetX + 9 + 158, pOffsetY + 18 + 140, -16777216);
            int i = pOffsetX + 9 + 79;
            pGuiGraphics.drawCenteredString(font, Component.translatable("perks.empty"), i, pOffsetY + 18 + 70 - 9 / 2, -1);
            pGuiGraphics.drawCenteredString(font, VERY_SAD_LABEL, i, pOffsetY + 18 + 140 - 9, -1);
        } else {
            tab.drawContents(pGuiGraphics, pOffsetX + 9, pOffsetY + 18);
        }
    }

    public void renderWindow(GuiGraphics pGuiGraphics, Font font, int pOffsetX, int pOffsetY) {
        RenderSystem.enableBlend();
        if (this.tabs.size() > 1) {
            for(PerkTab tab : this.tabs.values()) {
                if (tab.getPage() == tabPage)
                    tab.drawTab(pGuiGraphics, pOffsetX, pOffsetY, tab == this.selectedTab);
            }

            for(PerkTab tab : this.tabs.values()) {
                if (tab.getPage() == tabPage)
                    tab.drawIcon(pGuiGraphics, pOffsetX, pOffsetY);
            }
        }

        Component title = this.selectedTab != null ? this.selectedTab.getTreeTitle() : TITLE;

        pGuiGraphics.drawString(font, title, pOffsetX + 8, pOffsetY + 6, 4210752, false);
        if (selectedTab != null) {
            Component toRender = Component.translatable("gui.perks.token_available", this.selectedTab.getAvailable());
            int width = font.width(toRender);
            pGuiGraphics.drawString(font, toRender, pOffsetX + 170 - width, pOffsetY + 6, 0x5600F4, false);
        }
    }

    private void renderTooltips(GuiGraphics pGuiGraphics, Font font, int pMouseX, int pMouseY, int pOffsetX, int pOffsetY) {
        if (this.selectedTab != null) {
            pGuiGraphics.pose().pushPose();
            pGuiGraphics.pose().translate((float)(pOffsetX + 9), (float)(pOffsetY + 18), 400.0F);
            RenderSystem.enableDepthTest();
            this.selectedTab.drawTooltips(pGuiGraphics, pMouseX - pOffsetX - 9, pMouseY - pOffsetY - 18, pOffsetX, pOffsetY);
            RenderSystem.disableDepthTest();
            pGuiGraphics.pose().popPose();
        }

        if (this.tabs.size() > 1) {
            for(PerkTab tab : this.tabs.values()) {
                if (tab.getPage() == tabPage && tab.isMouseOver(pMouseX - pOffsetX, pMouseY - pOffsetY)) {
                    pGuiGraphics.renderTooltip(font, tab.getTitle(), pMouseX, pMouseY);
                }
            }
        }
    }

    @Override
    public void init(int leftPos, int topPos) {
        if (!this.tabs.isEmpty()) this.selectedTab = this.tabs.values().iterator().next();
    }

    @Override
    public @NotNull ResourceLocation pageBackgroundLocation() {
        return BACKGROUND_LOCATION;
    }

    public void addPerkPage(PerkTree perk) {
        PerkTab tab = PerkTab.create(Minecraft.getInstance(), this.tabs.size(), perk);
        if (tab != null) this.tabs.put(perk.root(), tab);
    }

    @Override
    public boolean onMouseClicked(int relativeX, int relativeY, int pButton) {
        if (pButton == 0) {

            if (this.selectedTab != null) {
                this.selectedTab.onClick(relativeX - 9, relativeY - 18);
            }

            for (PerkTab perkTab : this.tabs.values()) {
                if (perkTab.getPage() == tabPage && perkTab.isMouseOver(relativeX, relativeY)) {
                    this.selectedTab = this.tabs.get(perkTab.getPerk());
                    break;
                }
            }
            return true;
        }
        return false;
    }


    @Nullable
    public PerkWidget getPerkWidget(Perk pAdvancement) {
        PerkTab tab = this.getTab(pAdvancement);
        return tab == null ? null : tab.getWidget(pAdvancement);
    }

    @Nullable
    private PerkTab getTab(Perk pPerk) {
        while(pPerk.getParent() != null) {
            pPerk = pPerk.getParent();
        }

        return this.tabs.get(pPerk);
    }


    @Override
    public void onTreeAdded(PerkTree root) {
        this.addPerkPage(root);
    }

    @Override
    public void onLeaveAdded(Perk perk) {
        PerkTab tab = this.getTab(perk);
        if (tab != null) tab.addPerk(perk);
    }

    @Override
    public void onAvailableChange(PerkTree tree, Integer value) {
        PerkTab tab = this.getTab(tree.root());
        if (tab != null) tab.setAvailable(value);
    }

    @Override
    public void onUpdateProgress(Perk perk, int progress) {
        PerkWidget perkWidget = this.getPerkWidget(perk);
        if (perkWidget != null) perkWidget.setProgress(progress);
    }
}
