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

import java.util.List;

public class AimEnchantment extends ModBowEnchantment implements IWeaponEnchantment {
    public AimEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND}, "Aim");
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public CompoundTag write(int level, ItemStack bow, LivingEntity owner, AbstractArrow arrow) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Level", level);
        return tag;
    }

    @Override
    public float execute(LivingEntity target, CompoundTag tag, ExecuteType type, float oldDamage, AbstractArrow arrow) {
        if (type == ExecuteType.TICK) {
            List<LivingEntity> livingEntities = MathHelper.getLivingAround(arrow, tag.getInt("Level"));
            for (LivingEntity living : livingEntities) {
                if (arrow.getOwner() != living && !living.isDeadOrDying()) {
                    arrow.setDeltaMovement(MathHelper.setLength(living.position().subtract(arrow.position()), arrow.getDeltaMovement().length()));
                    break;
                }
            }
        }
        return oldDamage;
    }

    @Override
    public boolean shouldTick() {
        return true;
    }


    @Override
    public String[] getDescriptionMods(int level) {
        return new String[] {"" + 2*level};
    }
}
