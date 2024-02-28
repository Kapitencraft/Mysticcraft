package net.kapitencraft.mysticcraft.item.combat.weapon.melee.dagger;

import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.misc.ModTiers;
import net.minecraft.world.item.Rarity;

public class DarkDagger extends ModDaggerItem {
    public DarkDagger() {
        this(Rarity.EPIC, 1);
    }

    protected DarkDagger(Rarity rarity, int damage) {
        super(ModTiers.SHADOW_TIER, damage, MiscHelper.rarity(rarity));
    }

    @Override
    public double getStrenght() {
        return 20;
    }

    @Override
    public double getCritDamage() {
        return 50;
    }
}
