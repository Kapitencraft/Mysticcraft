package net.kapitencraft.mysticcraft.mixin.classes;

import com.llamalad7.mixinextras.sugar.Local;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MerchantMenu.class)
public abstract class MerchantMenuMixin extends AbstractContainerMenu {

    @Shadow @Final private MerchantContainer tradeContainer;

    protected MerchantMenuMixin(@Nullable MenuType<?> menuType, int containerId) {
        super(menuType, containerId);
    }

    @SuppressWarnings("DataFlowIssue")
    @Inject(method = "moveFromInventoryToPaymentSlot", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;isEmpty()Z", ordinal = 0), cancellable = true)
    private void addWallet(int paymentSlotIndex, ItemCost payment, CallbackInfo ci, @Local(ordinal = 0) ItemStack stack) {
        if (!stack.isEmpty() && payment.test(new ItemStack(Items.EMERALD)) && stack.has(ModDataComponentTypes.WALLET_BALANCE)) {
            ItemStack trade = this.tradeContainer.getItem(paymentSlotIndex);
            if (trade.isEmpty()) {
                int k = Math.min(new ItemStack(Items.EMERALD).getMaxStackSize(), stack.get(ModDataComponentTypes.WALLET_BALANCE));
                ItemStack n = trade.isEmpty() ? new ItemStack(Items.EMERALD, k) : trade.copyWithCount(trade.getCount() + k);
                stack.update(ModDataComponentTypes.WALLET_BALANCE, 0, v -> v - k);
                this.tradeContainer.setItem(paymentSlotIndex, n);
                if (n.getCount() >= trade.getMaxStackSize()) ci.cancel();
            } else {
                int k = Math.min(trade.getMaxStackSize() - trade.getCount(), stack.get(ModDataComponentTypes.WALLET_BALANCE));
                ItemStack n = trade.isEmpty() ? new ItemStack(Items.EMERALD, k) : trade.copyWithCount(trade.getCount() + k);
                stack.update(ModDataComponentTypes.WALLET_BALANCE, 0, v -> v - k);
                this.tradeContainer.setItem(paymentSlotIndex, n);
                if (n.getCount() >= trade.getMaxStackSize()) ci.cancel();
            }
        }
    }
}
