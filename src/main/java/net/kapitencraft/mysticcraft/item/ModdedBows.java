package net.kapitencraft.mysticcraft.item;

import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public abstract class ModdedBows extends BowItem {

    public final double DIVIDER = 20;

    public ModdedBows(Item.Properties p_40660_) {
        super(p_40660_);
    }

    @Override
    public void appendHoverText(@Nonnull ItemStack itemStack, @Nullable Level level, @Nonnull List<Component> list, @Nullable TooltipFlag flag) {
        StringBuilder gemstoneText = new StringBuilder();
        if (itemStack.getItem() instanceof IGemstoneApplicable gemstoneApplicable) {
            for (@Nullable GemstoneSlot slot : gemstoneApplicable.getGemstoneSlots()) {
                if (slot != null) {
                    gemstoneText.append(slot.getDisplay());
                }
            }
        }
        if (!gemstoneText.toString().equals("")) {
            list.add(Component.literal(gemstoneText.toString()));
        }

    }
    @Override
    public void releaseUsing(ItemStack bow, Level world, LivingEntity archer, int timeleft) {
        if (archer instanceof Player player) {
            boolean flag = player.getAbilities().instabuild || bow.getEnchantmentLevel(Enchantments.INFINITY_ARROWS) > 0;
            ItemStack itemstack = player.getProjectile(bow);

            int i = this.getUseDuration(bow) - timeleft;
            i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(bow, world, player, i, !itemstack.isEmpty() || flag);
            if (i < 0) return;

            if (!itemstack.isEmpty() || flag) {
                if (itemstack.isEmpty()) {
                    itemstack = new ItemStack(Items.ARROW);
                }

                float f = getPowerForTimeModded(i, bow.getEnchantmentLevel(ModEnchantments.ELVISH_MASTERY.get()));
                if (!((double)f < 0.1D)) {
                    boolean flag1 = player.getAbilities().instabuild || (itemstack.getItem() instanceof ArrowItem && ((ArrowItem)itemstack.getItem()).isInfinite(itemstack, bow, player));
                    if (!world.isClientSide) {
                        ArrowItem arrowitem = (ArrowItem)(itemstack.getItem() instanceof ArrowItem ? itemstack.getItem() : Items.ARROW);
                        AbstractArrow abstractarrow = arrowitem.createArrow(world, itemstack, player);
                        abstractarrow = customArrow(abstractarrow);
                        abstractarrow.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, f * 3, 1.0F);
                        abstractarrow.setBaseDamage(6);
                        abstractarrow.setKnockback(2);
                        double fbspeedmul = LongBowItem.ARROW_SPEED_MUL / 3;
                        int speedmul = bow.getEnchantmentLevel(ModEnchantments.FAST_ARROWS.get());
                        abstractarrow.setDeltaMovement(abstractarrow.getDeltaMovement().x * fbspeedmul * (1 + speedmul * 0.1), abstractarrow.getDeltaMovement().y * fbspeedmul * (1 + speedmul * 0.1), abstractarrow.getDeltaMovement().z * fbspeedmul * (1 + speedmul * 0.1));
                        if (f == 1.0F) {
                            abstractarrow.setCritArrow(true);
                        }

                        int j = bow.getEnchantmentLevel(Enchantments.POWER_ARROWS);
                        if (j > 0) {
                            abstractarrow.setBaseDamage(abstractarrow.getBaseDamage() + (double)j * 0.5D + 0.5D);
                        }

                        int k = bow.getEnchantmentLevel(Enchantments.PUNCH_ARROWS);
                        if (k > 0) {
                            abstractarrow.setKnockback(abstractarrow.getKnockback() + k);
                        }

                        if (bow.getEnchantmentLevel(Enchantments.FLAMING_ARROWS) > 0) {
                            abstractarrow.setSecondsOnFire(100);
                        }

                        bow.hurtAndBreak(1, player, (p_40665_) -> {
                            p_40665_.broadcastBreakEvent(player.getUsedItemHand());
                        });
                        if (flag1 || player.getAbilities().instabuild && (itemstack.is(Items.SPECTRAL_ARROW) || itemstack.is(Items.TIPPED_ARROW))) {
                            abstractarrow.pickup = AbstractArrow.Pickup.CREATIVE_ONLY;
                        }

                        world.addFreshEntity(abstractarrow);
                    }

                    world.playSound((Player)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ARROW_SHOOT, SoundSource.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!flag1 && !player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                        if (itemstack.isEmpty()) {
                            player.getInventory().removeItem(itemstack);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                }
            }
        }
    }

    public float getPowerForTimeModded(int curmul, int elv_enchantlvl) {
        float f = (float) curmul / (float)(this.DIVIDER * (1 - (elv_enchantlvl * 0.1)));
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) {
            f = 1.0F;
        }
        return f;

    }
}
