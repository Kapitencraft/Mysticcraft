package net.kapitencraft.mysticcraft.item.combat.armor;

import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.ITieredItem;
import net.kapitencraft.mysticcraft.misc.ModRarities;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public abstract class TieredArmorItem extends ModArmorItem implements ITieredItem {
    protected static final Properties NETHER_ARMOR_PROPERTIES = new Properties().rarity(ModRarities.LEGENDARY);

    public TieredArmorItem(ArmorMaterial p_40386_, EquipmentSlot p_40387_, Properties p_40388_) {
        super(p_40386_, p_40387_, p_40388_);
    }

    public static boolean hasFullSetActive(ItemTier armorTier, ArmorMaterial material, LivingEntity living) {
        for (EquipmentSlot slot : MiscHelper.ARMOR_EQUIPMENT) {
            ItemStack itemStack = living.getItemBySlot(slot);
            ItemTier armorTier1 = ITieredItem.getTier(itemStack);
            if (armorTier != armorTier1) {
                return false;
            }
        }
        return ModArmorItem.isFullSetActive(living, material);
    }


    @Override
    public int getMaxDamage(ItemStack stack) {
        return (int) (this.getMaxDamage() * (1 + ITieredItem.getTier(stack).getValueMul() * 2));
    }
}
