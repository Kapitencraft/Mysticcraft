package net.kapitencraft.mysticcraft.item.combat.weapon.ranged;

import net.kapitencraft.mysticcraft.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.capability.containable.ContainableCapabilityProvider;
import net.kapitencraft.mysticcraft.capability.containable.QuiverCapability;
import net.kapitencraft.mysticcraft.item.material.containable.ContainableItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
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

public class QuiverItem extends ContainableItem<ArrowItem, QuiverCapability> {
    public static final ThreadLocal<ItemStack> operationQuiver = new ThreadLocal<>();
    public static TabGroup QUIVER_GROUP = new TabGroup(TabRegister.TabTypes.WEAPONS_AND_TOOLS);

    public QuiverItem(Properties p_41383_, int quiverSize) {
        super(p_41383_, quiverSize);
    }

    @Override
    public int getBarColor(@NotNull ItemStack stack) {
        float f = Math.max(0.0F, (getRemainingCapacity(stack) * 1f / getCapacity(stack)));
        return Mth.hsvToRgb(f / 3.0F, 1.0F, 1.0F);
    }

    @Override
    public int getBarWidth(@NotNull ItemStack stack) {
        return Math.round((float) getUsedCapacity(stack) * 13.0F / (float)getCapacity(stack));
    }


    @Override
    public void appendHoverTextWithPlayer(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag, Player player) {
        super.appendHoverTextWithPlayer(itemStack, level, list, flag, player);
        TextColor color = TextColor.fromRgb(getBarColor(itemStack));
        list.add(Component.literal(getUsedCapacity(itemStack) + " / " + getCapacity(itemStack)).withStyle(Style.EMPTY.withColor(color)));
    }

    @Override
    public ContainableCapabilityProvider<ArrowItem, QuiverCapability> makeCapabilityProvider() {
        return new ContainableCapabilityProvider<>( new QuiverCapability(), CapabilityHelper.QUIVER);
    }

    @Override
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return getUsedCapacity(stack) > 0;
    }
}