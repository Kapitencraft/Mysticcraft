package net.kapitencraft.mysticcraft.item.weapon.ranged.bow;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.misc.utils.TagUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ShortBowItem extends ModBowItem {
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
        if (archer != null) base_cooldown *= (1 / (archer.getAttributeValue(ModAttributes.DRAW_SPEED.get()) / 100));
        return base_cooldown;
    }

    public AbstractArrow createArrowProperties(LivingEntity archer, ItemStack bow, int kb, float rotX, float rotY) {
        Level world = archer.level;
        ItemStack arrowStack = archer.getProjectile(bow);
        if (!arrowStack.isEmpty() && arrowStack.getItem() instanceof ArrowItem arrowItem) {
            AbstractArrow arrow = arrowItem.createArrow(world, arrowStack, archer);
            arrow.shootFromRotation(archer, rotX, rotY, 0.0F, (float) (5 + archer.getAttributeValue(ModAttributes.ARROW_SPEED.get()) * 0.02), 1.0F);
            arrow.setBaseDamage(archer.getAttributeValue(ModAttributes.RANGED_DAMAGE.get()));
            arrow.setKnockback(kb);
            arrow.setCritArrow(true);
            registerEnchant(bow, arrow);
            bow.hurtAndBreak(1, archer, (p_40665_) -> p_40665_.broadcastBreakEvent(archer.getUsedItemHand()));
            world.addFreshEntity(arrow);
            if (!(archer instanceof Player player && player.getAbilities().instabuild) || bow.getEnchantmentLevel(Enchantments.INFINITY_ARROWS) > 0) {
                arrowStack.shrink(1);
            }
            world.playSound(null, archer.getX(), archer.getY(), archer.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + 0.5F);
            return arrow;
        } else { return null;}
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity) {
        this.releaseUsing(stack, entity.level, entity, 0);
        return true;
    }

    public abstract float getShotCooldown();

    public boolean canShoot(CompoundTag tag, Level world) {
        return !world.isClientSide && !TagUtils.checkForIntAbove0(tag, COOLDOWN_ID);
    }

    @Override
    public void inventoryTick(ItemStack bow, @NotNull Level p_41405_, @NotNull Entity p_41406_, int p_41407_, boolean p_41408_) {
        if (bow.getTag() != null) {
            CompoundTag tag = bow.getTag();
            if (TagUtils.checkForIntAbove0(tag, COOLDOWN_ID)) {
                tag.putInt(COOLDOWN_ID, tag.getInt(COOLDOWN_ID) - 1);
            }
        }
    }


    @Override
    public void appendHoverTextWithPlayer(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> list, @NotNull TooltipFlag flag, Player player) {
        super.appendHoverText(stack, level, list, flag);
        list.add(Component.literal(""));
        list.add(Component.literal("Shot Cooldown: ").append(this.createCooldown(player) + "s").withStyle(ChatFormatting.GREEN));
        list.add(Component.literal(""));
        list.add(Component.literal("Short Bow: Instantly Shoots!").withStyle(ChatFormatting.DARK_PURPLE));
    }

    protected void createArrows(@NotNull ItemStack bow, @NotNull Level world, @NotNull LivingEntity archer) {
        int legolasLevel = bow.getEnchantmentLevel(ModEnchantments.LEGOLAS_EMULATION.get());
        for (int j = 0; j < legolasLevel; j++) {
            float yChange = (float) (Math.random() * (5 - legolasLevel) - (5 - legolasLevel) / 2);
            float xChange = (float) (Math.random() * (5 - legolasLevel) - (5 - legolasLevel) / 2);
            createArrowProperties(archer, bow, this.getKB(), archer.getXRot() + xChange, archer.getYRot() + yChange);
        }
        createArrowProperties(archer, bow, this.getKB(), archer.getXRot(), archer.getYRot());
    }
}
