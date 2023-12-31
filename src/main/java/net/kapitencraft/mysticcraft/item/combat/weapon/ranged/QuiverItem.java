package net.kapitencraft.mysticcraft.item.combat.weapon.ranged;

import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.item.material.containable.ContainableItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class QuiverItem extends ContainableItem<ArrowItem> {
    public static TabGroup QUIVER_GROUP = new TabGroup(TabRegister.TabTypes.WEAPONS_AND_TOOLS);
    private static final int BAR_COLOR = Mth.color(0, 1f, 0.088f);

    public QuiverItem(Properties p_41383_, int quiverSize) {
        super(p_41383_, quiverSize);
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        return BAR_COLOR;
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        return Math.round((float) getUsedCapacity(stack) * 13.0F / (float)getCapacity(stack));
    }


    @Override
    public void appendHoverTextWithPlayer(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag, Player player) {
        super.appendHoverTextWithPlayer(itemStack, level, list, flag, player);
        double percentage = getUsedCapacity(itemStack)  * 1. / getCapacity(itemStack);
        TextColor color = TextColor.fromLegacyFormat(ChatFormatting.GREEN);
        if (percentage > 0.9) {
            color = TextColor.fromLegacyFormat(ChatFormatting.RED);
        } else if (percentage > 0.75) {
            color = TextColor.fromRgb(MathHelper.RGBtoInt(255, 127, 0));
        } else if (percentage > 0.5) {
            color = TextColor.fromLegacyFormat(ChatFormatting.YELLOW);
        }
        list.add(Component.literal(getUsedCapacity(itemStack) + " / " + getCapacity(itemStack)).withStyle(Style.EMPTY.withColor(color)));
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return getUsedCapacity(stack) > 0;
    }

    @Override
    public TabGroup getGroup() {
        return QUIVER_GROUP;
    }
}