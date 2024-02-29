package net.kapitencraft.mysticcraft.helpers;

import net.kapitencraft.mysticcraft.api.MapStream;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedAbilityEnchantment;
import net.kapitencraft.mysticcraft.item.capability.reforging.Reforge;
import net.kapitencraft.mysticcraft.item.capability.reforging.Reforges;
import net.kapitencraft.mysticcraft.item.item_bonus.Bonus;
import net.kapitencraft.mysticcraft.item.item_bonus.IArmorBonusItem;
import net.kapitencraft.mysticcraft.item.item_bonus.IItemBonusItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

public class BonusHelper {
    public static void tickEnchantments(LivingEntity living) {
        doForSlot((stack, slot) -> MapStream.of(stack.getAllEnchantments())
                .filterKeys(e -> e instanceof ExtendedAbilityEnchantment)
                .mapKeys(MiscHelper.instanceMapper(ExtendedAbilityEnchantment.class))
                .forEach((enchantment, integer) -> enchantment.onTick(living, integer)), living, (stack, slot) -> stack.isEnchanted());
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
        }, living, (stack, slot) -> true);
    }

    public static List<Bonus> getBonusesFromStack(ItemStack stack) {
        List<Bonus> bonuses = new ArrayList<>();
        if (stack.getItem() instanceof IItemBonusItem bonusItem) {
            bonuses.add(bonusItem.getBonus());
            bonuses.add(bonusItem.getExtraBonus());
            if (bonusItem instanceof IArmorBonusItem armorBonusItem) {
                EquipmentSlot slot = MiscHelper.getSlotForStack(stack);
                bonuses.add(armorBonusItem.getFullSetBonus());
                bonuses.add(armorBonusItem.getExtraBonus(slot));
                bonuses.add(armorBonusItem.getPieceBonusForSlot(slot));
            }
        }
        Reforge reforge = Reforge.getFromStack(stack);
        if (reforge != null) bonuses.add(reforge.getBonus());
        return bonuses.stream().filter(Objects::nonNull).toList();
    }



    private static void doForSlot(BiConsumer<ItemStack, EquipmentSlot> stackConsumer, LivingEntity living, BiPredicate<ItemStack, EquipmentSlot> usagePredicate) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = living.getItemBySlot(slot);
            if (usagePredicate.test(stack, slot)) stackConsumer.accept(stack, slot);
        }
    }
}
