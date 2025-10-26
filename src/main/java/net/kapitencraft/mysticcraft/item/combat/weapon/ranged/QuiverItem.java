package net.kapitencraft.mysticcraft.item.combat.weapon.ranged;

import net.kapitencraft.kap_lib.item.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.registry.ModCreativeModTabs;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class QuiverItem extends Item {
    //TODO extract from a static variable
    public static final ThreadLocal<ItemStack> operationQuiver = new ThreadLocal<>();
    public static TabGroup QUIVER_GROUP = TabGroup.create(ModCreativeModTabs.WEAPONS_AND_TOOLS);

    public QuiverItem(Properties p_41383_, int quiverSize) {
        super(p_41383_);
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        float f = Math.max(0.0F, (getRemainingCapacity(stack) * 1f / getCapacity(stack)));
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }

    private int getCapacity(@NotNull ItemStack stack) {
        return 0;
    }

    private int getRemainingCapacity(@NotNull ItemStack stack) {
        return getCapacity(stack) - getUsedCapacity(stack);
    }
    private int getUsedCapacity(@NotNull ItemStack stack) {
        return 0;
    }


    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        return Math.round((float) getUsedCapacity(stack) * 13.0F / (float)getCapacity(stack));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        TextColor color = TextColor.fromRgb(getBarColor(stack));
        tooltipComponents.add(Component.literal(getUsedCapacity(stack) + " / " + getCapacity(stack)).withStyle(Style.EMPTY.withColor(color)));
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return getUsedCapacity(stack) > 0;
    }
}