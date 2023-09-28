package net.kapitencraft.mysticcraft.utils;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedAbilityEnchantment;
import net.kapitencraft.mysticcraft.item.item_bonus.Bonus;
import net.kapitencraft.mysticcraft.item.item_bonus.IArmorBonusItem;
import net.kapitencraft.mysticcraft.item.item_bonus.IWeaponBonusItem;
import net.kapitencraft.mysticcraft.item.reforging.Reforge;
import net.kapitencraft.mysticcraft.item.reforging.Reforges;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Map;
import java.util.function.BiConsumer;

public class BonusUtils {
    public static void tickEnchantments(LivingEntity living) {
        doForSlot((stack, slot) -> {
            Map<Enchantment, Integer> map = stack.getAllEnchantments();
            for (Map.Entry<Enchantment, Integer> e: map.entrySet()) {
                if (e.getKey() instanceof ExtendedAbilityEnchantment enchantment) {
                    enchantment.onTick(living, e.getValue());
                }
            }
        }, living);
    }

    public static void useBonuses(LivingEntity living, BiConsumer<Bonus, ItemStack> user) {
        doForSlot((stack, slot) -> {
            if (stack.getItem() instanceof IArmorBonusItem bonusItem) {
                user.accept(bonusItem.getFullSetBonus(), stack);
                user.accept(bonusItem.getPieceBonusForSlot(slot), stack);
                user.accept(bonusItem.getExtraBonus(slot), stack);
            }
            Reforge reforge = Reforges.getReforge(stack);
            if (reforge != null && reforge.getBonus() != null) {
                user.accept(reforge.getBonus(), stack);
            }
            if (stack.getItem() instanceof IWeaponBonusItem weaponBonusItem) {
                user.accept(weaponBonusItem.getBonus(), stack);
                user.accept(weaponBonusItem.getExtraBonus(), stack);
            }
        }, living);
    }

    private static void doForSlot(BiConsumer<ItemStack, EquipmentSlot> stackConsumer, LivingEntity living) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = living.getItemBySlot(slot);
            stackConsumer.accept(stack, slot);
        }
    }
}
