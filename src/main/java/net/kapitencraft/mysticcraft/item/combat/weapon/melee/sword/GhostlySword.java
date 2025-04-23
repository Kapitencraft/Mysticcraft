package net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.misc.ModTiers;
import net.minecraft.world.item.Rarity;

public class GhostlySword extends ModSwordItem {
    public GhostlySword() {
        super(ModTiers.GHOSTLY_TIER, 4, DEFAULT_ATTACK_SPEED, MiscHelper.rarity(Rarity.EPIC));
    }

    @Override
    public double getStrenght() {
        return 80;
    }

    @Override
    public double getCritDamage() {
        return 0;
    }
}
