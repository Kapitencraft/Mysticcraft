package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ModBowEnchantment;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class FrozenArrowsEnchantment extends ModBowEnchantment {
    public FrozenArrowsEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.BOW, MiscUtils.WEAPON_SLOT, "FrozenArrow");
    }

    @Override
    public CompoundTag write(int level, ItemStack bow, LivingEntity owner, AbstractArrow arrow) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Level", level);
        return tag;
    }

    @Override
    public float execute(LivingEntity target, CompoundTag tag, ExecuteType type, float oldDamage, AbstractArrow arrow) {
        target.setTicksFrozen(tag.getInt("Level") * 40);
        return oldDamage;
    }

    @Override
    public boolean shouldTick() {
        return false;
    }

    @Override
    public double getValueMultiplier() {
        return 2;
    }

    @Override
    public boolean isPercentage() {
        return false;
    }
}
