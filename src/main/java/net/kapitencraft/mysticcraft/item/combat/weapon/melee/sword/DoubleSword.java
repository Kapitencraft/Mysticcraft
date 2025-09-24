package net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword;

import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.kapitencraft.mysticcraft.registry.ModCreativeModTabs;
import net.minecraft.world.item.Tier;

public class DoubleSword extends ModSwordItem {
    public static final TabGroup DOUBLE_SWORD_GROUP = TabGroup.builder().tab(ModCreativeModTabs.WEAPONS_AND_TOOLS).build();
    public DoubleSword(Tier p_43269_, Properties p_43272_) {
        super(p_43269_, 0, -2.1f, p_43272_);
    }

    @Override
    public double getStrenght() {
        return 150;
    }

    @Override
    public double getCritDamage() {
        return 100;
    }
}
