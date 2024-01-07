package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.entity.IFishingHook;
import net.kapitencraft.mysticcraft.entity.ModFishingHook;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.item.tools.fishing_rods.ModFishingRod;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FishingHook;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FishingRodItem.class)
public abstract class FishingRodMixin extends Item implements Vanishable {

    FishingRodItem self() {
        return (FishingRodItem) (Object) this;
    }

    public FishingRodMixin(Properties p_41383_) {
        super(p_41383_);
    }

    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addFreshEntity(Lnet/minecraft/world/entity/Entity;)Z"))
    public boolean addEntityProxy(Level level, Entity entity, Level ignored, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        int speedBonus = EnchantmentHelper.getFishingSpeedBonus(stack);
        int luckBonus = EnchantmentHelper.getFishingLuckBonus(stack);
        int catchBonus = stack.getEnchantmentLevel(ModEnchantments.FLASH.get());
        speedBonus += player.getAttributeValue(ModAttributes.FISHING_SPEED.get());
        luckBonus += player.getAttributeValue(ModAttributes.MAGIC_FIND.get());
        if (self() instanceof ModFishingRod fishingRod) {
            ModFishingHook hook = fishingRod.create(player, level, speedBonus, luckBonus);
            hook.setHookSpeedModifier(catchBonus);
            return level.addFreshEntity(hook);
        }
        FishingHook hook = (FishingHook) entity;
        hook.lureSpeed = speedBonus;
        hook.luck = luckBonus;
        ((IFishingHook) hook).setHookSpeedModifier(catchBonus);
        return level.addFreshEntity(hook);
    }
}
