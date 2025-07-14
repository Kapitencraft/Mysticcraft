package net.kapitencraft.mysticcraft.item.combat.armor;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.item.combat.armor.AbstractArmorItem;
import net.kapitencraft.kap_lib.util.ExtraRarities;
import net.kapitencraft.mysticcraft.capability.ITieredItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public abstract class TieredArmorItem extends AbstractArmorItem implements ITieredItem {
    protected static final Properties NETHER_ARMOR_PROPERTIES = new Properties().rarity(ExtraRarities.LEGENDARY);

    public TieredArmorItem(ArmorMaterial material, ArmorItem.Type type, Properties properties) {
        super(material, type, properties);
    }

    public static boolean hasFullSetActive(ItemTier armorTier, ArmorMaterial material, LivingEntity living) {
        for (EquipmentSlot slot : MiscHelper.ARMOR_EQUIPMENT) {
            ItemStack itemStack = living.getItemBySlot(slot);
            ItemTier armorTier1 = ITieredItem.getTier(itemStack);
            if (armorTier != armorTier1) {
                return false;
            }
        }
        return AbstractArmorItem.isFullSetActive(living, material);
    }


    @Override
    public int getMaxDamage(ItemStack stack) {
        return (int) (this.getMaxDamage() * (1 + ITieredItem.getTier(stack).getValueMul() * 2));
    }
}
