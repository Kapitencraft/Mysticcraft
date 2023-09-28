package net.kapitencraft.mysticcraft.enchantments.weapon.melee;

import net.kapitencraft.mysticcraft.enchantments.abstracts.IWeaponEnchantment;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.DamageEnchantment;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class JusticeEnchantment extends Enchantment implements IWeaponEnchantment {
    public JusticeEnchantment() {
        super(Rarity.COMMON, EnchantmentCategory.WEAPON, new EquipmentSlot[] {EquipmentSlot.MAINHAND, EquipmentSlot.OFFHAND});
    }
    @Override
    public float getDamageBonus(int level, MobType mobType, ItemStack enchantedItem) {
        if (mobType == MobType.ILLAGER) {
            return (float) (level * 2.5);
        }
        return 0;
    }

    @Override
    public boolean checkCompatibility(Enchantment p_44644_) {
        return !(p_44644_ instanceof DamageEnchantment);
    }
}
