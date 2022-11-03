package net.kapitencraft.mysticcraft.item.sword;

import net.kapitencraft.mysticcraft.item.ModTiers;
import net.minecraft.world.item.CreativeModeTab;

public class ManaSteelSword extends LongSwordItem {
    public ManaSteelSword() {
        super(ModTiers.MANA_STEEL, 7, 0.8f, new Properties().durability(1800).tab(CreativeModeTab.TAB_COMBAT));
    }

    @Override
    public double getReachMod() {
        return 3;
    }

    @Override
    public double getStrenght() {
        return 200;
    }

    @Override
    public double getCritDamage() {
        return 50;
    }
}
