package net.kapitencraft.mysticcraft.client.overlay;

import net.kapitencraft.kap_lib.overlay.OverlayProperties;
import net.kapitencraft.kap_lib.overlay.holder.Overlay;
import net.kapitencraft.mysticcraft.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.registry.ModAttachmentTypes;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

import java.util.List;

public class SpellSelectionOverlay extends Overlay {

    public SpellSelectionOverlay(OverlayProperties holder) {
        super(holder, Component.translatable("spell.available.title"));
    }

    @Override
    public float getWidth(LocalPlayer player, Font font) {
        return 0;
    }

    @Override
    public float getHeight(LocalPlayer player, Font font) {
        return SpellHelper.getAvailableSpells(player).size() * 10;
    }

    @Override
    public void render(Gui gui, GuiGraphics graphics, int screenWidth, int screenHeight, LocalPlayer player) {
        if (!player.isRemoved() && !player.isDeadOrDying()) {
            List<SpellSlot> slots = SpellHelper.getAvailableSpells(player);
            int slot = player.getData(ModAttachmentTypes.SELECTED_SPELL_SLOT);
            for (int i = 0; i < slots.size(); i++) {
                if (slot == i) graphics.drawString(gui.getFont(),
                        Component.literal("> ").withStyle(ChatFormatting.YELLOW).append(slots.get(i).description()),
                        0, 10 * i, -1);
                else graphics.drawString(gui.getFont(),
                        Component.literal("  ").append(slots.get(i).description()),
                        0, 10 * i, -1);
            }
        }
    }
}
