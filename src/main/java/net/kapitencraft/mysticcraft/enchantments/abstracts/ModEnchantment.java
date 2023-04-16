package net.kapitencraft.mysticcraft.enchantments.abstracts;

public interface ModEnchantment {

    boolean isPercentage();

    Object[] getDescriptionMods(int level);
}
