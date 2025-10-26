package net.kapitencraft.mysticcraft.item.creative;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

public class ModDebugStickItem extends Item {
    public ModDebugStickItem() {
        super(new Properties().rarity(Rarity.EPIC));
    }

    @Override
    public boolean isFoil(@NotNull ItemStack p_41453_) {
        return true;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72;
    }
}
