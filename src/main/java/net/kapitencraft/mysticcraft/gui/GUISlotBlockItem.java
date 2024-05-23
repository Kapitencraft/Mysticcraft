package net.kapitencraft.mysticcraft.gui;

import net.kapitencraft.mysticcraft.item.misc.IModItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GUISlotBlockItem extends Item implements IGuiHelper, IModItem {
    private List<Component> tooltip;

    public GUISlotBlockItem() {
        super(new Properties().stacksTo(1));
    }

    public GUISlotBlockItem putTooltip(List<Component> tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    @Override
    public void appendHoverTextWithPlayer(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag, Player player) {
        list.addAll(this.tooltip);
    }

    @Override
    public TabGroup getGroup() {
        return null;
    }
}
