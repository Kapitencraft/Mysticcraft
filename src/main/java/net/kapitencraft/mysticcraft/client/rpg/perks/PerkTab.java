package net.kapitencraft.mysticcraft.client.rpg.perks;

import com.google.common.collect.Maps;
import net.kapitencraft.mysticcraft.rpg.perks.DisplayInfo;
import net.kapitencraft.mysticcraft.rpg.perks.Perk;
import net.kapitencraft.mysticcraft.rpg.perks.PerkTree;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.Map;

@OnlyIn(Dist.CLIENT)
public class PerkTab {
   private final Minecraft minecraft;
   private final PerkTabType type;
   private final int index;
   private final PerkTree tree;
   private final Perk perk;
   private final DisplayInfo display;
   private final ItemStack icon;
   private final Component treeTitle;
   private final Component title;
   private final PerkWidget root;
   private final Map<Perk, PerkWidget> widgets = Maps.newLinkedHashMap();
   private double scrollX;
   private double scrollY;
   private int minX = Integer.MAX_VALUE;
   private int minY = Integer.MAX_VALUE;
   private int maxX = Integer.MIN_VALUE;
   private int maxY = Integer.MIN_VALUE;
   private float fade;
   private boolean centered;
   private int page;
   private int tokensAvailable;

   PerkTab(Minecraft pMinecraft, PerkTabType pType, int pIndex, PerkTree tree, Perk perk, DisplayInfo pDisplay) {
      this.minecraft = pMinecraft;
      this.type = pType;
      this.index = pIndex;
      this.tree = tree;
      this.perk = perk;
      this.display = pDisplay;
      this.icon = tree.icon();
      this.title = perk.getTitle();
      this.treeTitle = this.tree.getTitle();
      this.root = new PerkWidget(this, pMinecraft, perk, pDisplay);
      this.addWidget(this.root, perk);
   }

   PerkTab(Minecraft mc, PerkTabType type, int index, int page, Perk adv, DisplayInfo info, PerkTree tree) {
      this(mc, type, index, tree, adv, info);
      this.page = page;
   }

   public int getPage() {
      return page;
   }

   PerkTabType getType() {
      return this.type;
   }

   public int getIndex() {
      return this.index;
   }

   public Perk getPerk() {
      return this.perk;
   }

   public Component getTitle() {
      return this.title;
   }

   public DisplayInfo getDisplay() {
      return this.display;
   }

   public void drawTab(GuiGraphics pGuiGraphics, int pOffsetX, int pOffsetY, boolean pIsSelected) {
      this.type.draw(pGuiGraphics, pOffsetX, pOffsetY, pIsSelected, this.index);
   }

   public void drawIcon(GuiGraphics pGuiGraphics, int pOffsetX, int pOffsetY) {
      this.type.drawIcon(pGuiGraphics, pOffsetX, pOffsetY, this.index, this.icon);
   }

   public void drawContents(GuiGraphics pGuiGraphics, int pX, int pY) {
      if (!this.centered) {
         this.scrollX = 117 - (this.maxX + this.minX) / 2d;
         this.scrollY = 56 - (this.maxY + this.minY) / 2d;
         this.centered = true;
      }

      pGuiGraphics.enableScissor(pX, pY, pX + 158, pY + 140);
      pGuiGraphics.pose().pushPose();
      pGuiGraphics.pose().translate((float)pX, (float)pY, 0.0F);
      ResourceLocation resourcelocation = this.tree.background();
      int i = Mth.floor(this.scrollX);
      int j = Mth.floor(this.scrollY);
      int k = i % 16;
      int l = j % 16;

      for(int i1 = -1; i1 <= 15; ++i1) {
         for(int j1 = -1; j1 <= 8; ++j1) {
            pGuiGraphics.blit(resourcelocation, k + 16 * i1, l + 16 * j1, 0.0F, 0.0F, 16, 16, 16, 16);
         }
      }

      this.root.drawConnectivity(pGuiGraphics, i, j, true);
      this.root.drawConnectivity(pGuiGraphics, i, j, false);
      this.root.draw(pGuiGraphics, i, j);
      pGuiGraphics.pose().popPose();
      pGuiGraphics.disableScissor();
   }

   public void drawTooltips(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, int pOffsetX, int pOffsetY) {
      pGuiGraphics.pose().pushPose();
      pGuiGraphics.pose().translate(0.0F, 0.0F, -200.0F);
      pGuiGraphics.fill(0, 0, 158, 140, Mth.floor(this.fade * 255.0F) << 24);
      boolean flag = false;
      int i = Mth.floor(this.scrollX);
      int j = Mth.floor(this.scrollY);
      if (pMouseX > 0 && pMouseX < 234 && pMouseY > 0 && pMouseY < 113) {
         for(PerkWidget widget : this.widgets.values()) {
            if (widget.isMouseOver(i, j, pMouseX, pMouseY)) {
               flag = true;
               widget.drawHover(pGuiGraphics, i, j, this.fade, pOffsetX, pOffsetY);
               break;
            }
         }
      }

      pGuiGraphics.pose().popPose();
      if (flag) {
         this.fade = Mth.clamp(this.fade + 0.02F, 0.0F, 0.3F);
      } else {
         this.fade = Mth.clamp(this.fade - 0.04F, 0.0F, 1.0F);
      }

   }

   public boolean isMouseOver(double pMouseX, double pMouseY) {
      return this.type.isMouseOver(this.index, pMouseX, pMouseY);
   }

   @Nullable
   public static PerkTab create(Minecraft pMinecraft, int pTabIndex, PerkTree tree) {
       for (PerkTabType tabType : PerkTabType.values()) {
           if ((pTabIndex % PerkTabType.MAX_TABS) < tabType.getMax()) {
               return new PerkTab(pMinecraft, tabType, pTabIndex % PerkTabType.MAX_TABS,  pTabIndex / PerkTabType.MAX_TABS, tree.root(), tree.root().getDisplay(), tree);
           }
           pTabIndex -= tabType.getMax();
       }
       return null;
   }

   public void scroll(double pDragX, double pDragY) {
      if (this.maxX - this.minX > 234) {
         this.scrollX = Mth.clamp(this.scrollX + pDragX, -(this.maxX - 234), 0.0D);
      }

      if (this.maxY - this.minY > 113) {
         this.scrollY = Mth.clamp(this.scrollY + pDragY, -(this.maxY - 113), 0.0D);
      }

   }

   public void addPerk(Perk perk) {
       PerkWidget widget = new PerkWidget(this, this.minecraft, perk, perk.getDisplay());
       this.addWidget(widget, perk);
   }

   private void addWidget(PerkWidget pWidget, Perk perk) {
      this.widgets.put(perk, pWidget);
      int i = pWidget.getX();
      int j = i + 28;
      int k = pWidget.getY();
      int l = k + 27;
      this.minX = Math.min(this.minX, i);
      this.maxX = Math.max(this.maxX, j);
      this.minY = Math.min(this.minY, k);
      this.maxY = Math.max(this.maxY, l);

      for(PerkWidget widget : this.widgets.values()) {
         widget.attachToParent();
      }

   }

   @Nullable
   public PerkWidget getWidget(Perk perk) {
      return this.widgets.get(perk);
   }

   public void onClick(int relativeX, int relativeY) {
      int i = Mth.floor(this.scrollX);
      int j = Mth.floor(this.scrollY);
      for (PerkWidget value : this.widgets.values()) {
         if (value.isMouseOver(i, j, relativeX, relativeY)) {
            value.sendPerkUpgradePacket();
            break;
         }
      }
   }

   public Component getTreeTitle() {
      return treeTitle;
   }

   public void setAvailable(Integer value) {
      this.tokensAvailable = value;
   }

   public int getAvailable() {
      return this.tokensAvailable;
   }

   public PerkTree getTree() {
      return this.tree;
   }
}
