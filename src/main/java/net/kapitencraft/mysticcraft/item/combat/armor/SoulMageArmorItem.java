package net.kapitencraft.mysticcraft.item.combat.armor;

import net.kapitencraft.kap_lib.core.util.ExtraRarities;
import net.kapitencraft.kap_lib.item.creative_tab.ArmorTabGroup;
import net.kapitencraft.mysticcraft.item.combat.armor.client.NetherArmorItem;
import net.kapitencraft.mysticcraft.registry.ModArmorMaterials;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class SoulMageArmorItem extends NetherArmorItem {
    public static final ArmorTabGroup TAB = ArmorTabGroup.create();

    public SoulMageArmorItem(Type type) {
        super(ModArmorMaterials.SOUL_MAGE, type, new Properties().rarity(ExtraRarities.LEGENDARY));
    }

    //@Override
    //public Multimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot) {
    //    HashMultimap<Attribute, AttributeModifier> builder = HashMultimap.create();
    //    if (slot == this.getEquipmentSlot()) {
    //        builder.put(ExtraAttributes.MAX_MANA, AttributeHelper.createModifier("SoulMageArmorMaxMana", AttributeModifier.Operation.ADDITION,345));
    //        builder.put(ExtraAttributes.MAGIC_DAMAGE.get(), AttributeHelper.createModifier("SoulMageArmorDamage", AttributeModifier.Operation.ADDITION, 3));
    //    }
    //    return builder;
    //}

    @Override
    public List<ItemStack> getMatCost(ItemStack stack) {
        return null;
    }
}
