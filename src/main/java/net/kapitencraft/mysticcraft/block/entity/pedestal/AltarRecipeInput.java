package net.kapitencraft.mysticcraft.block.entity.pedestal;

import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public class AltarRecipeInput implements RecipeInput {
    private final SimpleContainer container = new SimpleContainer(9);

    @Override
    public ItemStack getItem(int index) {
        return container.getItem(index);
    }

    @Override
    public int size() {
        return 9;
    }

    public void setAltarItem(ItemStack item) {
        this.container.setItem(0, item);
    }

    public void setPedestalItem(int i, ItemStack item) {
        this.container.setItem(i + 1, item);
    }
}
