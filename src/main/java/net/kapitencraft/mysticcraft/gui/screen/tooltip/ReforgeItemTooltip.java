package net.kapitencraft.mysticcraft.gui.screen.tooltip;

import com.google.common.collect.ImmutableList;
import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.mysticcraft.gui.reforging_anvil.ReforgeAnvilMenu;
import net.kapitencraft.mysticcraft.gui.reforging_anvil.ReforgeAnvilScreen;
import net.kapitencraft.mysticcraft.gui.screen.HoverScreenUpdatable;
import net.kapitencraft.mysticcraft.helpers.InventoryHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public class ReforgeItemTooltip extends HoverScreenUpdatable<ReforgeAnvilMenu> {
    private int emeraldsRemaining = 0;
    public ReforgeItemTooltip(ReforgeAnvilMenu menu) {
        super(43, ReforgeAnvilScreen.BUTTON_Y_OFFSET, 16, 16, menu);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void tick() {
        ArrayList<Component> list = new ArrayList<>();
        list.add(Component.translatable("reforge.button"));
        if (!Minecraft.getInstance().player.getAbilities().instabuild)
            list.add(Component.translatable("reforge.cost", 5 - this.emeraldsRemaining).withStyle(this.emeraldsRemaining < 1 ? ChatFormatting.GREEN : ChatFormatting.RED));
        text = ImmutableList.copyOf(list);
    }

    @Override
    public boolean changed() {
        int i = MathHelper.count(InventoryHelper.getRemaining(List.of(new ItemStack(Items.EMERALD, 5)), this.menu.getPlayer()).stream().map(ItemStack::getCount).toList());
        boolean flag = emeraldsRemaining != i;
        emeraldsRemaining = i;
        return flag;
    }
}
