package net.kapitencraft.mysticcraft.item.combat.weapon.melee;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword.ModSwordItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Tiers;

public class RainbowSwordItem extends ModSwordItem {
    public RainbowSwordItem() {
        super(Tiers.IRON, 10, -2.4f, MiscHelper.rarity(Rarity.RARE));
    }

    @Override
    public double getStrenght() {
        return 25;
    }

    @Override
    public double getCritDamage() {
        return 60;
    }
}
