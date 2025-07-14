package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.capability.essence.IEssenceData;
import net.kapitencraft.mysticcraft.item.material.EssenceItem;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public abstract class InventoryMixin {
    @Shadow @Final public NonNullList<ItemStack> items;

    @Shadow @Final public Player player;

    @Inject(method = "add(ILnet/minecraft/world/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
    private void changeEssence(int p_36041_, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() instanceof EssenceItem) {
            IEssenceData.increaseEssence(player, stack);
            cir.setReturnValue(true);
        }
    }
}