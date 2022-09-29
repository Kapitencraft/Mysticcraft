package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ArrowEnchanted extends Arrow {
    private int ticks;

    public ArrowEnchanted(Level p_36861_, double p_36862_, double p_36863_, double p_36864_, double XJaw, double YJaw) {
        super(p_36861_, p_36862_, p_36863_, p_36864_);
        this.ticks = 0;
        this.setYHeadRot((float) YJaw);
        this.setXRot((float) XJaw);
        CompoundTag tag = this.getPersistentData();
        ItemStack stack = this.getOwner() instanceof LivingEntity living ? living.getMainHandItem() : null;
        if (stack != null) {
            tag.putBoolean("SnipeEnchant", stack.getEnchantmentLevel(ModEnchantments.SNIPE.get()) > 0);
            tag.putInt("AimEnchant", stack.getEnchantmentLevel(ModEnchantments.AIM.get()));
        }
    }

    @Override
    public void tick() {
        this.ticks++;
    }

    public int getTicks() {
        return this.ticks;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Ticks", this.ticks);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
    }
}
