package net.kapitencraft.mysticcraft.enchantments.weapon;

import net.kapitencraft.mysticcraft.enchantments.abstracts.IWeaponEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ModEnchantmentCategories;
import net.minecraft.world.item.enchantment.Enchantment;

public class ScavengerEnchantment extends Enchantment implements IWeaponEnchantment {
    public ScavengerEnchantment() {
        super(Rarity.RARE, ModEnchantmentCategories.ALL_WEAPONS, DEFAULT_SLOT);
    }
}
