package net.kapitencraft.mysticcraft.client.rpg.perks;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import net.kapitencraft.mysticcraft.network.ModMessages;
import net.kapitencraft.mysticcraft.network.packets.C2S.UpgradePerkPacket;
import net.kapitencraft.mysticcraft.rpg.perks.DisplayInfo;
import net.kapitencraft.mysticcraft.rpg.perks.Perk;
import net.minecraft.client.Minecraft;
import net.minecraft.client.StringSplitter;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.advancements.AdvancementWidgetType;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class PerkWidget {
   private static final ResourceLocation WIDGETS_LOCATION = new ResourceLocation("textures/gui/advancements/widgets.png");
   private static final int HEIGHT = 26;
   private static final int BOX_X = 0;
   private static final int BOX_WIDTH = 200;
   private static final int FRAME_WIDTH = 26;
   private static final int ICON_X = 8;
   private static final int ICON_Y = 5;
   private static final int ICON_WIDTH = 26;
   private static final int TITLE_PADDING_LEFT = 3;
   private static final int TITLE_PADDING_RIGHT = 5;
   private static final int TITLE_X = 32;
   private static final int TITLE_Y = 9;
   private static final int TITLE_MAX_WIDTH = 163;
   private static final int[] TEST_SPLIT_OFFSETS = new int[]{0, 10, -10, 25, -25};
   private final PerkTab tab;
   private final Perk perk;
   private final DisplayInfo display;
   private final FormattedCharSequence title;
   private final int width;
   private final List<FormattedCharSequence> description;
   private final Minecraft minecraft;
   @Nullable
   private PerkWidget parent;
   private final List<PerkWidget> children = Lists.newArrayList();
   private int progress;
   private final int x;
   private final int y;

   public PerkWidget(PerkTab pTab, Minecraft pMinecraft, Perk perk, DisplayInfo pDisplay) {
      this.tab = pTab;
      this.perk = perk;
      this.display = pDisplay;
      this.minecraft = pMinecraft;
      this.title = Language.getInstance().getVisualOrder(pMinecraft.font.substrByWidth(perk.getTitle(), 163));
      this.x = Mth.floor(pDisplay.getX() * 27.0F);
      this.y = Mth.floor(pDisplay.getY() * 28.0F);
      int i = perk.getMaxLevel();
      int j = String.valueOf(i).length();
      int k = i > 1 ? pMinecraft.font.width("  ") + pMinecraft.font.width("0") * j * 2 + pMinecraft.font.width("/") : 0;
      int l = 29 + pMinecraft.font.width(this.title) + k;
      this.description = Language.getInstance().getVisualOrder(this.findOptimalLines(ComponentUtils.mergeStyles(perk.getDescription().copy(), Style.EMPTY.withColor(pDisplay.getFrame().getChatColor())), l));

      for(FormattedCharSequence formattedcharsequence : this.description) {
         l = Math.max(l, pMinecraft.font.width(formattedcharsequence));
      }

      this.width = l + 3 + 5;
   }

   private static float getMaxWidth(StringSplitter pManager, List<FormattedText> pText) {
      return (float)pText.stream().mapToDouble(pManager::stringWidth).max().orElse(0.0D);
   }

   private List<FormattedText> findOptimalLines(Component pComponent, int pMaxWidth) {
      StringSplitter stringsplitter = this.minecraft.font.getSplitter();
      List<FormattedText> list = null;
      float f = Float.MAX_VALUE;

      for(int i : TEST_SPLIT_OFFSETS) {
         List<FormattedText> list1 = stringsplitter.splitLines(pComponent, pMaxWidth - i, Style.EMPTY);
         float f1 = Math.abs(getMaxWidth(stringsplitter, list1) - (float)pMaxWidth);
         if (f1 <= 10.0F) {
            return list1;
         }

         if (f1 < f) {
            f = f1;
            list = list1;
         }
      }

      return list;
   }

   @Nullable
   private PerkWidget getFirstVisibleParent(Perk perk) {
       perk = perk.getParent();

       return perk != null ? this.tab.getWidget(perk) : null;
   }

   public void drawConnectivity(GuiGraphics pGuiGraphics, int pX, int pY, boolean pDropShadow) {
      if (this.parent != null) {
         int parentMiddleY = pY + this.parent.y + 13;
         int parentBottomY = pY + this.parent.y + 26 + 4;
         int parentMiddleX = pX + this.parent.x + 13;
         int middleY = pY + this.y + 13;
         int middleX = pX + this.x + 13;
         int color = pDropShadow ? -16777216 : -1;
         if (pDropShadow) {
            pGuiGraphics.vLine(parentMiddleX - 1, parentMiddleY, parentBottomY, color);
            pGuiGraphics.vLine(parentMiddleX, parentMiddleY, parentBottomY + 1, color);
            pGuiGraphics.vLine(parentMiddleX + 1, parentMiddleY, parentBottomY, color);

            pGuiGraphics.vLine(middleX - 1, middleY, parentBottomY - 1, color);
            pGuiGraphics.vLine(middleX, middleY,  parentBottomY - 1, color);
            pGuiGraphics.vLine(middleX + 1, middleY, parentBottomY - 1, color);

            pGuiGraphics.hLine(parentMiddleX, middleX, parentBottomY - 1, color);
            pGuiGraphics.hLine(parentMiddleX, middleX, parentBottomY + 1, color);
         } else {
            pGuiGraphics.vLine(parentMiddleX, parentMiddleY, parentBottomY, color);
            pGuiGraphics.vLine(middleX, middleY, parentBottomY, color);
            pGuiGraphics.hLine(parentMiddleX, middleX, parentBottomY, color);
         }
      }

      for(PerkWidget perkWidget : this.children) {
         perkWidget.drawConnectivity(pGuiGraphics, pX, pY, pDropShadow);
      }
   }

   public void draw(GuiGraphics pGuiGraphics, int pX, int pY) {
       float f = this.progress / (float) this.perk.getMaxLevel();
       AdvancementWidgetType advancementwidgettype;
       if (f >= 1.0F) {
          advancementwidgettype = AdvancementWidgetType.OBTAINED;
       } else {
          advancementwidgettype = AdvancementWidgetType.UNOBTAINED;
       }

       pGuiGraphics.blit(WIDGETS_LOCATION, pX + this.x, pY + this.y + 3, this.display.getFrame().getTexture(), 128 + advancementwidgettype.getIndex() * 26, 26, 26);
       pGuiGraphics.renderFakeItem(this.display.getIcon(), pX + this.x + 5, pY + this.y + 8);

       for(PerkWidget child : this.children) {
         child.draw(pGuiGraphics, pX, pY);
      }
   }

   public int getWidth() {
      return this.width;
   }

   public void setProgress(int pProgress) {
      this.progress = pProgress;
   }

   public void addChild(PerkWidget pAdvancementWidget) {
      this.children.add(pAdvancementWidget);
   }

   public void drawHover(GuiGraphics pGuiGraphics, int pX, int pY, float pFade, int pWidth, int pHeight) {
      boolean flag = false; // pWidth + pX + this.x + this.width + 26 >= this.tab.getScreen().width;
      String s = String.format("%d/%d", this.progress, this.perk.getMaxLevel());
      int i = this.minecraft.font.width(s);
      boolean flag1 = 113 - pY - this.y - 26 <= 6 + this.description.size() * 9;
      float f = this.progress / (float) this.perk.getMaxLevel();
      int j = Mth.floor(f * (float)this.width);
      AdvancementWidgetType type;
      AdvancementWidgetType type1;
      AdvancementWidgetType type2;
      if (f >= 1.0F) {
         j = this.width / 2;
         type = AdvancementWidgetType.OBTAINED;
         type1 = AdvancementWidgetType.OBTAINED;
         type2 = AdvancementWidgetType.OBTAINED;
      } else if (j < 2) {
         j = this.width / 2;
         type = AdvancementWidgetType.UNOBTAINED;
         type1 = AdvancementWidgetType.UNOBTAINED;
         type2 = AdvancementWidgetType.UNOBTAINED;
      } else if (j > this.width - 2) {
         j = this.width / 2;
         type = AdvancementWidgetType.OBTAINED;
         type1 = AdvancementWidgetType.OBTAINED;
         type2 = AdvancementWidgetType.UNOBTAINED;
      } else {
         type = AdvancementWidgetType.OBTAINED;
         type1 = AdvancementWidgetType.UNOBTAINED;
         type2 = AdvancementWidgetType.UNOBTAINED;
      }

      int k = this.width - j;
      RenderSystem.enableBlend();
      int l = pY + this.y + 3;
      int i1;
      if (flag) {
         i1 = pX + this.x - this.width + 26 + 6;
      } else {
         i1 = pX + this.x - 3;
      }

      int j1 = 32 + this.description.size() * 9;
      if (!this.description.isEmpty()) {
         if (flag1) {
            pGuiGraphics.blitNineSliced(WIDGETS_LOCATION, i1, l + 26 - j1, this.width, j1, 10, 200, 26, 0, 52);
         } else {
            pGuiGraphics.blitNineSliced(WIDGETS_LOCATION, i1, l, this.width, j1, 10, 200, 26, 0, 52);
         }
      }

      pGuiGraphics.blit(WIDGETS_LOCATION, i1, l, 0, type.getIndex() * 26, j, 26);
      pGuiGraphics.blit(WIDGETS_LOCATION, i1 + j, l, 200 - k, type1.getIndex() * 26, k, 26);
      pGuiGraphics.blit(WIDGETS_LOCATION, pX + this.x, pY + this.y + 3, this.display.getFrame().getTexture(), 128 + type2.getIndex() * 26, 26, 26);
      if (flag) {
         pGuiGraphics.drawString(this.minecraft.font, this.title, i1 + 5, pY + this.y + 9, -1);
         if (s != null) {
            pGuiGraphics.drawString(this.minecraft.font, s, pX + this.x - i, pY + this.y + 9, -1);
         }
      } else {
         pGuiGraphics.drawString(this.minecraft.font, this.title, pX + this.x + 29, pY + this.y + 12, -1);
         pGuiGraphics.drawString(this.minecraft.font, s, pX + this.x + this.width - i - 8, pY + this.y + 12, -1);
      }

      if (flag1) {
         for(int k1 = 0; k1 < this.description.size(); ++k1) {
            pGuiGraphics.drawString(this.minecraft.font, this.description.get(k1), i1 + 5, l + 26 - j1 + 7 + k1 * 9, -5592406, false);
         }
      } else {
         for(int l1 = 0; l1 < this.description.size(); ++l1) {
            pGuiGraphics.drawString(this.minecraft.font, this.description.get(l1), i1 + 5, pY + this.y + 12 + 17 + l1 * 9, -5592406, false);
         }
      }

      pGuiGraphics.renderFakeItem(this.display.getIcon(), pX + this.x + 5, pY + this.y + 8);
   }

   public boolean isMouseOver(int pX, int pY, int pMouseX, int pMouseY) {
       int i = pX + this.x;
       int j = i + 26;
       int k = pY + this.y;
       int l = k + 26;
       return pMouseX >= i && pMouseX <= j && pMouseY >= k && pMouseY <= l;
   }

   public void attachToParent() {
      if (this.parent == null && this.perk.getParent() != null) {
         this.parent = this.getFirstVisibleParent(this.perk);
         if (this.parent != null) {
            this.parent.addChild(this);
         }
      }

   }

   public int getY() {
      return this.y;
   }

   public int getX() {
      return this.x;
   }

   public void sendPerkUpgradePacket() {
      ModMessages.sendToServer(new UpgradePerkPacket(this.perk.getId(), this.tab.getTree().id()));
   }
}