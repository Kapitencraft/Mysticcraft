package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.enchantments.abstracts.SimpleToolEnchantment;

public class ExperiencedEnchantment extends SimpleToolEnchantment {
    public ExperiencedEnchantment() {
        super(Rarity.UNCOMMON);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}
