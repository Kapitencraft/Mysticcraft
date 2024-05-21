package net.kapitencraft.mysticcraft.enchantments.weapon.ranged;

import net.kapitencraft.mysticcraft.enchantments.abstracts.IWeaponEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ModBowEnchantment;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.Comparator;

public class AimEnchantment extends ModBowEnchantment implements IWeaponEnchantment {
    public AimEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND}, "Aim");
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public CompoundTag write(CompoundTag tag, int level, ItemStack bow, LivingEntity owner, AbstractArrow arrow) {
        return tag;
    }

    @Override
    public float execute(LivingEntity target, CompoundTag tag, ExecuteType type, float oldDamage, AbstractArrow arrow) {
        if (type == ExecuteType.TICK) {
            MathHelper.getLivingAround(arrow, getLevel(tag) * 2).stream()
                    .filter(living -> arrow.getOwner() != living && !living.isDeadOrDying())
                    .sorted(Comparator.comparingDouble(value -> value.distanceTo(arrow)))
                    .findAny().ifPresent(living -> arrow.setDeltaMovement(MathHelper.setLength(living.position().subtract(arrow.position()), arrow.getDeltaMovement().length())));
        }
        return oldDamage;
    }

    @Override
    public boolean shouldTick() {
        return true;
    }


    @Override
    public Object[] getDescriptionMods(int level) {
        return new Object[] {2*level};
    }
}
