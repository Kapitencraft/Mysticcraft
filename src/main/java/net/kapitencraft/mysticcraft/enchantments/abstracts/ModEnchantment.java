package net.kapitencraft.mysticcraft.enchantments.abstracts;

public interface ModEnchantment {

    default Object[] getDescriptionMods(int level) {
        return new Object[]  {level};
    }
}
