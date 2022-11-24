package net.kapitencraft.mysticcraft.item.bow;

import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ShortBowItem extends ModdedBows {

    public ShortBowItem(Properties p_40660_) {
        super(p_40660_);
    }


    @Override
    public void releaseUsing(@NotNull ItemStack bow, @NotNull Level world, @NotNull LivingEntity archer, int timeLeft) {
        CompoundTag tag = bow.getOrCreateTag();
        if (canShoot(tag, world)) {
            createArrows(bow, world, archer);
            bow.getOrCreateTag().putInt("Cooldown", (int) (this.createCooldown(bow) / 0.05));
        }
    }

    public float createCooldown(ItemStack stack) {
        float base_cooldown = this.getShotCooldown();
        base_cooldown *= (1 - stack.getEnchantmentLevel(ModEnchantments.ELVISH_MASTERY.get()) * 0.1);
        return base_cooldown;
    }

    public AbstractArrow createArrowProperties(LivingEntity archer, ItemStack bow, int kb, float rotX, float rotY) {
        Level world = archer.level;
        ItemStack itemstack = archer.getProjectile(bow);
        if (!itemstack.isEmpty() && itemstack.getItem() instanceof ArrowItem arrowItem) {
            AbstractArrow arrow = arrowItem.createArrow(world, itemstack, archer);
            arrow.shootFromRotation(archer, rotX, rotY, 0.0F, 4, 1.0F);
            arrow.setBaseDamage(archer.getAttributeValue(Attributes.ATTACK_DAMAGE));
            arrow.setKnockback(kb);
            arrow.setCritArrow(true);
            registerEnchant(bow, arrow);
            bow.hurtAndBreak(1, archer, (p_40665_) -> p_40665_.broadcastBreakEvent(archer.getUsedItemHand()));
            world.addFreshEntity(arrow);
            if (!(archer instanceof Player player && player.getAbilities().instabuild) || bow.getEnchantmentLevel(Enchantments.INFINITY_ARROWS) > 0) {
                itemstack.shrink(1);
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
        return (!world.isClientSide && (!tag.contains("Cooldown") || tag.getInt("Cooldown") <= 0));
    }

    @Override
    public void inventoryTick(ItemStack bow, @NotNull Level p_41405_, @NotNull Entity p_41406_, int p_41407_, boolean p_41408_) {
        if (bow.getTag() != null) {
            CompoundTag tag = bow.getTag();
            if (tag.contains("Cooldown") && tag.getInt("Cooldown") > 0) {
                tag.putInt("Cooldown", tag.getInt("Cooldown") - 1);
            }
        }
    }

    @Override
    public void appendHoverText(@NotNull ItemStack itemStack, @Nullable Level level, @NotNull List<Component> list, @Nullable TooltipFlag flag) {
        list.add(Component.literal("Shot Cooldown: " + this.createCooldown(itemStack) + "s").withStyle(ChatFormatting.GREEN));
    }

    protected void createArrows(@NotNull ItemStack bow, @NotNull Level world, @NotNull LivingEntity archer) {
        createArrowProperties(archer, bow, this.getKB(), archer.getXRot(), archer.getYRot());
    }
}
