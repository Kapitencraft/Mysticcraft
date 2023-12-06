package net.kapitencraft.mysticcraft.helpers;

import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedAbilityEnchantment;
import net.kapitencraft.mysticcraft.item.data.reforging.Reforge;
import net.kapitencraft.mysticcraft.item.data.reforging.Reforges;
import net.kapitencraft.mysticcraft.item.item_bonus.Bonus;
import net.kapitencraft.mysticcraft.item.item_bonus.IArmorBonusItem;
import net.kapitencraft.mysticcraft.item.item_bonus.IItemBonusItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Map;
import java.util.function.BiConsumer;

public class BonusHelper {
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
                if (stack.getItem() instanceof ArmorItem armorItem && armorItem.getSlot() == EquipmentSlot.CHEST) MiscHelper.ifNonNull(bonusItem.getFullSetBonus(), fullSetBonus -> user.accept(fullSetBonus, stack));
                MiscHelper.ifNonNull(bonusItem.getPieceBonusForSlot(slot), pieceBonus -> user.accept(pieceBonus, stack));
                MiscHelper.ifNonNull(bonusItem.getExtraBonus(slot), extraBonus -> user.accept(extraBonus, stack));
            }
            Reforge reforge = Reforges.getReforge(stack);
            if (reforge != null && reforge.getBonus() != null) {
                user.accept(reforge.getBonus(), stack);
            }
            if (stack.getItem() instanceof IItemBonusItem bonusItem) {
                MiscHelper.ifNonNull(bonusItem.getBonus(), pieceBonus -> user.accept(pieceBonus, stack));
                MiscHelper.ifNonNull(bonusItem.getExtraBonus(), extraBonus -> user.accept(extraBonus, stack));
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
