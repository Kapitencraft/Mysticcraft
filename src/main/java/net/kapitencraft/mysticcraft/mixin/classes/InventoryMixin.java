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
}