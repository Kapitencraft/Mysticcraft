package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.enchantments.abstracts.IWeaponEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ModBowEnchantment;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.misc.utils.MathUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.phys.Vec3;

public class SnipeEnchantment extends ModBowEnchantment implements IWeaponEnchantment {

    public SnipeEnchantment() {
        super(Rarity.RARE, EnchantmentCategory.BOW, new EquipmentSlot[]{EquipmentSlot.MAINHAND}, "Snipe");
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public CompoundTag write(int level, ItemStack bow, LivingEntity owner, AbstractArrow arrow) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("SnipeEnchant", bow.getEnchantmentLevel(ModEnchantments.SNIPE.get()));
        tag.putDouble("LaunchX", arrow.getX());
        tag.putDouble("LaunchY", arrow.getY());
        tag.putDouble("LaunchZ", arrow.getZ());
        return tag;
    }

    @Override
    public float execute(LivingEntity target, CompoundTag tag, ExecuteType type, float oldDamage, AbstractArrow arrow) {
        if (type == ExecuteType.HIT) {
            Vec3 start = new Vec3(tag.getDouble("LaunchX"), tag.getDouble("LaunchY"), tag.getDouble("LaunchZ"));
            Vec3 targetPos = MathUtils.getPosition(target);
            double distance = start.distanceTo(targetPos);
            return (float) (oldDamage * (1 + (distance / 10) * 0.01) * tag.getInt("Level"));
        }
        return oldDamage;
    }

    @Override
    public boolean shouldTick() {
        return false;
    }

    @Override
    public boolean isPercentage() {
        return true;
    }

    @Override
    public Object[] getDescriptionMods(int level) {
        return new Object[] {level + "%"};
    }
}
