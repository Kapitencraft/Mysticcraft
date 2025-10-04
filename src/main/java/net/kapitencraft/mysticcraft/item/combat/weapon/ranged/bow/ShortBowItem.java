package net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow;

import net.kapitencraft.kap_lib.helpers.IOHelper;
import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.kap_lib.item.ExtendedItem;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ShortBowItem extends ModBowItem implements ExtendedItem {
    public static final String COOLDOWN_ID = "Cooldown";

    @Override
    public double getDivider() {
        return 0;
    }

    public ShortBowItem(Properties p_40660_) {
        super(p_40660_);
    }


    @Override
    public void releaseUsing(@NotNull ItemStack bow, @NotNull Level world, @NotNull LivingEntity archer, int timeLeft) {
        CompoundTag tag = bow.getOrCreateTag();
        if (canShoot(tag, world)) {
            createArrows(bow, world, archer);
            bow.getOrCreateTag().putInt(COOLDOWN_ID, (int) (this.createCooldown(archer) / 0.05));
        }
    }

    public float createCooldown(LivingEntity archer) {
        float base_cooldown = this.getShotCooldown();
        if (archer != null) base_cooldown *= (1 / ((float) archer.getAttributeValue(ExtraAttributes.DRAW_SPEED.get()) / 100));
        return (float) MathHelper.defRound(base_cooldown);
    }

    public abstract float getShotCooldown();

    public boolean canShoot(CompoundTag tag, Level world) {
        return !world.isClientSide && !IOHelper.checkForIntAbove0(tag, COOLDOWN_ID);
    }

    @Override
    public void inventoryTick(ItemStack bow, @NotNull Level p_41405_, @NotNull Entity p_41406_, int p_41407_, boolean p_41408_) {
        if (bow.getTag() != null) {
            CompoundTag tag = bow.getTag();
            IOHelper.reduceBy1(tag, COOLDOWN_ID);
        }
    }

    @Override
    public void appendHoverTextWithPlayer(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag, Player player) {
        super.appendHoverText(stack, level, list, flag);
        list.add(CommonComponents.EMPTY);
        list.add(Component.literal("Shot Cooldown: ").append(this.createCooldown(player) + "s").withStyle(ChatFormatting.GREEN));
        list.add(CommonComponents.EMPTY);
        list.add(Component.literal("Short Bow: Instantly Shoots!").withStyle(ChatFormatting.DARK_PURPLE));
    }

    public void createArrows(@NotNull ItemStack bow, @NotNull Level world, @NotNull LivingEntity archer) {
        createArrowProperties(archer, true, bow, this.getKB(), archer.getXRot(), archer.getYRot());
    }
}
