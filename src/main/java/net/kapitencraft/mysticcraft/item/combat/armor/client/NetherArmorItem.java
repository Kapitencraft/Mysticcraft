package net.kapitencraft.mysticcraft.item.combat.armor.client;

import net.kapitencraft.mysticcraft.item.combat.armor.TieredArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;

public abstract class NetherArmorItem extends TieredArmorItem {
    public NetherArmorItem(ArmorMaterial p_40386_, EquipmentSlot p_40387_, Properties p_40388_) {
        super(p_40386_, p_40387_, p_40388_);
    }

    @Override
    public List<ItemTier> getAvailableTiers() {
        return ItemTier.NETHER_ARMOR_TIERS;
    }

    @Override
    public ItemTier fromDefault() {
        return ItemTier.HOT;
    }

    @Override
    public Map<Item, Integer> getMatCost(ItemStack stack) {
        return null;
    }

}
