package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.spell.Spell;
import net.minecraft.world.item.Rarity;

public class FireBoltSpell extends Spell {
    private final double damage;
    private final boolean explosive;
    public FireBoltSpell(double base_damage, boolean explosive) {
        super(50, "Fire Bolt", "0110011", Type.RELEASE, Rarity.UNCOMMON);
        this.damage = base_damage;
        this.explosive = explosive;
    }

    private double getDamage() {
        return this.damage;
    }

}