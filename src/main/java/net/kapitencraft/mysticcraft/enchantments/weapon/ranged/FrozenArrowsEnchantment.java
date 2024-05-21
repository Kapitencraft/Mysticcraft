package net.kapitencraft.mysticcraft.enchantments.weapon.ranged;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ModBowEnchantment;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class FrozenArrowsEnchantment extends ModBowEnchantment {
    public FrozenArrowsEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.BOW, MiscHelper.WEAPON_SLOT, "FrozenArrow");
    }

    @Override
    public CompoundTag write(CompoundTag tag, int level, ItemStack bow, LivingEntity owner, AbstractArrow arrow) {
        return tag;
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public float execute(LivingEntity target, CompoundTag tag, ExecuteType type, float oldDamage, AbstractArrow arrow) {
        if (type == ExecuteType.HIT) target.setTicksFrozen(getLevel(tag) * 40);
        return oldDamage;
    }

    @Override
    public boolean shouldTick() {
        return false;
    }


    @Override
    public String[] getDescriptionMods(int level) {
        return new String[] {"" + level*40};
    }
}
