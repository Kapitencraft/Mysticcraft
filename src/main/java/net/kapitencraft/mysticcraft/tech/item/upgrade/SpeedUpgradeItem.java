package net.kapitencraft.mysticcraft.tech.item.upgrade;

import net.minecraft.world.item.Rarity;

public class SpeedUpgradeItem extends UpgradeModuleItem {
    public SpeedUpgradeItem(Properties properties) {
        super(properties.rarity(Rarity.COMMON));
    }
}
