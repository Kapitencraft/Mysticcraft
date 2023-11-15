package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

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
        return MiscHelper.repairPlayerItems(player, (int) healValue, ModEnchantments.HEALTH_MENDING.get());
    }
}
