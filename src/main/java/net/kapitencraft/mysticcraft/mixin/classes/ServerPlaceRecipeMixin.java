package net.kapitencraft.mysticcraft.mixin.classes;

import net.minecraft.recipebook.PlaceRecipe;
import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ServerPlaceRecipe.class)
public abstract class ServerPlaceRecipeMixin implements PlaceRecipe<Integer> {

    @Shadow protected Inventory inventory;

    /**
     * @reason higher stack size crafting
     * @author Kapitencraft
     */

    @ModifyConstant(method = "moveItemToGrid")
    public int moveItemToGrid(int i, Slot slot, ItemStack stack) {
        return i == 1 ? stack.getCount() : i;
    }
}
