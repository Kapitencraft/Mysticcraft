package net.kapitencraft.mysticcraft.item;

import net.kapitencraft.mysticcraft.entity.ModFishingHook;
import net.kapitencraft.mysticcraft.misc.utils.FishingHookUtils;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public abstract class ModFishingRod extends FishingRodItem {
    public ModFishingRod(Properties p_41285_) {
        super(p_41285_);
    }

    public abstract HookCreator creator();

    protected interface HookCreator {
        ModFishingHook create(Player player, Level level, int luck, int lureSpeed);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand p_41292_) {
        ItemStack itemstack = player.getItemInHand(p_41292_);
        ModFishingHook hook = FishingHookUtils.getActiveHook(player);
        boolean retrieve = hook != null;
        if (!level.isClientSide) {
            if (retrieve) {
                int i = hook.retrieve(itemstack);
                itemstack.hurtAndBreak(i, player, (p_41288_) -> {
                    p_41288_.broadcastBreakEvent(p_41292_);
                });
            } else {
                int k = EnchantmentHelper.getFishingSpeedBonus(itemstack);
                int j = EnchantmentHelper.getFishingLuckBonus(itemstack);
                level.addFreshEntity(creator().create(player, level, j, k));
            }
        }
        if (retrieve) {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_RETRIEVE, SoundSource.NEUTRAL, 1.0F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
        } else {
            level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.FISHING_BOBBER_THROW, SoundSource.NEUTRAL, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
            player.awardStat(Stats.ITEM_USED.get(this));
            player.gameEvent(GameEvent.ITEM_INTERACT_START);
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }
}
