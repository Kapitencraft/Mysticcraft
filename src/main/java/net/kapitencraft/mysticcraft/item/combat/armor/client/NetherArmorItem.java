package net.kapitencraft.mysticcraft.item.combat.armor.client;

import net.kapitencraft.mysticcraft.item.combat.armor.TieredArmorItem;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;

import java.util.List;

public abstract class NetherArmorItem extends TieredArmorItem {
    public NetherArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties properties) {
        super(material, type, properties);
    }

    @Override
    public List<ItemTier> getAvailableTiers() {
        return ItemTier.NETHER_ARMOR_TIERS;
    }

    @Override
    public ItemTier fromDefault() {
        return ItemTier.HOT;
    }
}
