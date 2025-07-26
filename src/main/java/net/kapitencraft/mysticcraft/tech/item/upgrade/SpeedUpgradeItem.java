package net.kapitencraft.mysticcraft.tech.item.upgrade;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.minecraft.world.item.Rarity;

public class SpeedUpgradeItem extends UpgradeModuleItem {
    public SpeedUpgradeItem() {
        super(MiscHelper.rarity(Rarity.COMMON));
    }
}
