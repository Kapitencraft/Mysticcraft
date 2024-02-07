package net.kapitencraft.mysticcraft.gui.screen.tooltip;

import net.kapitencraft.mysticcraft.gui.reforging_anvil.ReforgeAnvilMenu;
import net.kapitencraft.mysticcraft.gui.reforging_anvil.ReforgeAnvilScreen;
import net.kapitencraft.mysticcraft.gui.screen.HoverScreenUpdatable;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class UpgradeItemTooltip extends HoverScreenUpdatable<ReforgeAnvilMenu> {
    private boolean hadMaxed = false;
    private List<ItemStack> cachedRequirements = new ArrayList<>();

    public UpgradeItemTooltip(ReforgeAnvilMenu menu) {
        super(115, ReforgeAnvilScreen.BUTTON_Y_OFFSET, 16, 16, menu);
    }

    @Override
    public void tick() {
        List<ItemStack> remaining = this.menu.getRemaining();
        if (cachedRequirements.equals(remaining) && !text.isEmpty()) return;
        cachedRequirements = remaining;
        List<Component> costExtra = new ArrayList<>();
        costExtra.add(Component.translatable("upgrade.button"));
        if (this.menu.upgradeable() != null && !this.menu.upgradeable().mayUpgrade(this.menu.getUpgradeStack())) {
            costExtra.add(Component.translatable("upgrade.no_more").withStyle(ChatFormatting.GREEN));
        } else if (!remaining.isEmpty()) {
            costExtra.add(Component.translatable("upgrade.cost").withStyle(ChatFormatting.RED));
            costExtra.addAll(remaining.stream().map(UpgradeItemTooltip::hoverNameWithCount).map(component -> component.withStyle(ChatFormatting.RED)).toList());
        }
        text = costExtra;
    }

    private static MutableComponent hoverNameWithCount(ItemStack stack) {
        return Component.literal(String.valueOf(stack.getCount())).append(" ").append(stack.getHoverName());
    }

    private boolean isComplete() {
        return this.menu.upgradeable() != null && !this.menu.upgradeable().mayUpgrade(this.menu.getUpgradeStack());
    }

    @Override
    public boolean changed() {
        List<ItemStack> remaining = this.menu.getRemaining();
        boolean flag = hadMaxed != isComplete();
        hadMaxed = isComplete();
        return !cachedRequirements.equals(remaining) || text.isEmpty() || flag;
    }
}
