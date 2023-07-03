package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import java.util.Map;

public class HealthMendingEnchantment extends Enchantment {
    public HealthMendingEnchantment() {
        super(Rarity.VERY_RARE, EnchantmentCategory.BREAKABLE, EquipmentSlot.values());
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public int getMaxCost(int lvl) {
        return getMinCost(lvl) * 10;
    }

    public static float repairPlayerItems(Player player, float healValue) {
        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.getRandomItemWith(ModEnchantments.HEALTH_MENDING.get(), player, ItemStack::isDamaged);
        if (entry != null) {
            ItemStack itemstack = entry.getValue();
            int i = Math.min((int) (healValue * itemstack.getXpRepairRatio()), itemstack.getDamageValue());
            itemstack.setDamageValue(itemstack.getDamageValue() - i);
            float j = healValue - i / 2f;
            return j > 0 ? repairPlayerItems(player, j) : 0;
        } else {
            return healValue;
        }

    }
}
