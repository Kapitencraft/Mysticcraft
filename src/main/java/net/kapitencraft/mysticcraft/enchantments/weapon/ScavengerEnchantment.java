    package net.kapitencraft.mysticcraft.enchantments.weapon;

import net.kapitencraft.mysticcraft.enchantments.abstracts.IWeaponEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ModEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ModEnchantmentCategories;
import net.minecraft.world.item.enchantment.Enchantment;

public class ScavengerEnchantment extends Enchantment implements IWeaponEnchantment, ModEnchantment {
    public ScavengerEnchantment() {
        super(Rarity.RARE, ModEnchantmentCategories.ALL_WEAPONS, DEFAULT_SLOT);
    }

    @Override
    public String[] getDescriptionMods(int level) {
        return new String[] {level * 20 + "%"};
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}
