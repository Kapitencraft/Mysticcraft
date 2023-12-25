package net.kapitencraft.mysticcraft.item.combat.weapon.melee.dagger;

import net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword.ModSwordItem;
import net.minecraft.world.item.Tier;

public abstract class ModDaggerItem extends ModSwordItem {
    public ModDaggerItem(Tier p_43269_, int attackDamage, Properties p_43272_) {
        super(p_43269_, attackDamage, -1.8f, p_43272_);
    }
}
