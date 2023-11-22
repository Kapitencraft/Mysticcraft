package net.kapitencraft.mysticcraft.item.combat.weapon.melee.lance;

import net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword.ModSwordItem;
import net.minecraft.world.item.Tier;

public abstract class LanceItem extends ModSwordItem {

    public LanceItem(Tier p_43269_, int attackDamage, Properties p_43272_) {
        super(p_43269_, attackDamage, -2.2f, p_43272_);
    }
}