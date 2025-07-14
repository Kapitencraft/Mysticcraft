package net.kapitencraft.mysticcraft.client;

import net.kapitencraft.kap_lib.client.overlay.OverlayProperties;
import net.kapitencraft.kap_lib.client.overlay.holder.Overlay;
import net.kapitencraft.mysticcraft.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellItem;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class SpellCastChargeOverlay extends Overlay {
    public SpellCastChargeOverlay(OverlayProperties holder) {
        super(holder, Component.translatable("overlay.cast_charge"));
    }

    @Override
    public float getWidth(LocalPlayer player, Font font) {
        return 100;
    }

    @Override
    public float getHeight(LocalPlayer player, Font font) {
        return 20;
    }

    @Override
    public void render(ForgeGui gui, GuiGraphics graphics, int screenWidth, int screenHeight, LocalPlayer player) {
        if (player.isUsingItem()) {
            ItemStack stack = player.getUseItem();
            if (stack.getItem() instanceof SpellItem) {
                Spell spell = SpellHelper.getActiveSpell(stack);
                int duration = player.getTicksUsingItem();
                graphics.fill(0, 0, 100, 10, 0xFF008FFF);
                graphics.fill(1, 1, 99 * duration / spell.castDuration(), 9, 0xFF0000FF);
            }
        }
    }
}
