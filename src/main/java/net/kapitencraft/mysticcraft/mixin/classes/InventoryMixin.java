package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public abstract class InventoryMixin {
    @Accessor
    abstract Player getPlayer();

    @Accessor
    abstract NonNullList<ItemStack> getItems();

    @Accessor
    abstract int getSelected();


    /**
     * @reason Mining Speed Attribute
     * @author Kapitencraft
     */
    @Overwrite
    public float getDestroySpeed(BlockState state) {
        return this.getItems().get(this.getSelected()).getDestroySpeed(state) == 1 ? 1f : (float) this.getPlayer().getAttributeValue(ModAttributes.MINING_SPEED.get());
    }


    @Inject(method = "findSlotMatchingItem", at = @At("HEAD"))
    public void find(ItemStack stack, CallbackInfoReturnable<Integer> returnable) {
        stack.getOrCreateTag();
    }

    @Inject(method = "hasRemainingSpaceForItem", at = @At("HEAD"))
    public void hasRemaining(ItemStack source, ItemStack newItem, CallbackInfoReturnable<Integer> returnable) {
        newItem.getOrCreateTag();
    }
}