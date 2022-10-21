package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.minecraft.world.item.enchantment.Enchantment;

public class EfficientJewelling extends Enchantment {
    public EfficientJewelling() {
        super(Rarity.UNCOMMON, MISCTools.GEMSTONE_ITEM, MISCTools.allEquipmentSlots);
    }


    public int getMaxLevel() {
        return 100;
    }
}
