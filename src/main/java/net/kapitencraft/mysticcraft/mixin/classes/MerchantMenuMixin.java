package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.item.tools.ContainableItem;
import net.minecraft.world.inventory.MerchantContainer;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.*;

@Mixin(MerchantMenu.class)
public abstract class MerchantMenuMixin {


    @Shadow @Final private MerchantContainer tradeContainer;

    @Unique
    private MerchantMenu self() {
        return (MerchantMenu) (Object) this;
    }

    /**
     * @author Kapitencraft
     * @reason implementation of wallet and sacks
     */
    @Overwrite
    private void moveFromInventoryToPaymentSlot(int p_40061_, ItemStack stack2) {
        if (!stack2.isEmpty()) {
            stack2.getOrCreateTag();
            for(int i = 3; i < 39; ++i) {
                ItemStack current = self().slots.get(i).getItem();
                ItemStack newItem;
                boolean flag = false;
                if (current.getItem() instanceof ContainableItem<?> containableItem) {
                    newItem = containableItem.remove(stack2, stack2.getMaxStackSize(), current);
                    flag = true;
                } else {
                    newItem = current;
                }
                if (!newItem.isEmpty() && ItemStack.isSameItemSameTags(stack2, newItem)) {
                    ItemStack stack = tradeContainer.getItem(p_40061_);
                    int j = stack.isEmpty() ? 0 : stack.getCount();
                    int k = Math.min(stack2.getMaxStackSize() - j, newItem.getCount());
                    ItemStack stack1 = newItem.copy();
                    int l = j + k;
                    newItem.shrink(k);
                    if (flag) {
                        ContainableItem<?> item = (ContainableItem<?>) current.getItem();
                        item.put(newItem, current);
                    }
                    stack1.setCount(l);
                    this.tradeContainer.setItem(p_40061_, stack1);
                    if (l >= stack2.getMaxStackSize()) {
                        break;
                    }
                }
            }
        }

    }

}
