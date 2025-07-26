package net.kapitencraft.mysticcraft.tech.item.upgrade;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.minecraft.world.item.Rarity;

public class ParallelProcessingUpgradeItem extends UpgradeModuleItem {
    public ParallelProcessingUpgradeItem() {
        super(MiscHelper.rarity(Rarity.RARE));
    }
}
