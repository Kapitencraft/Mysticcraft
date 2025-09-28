package net.kapitencraft.mysticcraft.spell.capability;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.kapitencraft.mysticcraft.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.client.ModKeyMappings;
import net.kapitencraft.mysticcraft.network.ModMessages;
import net.kapitencraft.mysticcraft.network.packets.C2S.SelectSpellSlotPacket;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.List;

public class SelectSpellCastScreen extends Screen {
    private final List<SpellSlot> spells;

    public SelectSpellCastScreen() {
        super(Component.translatable("spell.select.title"));
        this.spells = SpellHelper.getAvailableSpells(Minecraft.getInstance().player);
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics pGuiGraphics) {
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        int middleX = this.width / 2;
        int middleY = this.height / 2;
        pGuiGraphics.drawCenteredString(this.font, this.title, middleX, 10, 0xFFFFF);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        BufferBuilder builder = Tesselator.getInstance().getBuilder();
        builder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);

        int selectedIndex = getSelectedIndex(pMouseX - middleX, pMouseY - middleY);
        int amount = this.spells.size();

        //render slice background
        for (int i = 0; i < amount; i++) {
            boolean b = selectedIndex == i;
            drawSlice(builder, middleX, middleY, 10, 45, 135, ((i - 0.5f) / amount + 0.25f) * 360, ((i + 0.5f) / amount + 0.25f) * 360, b ? 63 : 0, b ? 161 : 0, b ? 191 : 0, b ? 60 : 64);
        }
        Tesselator.getInstance().end();
        RenderSystem.disableBlend();

        //render internal
        for (int i = 0; i < amount; i++) {
            float angle = (i / (float) amount + 0.25f) * (2 * (float) Math.PI);
            int posX = middleX - 8 + (int) (70 * Math.cos(angle));
            int posY = middleY - 8 + (int) (70 * Math.sin(angle));
            pGuiGraphics.renderFakeItem(new ItemStack(Items.DIAMOND), posX, posY);
        }

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (pKeyCode == GLFW.GLFW_KEY_ESCAPE || pKeyCode == ModKeyMappings.SELECT_SPELL_CAST.getKey().getValue()) {
            this.onClose();
            return true;
        }
        return false;
    }

    public void drawSlice(
            BufferBuilder buffer,
            float x, float y, float z,
            float radiusIn, float radiusOut,
            float startAngle, float endAngle,
            int r, int g, int b, int a
    ) {
        float angle = endAngle - startAngle;
        int sections = Math.max(1, Mth.ceil(angle / 5));

        startAngle = (float) Math.toRadians(startAngle);
        endAngle = (float) Math.toRadians(endAngle);
        angle = endAngle - startAngle;

        for (int i = 0; i < sections; i++) {
            float angle1 = startAngle + (i / (float) sections) * angle;
            float angle2 = startAngle + ((i + 1) / (float) sections) * angle;

            float pos1InX = x + radiusIn * (float) Math.cos(angle1);
            float pos1InY = y + radiusIn * (float) Math.sin(angle1);
            float pos1OutX = x + radiusOut * (float) Math.cos(angle1);
            float pos1OutY = y + radiusOut * (float) Math.sin(angle1);
            float pos2OutX = x + radiusOut * (float) Math.cos(angle2);
            float pos2OutY = y + radiusOut * (float) Math.sin(angle2);
            float pos2InX = x + radiusIn * (float) Math.cos(angle2);
            float pos2InY = y + radiusIn * (float) Math.sin(angle2);

            buffer.vertex(pos1OutX, pos1OutY, z).color(r, g, b, a).endVertex();
            buffer.vertex(pos1InX, pos1InY, z).color(r, g, b, a).endVertex();
            buffer.vertex(pos2InX, pos2InY, z).color(r, g, b, a).endVertex();
            buffer.vertex(pos2OutX, pos2OutY, z).color(r, g, b, a).endVertex();
        }
    }

    public int getSelectedIndex(int relativeMouseX, int relativeMouseY) {
        double mouseRotationFromCenter = Math.toDegrees(Math.atan2(relativeMouseY, relativeMouseX));
        double mouseDistanceToCenterOfScreen = Math.sqrt(Math.pow(relativeMouseX, 2) + Math.pow(relativeMouseY, 2));
        int amount = this.spells.size();
        if (((-.5f / amount) + .25f) * 360 > mouseRotationFromCenter) mouseRotationFromCenter += 360;
        if (!(mouseDistanceToCenterOfScreen >= 45) || !(mouseDistanceToCenterOfScreen < 135)) return -1;
        for (int i = 0; i < amount; i++) {
            float sliceBorderLeft = (((i - 0.5f) / (float) amount) + 0.25f) * 360;
            float sliceBorderRight = (((i + 0.5f) / (float) amount) + 0.25f) * 360;
            if (mouseRotationFromCenter >= sliceBorderLeft && mouseRotationFromCenter < sliceBorderRight) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        int selectedIndex = getSelectedIndex((int) pMouseX - width / 2, (int) pMouseY - height / 2);
        if (selectedIndex > -1) {
            ModMessages.sendToServer(new SelectSpellSlotPacket(selectedIndex));
            PlayerSpells.get(Minecraft.getInstance().player).setSelectedSlot(selectedIndex);
            this.onClose();
            return true;
        }
        return super.mouseClicked(pMouseX, pMouseY, pButton);
    }
}
