package net.kapitencraft.mysticcraft.mixin.classes;

import net.minecraft.recipebook.PlaceRecipe;
import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ServerPlaceRecipe.class)
public abstract class ServerPlaceRecipeMixin implements PlaceRecipe<Integer> {

    /**
     * @reason higher stack size crafting
     * @author Kapitencraft
     */
    @Overwrite
    public void moveItemToGrid(Slot slot, ItemStack stack) {
        Inventory inventory = getInventory();
        int s = stack.getCount();
        int i = inventory.findSlotMatchingUnusedItem(stack);
        if (i != -1) {
            ItemStack itemstack = inventory.getItem(i).copy();
            if (!itemstack.isEmpty()) {
                if (itemstack.getCount() > s) {
                    inventory.removeItem(i, s);
                } else {
                    inventory.removeItemNoUpdate(i);
                }

                itemstack.setCount(s);
                if (slot.getItem().isEmpty()) {
                    slot.set(itemstack);
                } else {
                    slot.getItem().grow(s);
                }

            }
        }
    }

    @Accessor
    abstract Inventory getInventory();
}
