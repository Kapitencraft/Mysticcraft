package net.kapitencraft.mysticcraft.gui.screen.tooltip;

import net.kapitencraft.mysticcraft.gui.reforging_anvil.ReforgeAnvilMenu;
import net.kapitencraft.mysticcraft.gui.reforging_anvil.ReforgeAnvilScreen;
import net.kapitencraft.mysticcraft.gui.screen.HoverScreenUpdatable;
import net.kapitencraft.mysticcraft.helpers.InventoryHelper;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;

public class ReforgeItemTooltip extends HoverScreenUpdatable<ReforgeAnvilMenu> {
    private int emeraldsRemaining = 0;
    public ReforgeItemTooltip(ReforgeAnvilMenu menu) {
        super(43, ReforgeAnvilScreen.BUTTON_Y_OFFSET, 16, 16, menu);
    }

    @Override
    public void tick() {
        text = List.of(
                Component.translatable("reforge.button"),
                Component.translatable("reforge.cost", 5 - this.emeraldsRemaining).withStyle(this.emeraldsRemaining < 1 ? ChatFormatting.GREEN : ChatFormatting.RED)
        );
    }

    @Override
    public boolean changed() {
        int i = MathHelper.count(InventoryHelper.getRemaining(List.of(new ItemStack(Items.EMERALD, 5)), this.menu.player).stream().map(ItemStack::getCount).toList());
        boolean flag = emeraldsRemaining != i;
        emeraldsRemaining = i;
        return flag;
    }
}
