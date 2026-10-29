package net.kapitencraft.mysticcraft.item.combat.weapon.melee.dagger;

import net.kapitencraft.mysticcraft.item.misc.ModTiers;
import net.minecraft.world.item.Rarity;

public class DarkDagger extends ModDaggerItem {
    public static DarkDagger create(Properties properties) {
        return new DarkDagger(properties.rarity(Rarity.EPIC).attributes(createAttributes(ModTiers.SHADOW_TIER, 1, -1.8f, 20, 50)));
    }

    protected DarkDagger(Properties properties) {
        super(ModTiers.SHADOW_TIER, properties);
    }
}
