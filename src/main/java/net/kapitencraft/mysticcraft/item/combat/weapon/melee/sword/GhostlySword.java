package net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword;

import net.kapitencraft.mysticcraft.item.misc.ModTiers;
import net.minecraft.world.item.Rarity;

public class GhostlySword extends ModSwordItem {
    public GhostlySword(Properties properties) {
        super(ModTiers.GHOSTLY_TIER, properties.rarity(Rarity.EPIC).attributes(createAttributes(ModTiers.GHOSTLY_TIER, 4, DEFAULT_ATTACK_SPEED, 80, 0)));
    }
}
