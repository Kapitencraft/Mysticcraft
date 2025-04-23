package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.QuiverItem;
import net.kapitencraft.mysticcraft.registry.ModEnchantments;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(BowItem.class)
public class BowItemMixin {

    @Redirect(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V"))
    private void shrink(ItemStack instance, int pDecrement, ItemStack pStack, Level pLevel, LivingEntity pEntityLiving, int pTimeLeft) {
        ItemStack quiverStack = QuiverItem.operationQuiver.get();
        if (quiverStack == null || quiverStack.isEmpty() || !(MathHelper.chance(0.01 * quiverStack.getEnchantmentLevel(ModEnchantments.INFINITE_QUIVER.get()), pEntityLiving))) {
            instance.shrink(1);
        }
        QuiverItem.operationQuiver.set(ItemStack.EMPTY);
    }
}
