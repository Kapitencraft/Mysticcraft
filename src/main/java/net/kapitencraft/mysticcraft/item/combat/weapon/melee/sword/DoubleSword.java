package net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword;

import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.minecraft.world.item.Tier;

public class DoubleSword extends ModSwordItem {
    public static final TabGroup DOUBLE_SWORD_GROUP = new TabGroup(TabRegister.TabTypes.WEAPONS_AND_TOOLS);
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
