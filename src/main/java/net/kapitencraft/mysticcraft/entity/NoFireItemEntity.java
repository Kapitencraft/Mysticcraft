package net.kapitencraft.mysticcraft.entity;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class NoFireItemEntity extends ItemEntity {
    public NoFireItemEntity(Level p_32001_, double p_32002_, double p_32003_, double p_32004_, ItemStack p_32005_) {
        super(p_32001_, p_32002_, p_32003_, p_32004_, p_32005_);
    }

    @Override
    public boolean fireImmune() {
        return true;
    }
}
