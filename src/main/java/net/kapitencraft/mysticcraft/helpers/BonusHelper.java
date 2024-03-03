package net.kapitencraft.mysticcraft.helpers;

import net.kapitencraft.mysticcraft.api.MapStream;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ExtendedAbilityEnchantment;
import net.kapitencraft.mysticcraft.event.ModEventFactory;
import net.kapitencraft.mysticcraft.item.IEventListener;
import net.kapitencraft.mysticcraft.item.capability.reforging.Reforge;
import net.kapitencraft.mysticcraft.item.item_bonus.IArmorBonusItem;
import net.kapitencraft.mysticcraft.item.item_bonus.IItemBonusItem;
import net.kapitencraft.mysticcraft.item.item_bonus.MultiPieceBonus;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;

@Mod.EventBusSubscriber
public class BonusHelper {


    public static void tickEnchantments(LivingEntity living) {
        doForSlot((stack, slot) -> MapStream.of(stack.getAllEnchantments())
                .filterKeys(e -> e instanceof ExtendedAbilityEnchantment)
                .mapKeys(MiscHelper.instanceMapper(ExtendedAbilityEnchantment.class))
                .forEach((enchantment, integer) -> enchantment.onTick(living, integer)), living, (stack, slot) -> stack.isEnchanted());
    }

    public static List<IEventListener> getListenersFromStack(EquipmentSlot slot, ItemStack stack, LivingEntity living) {
        List<IEventListener> bonuses = new ArrayList<>();
        if (stack.getItem() instanceof IItemBonusItem bonusItem) {
            bonuses.add(bonusItem.getBonus());
            bonuses.add(bonusItem.getExtraBonus());
            if (stack.getItem() instanceof ArmorItem armorItem) bonuses.addAll(getArmorBonuses(armorItem, living, slot));
        }
        Reforge reforge = Reforge.getFromStack(stack);
        if (reforge != null) bonuses.add(reforge.getBonus());
        ModEventFactory.fetchItemBonuses(bonuses, stack, slot);
        return bonuses.stream().filter(Objects::nonNull).toList();
    }

    private static List<IEventListener> getArmorBonuses(ArmorItem armorItem, LivingEntity living, EquipmentSlot slot) {
        List<IEventListener> list = new ArrayList<>();
        if (armorItem instanceof IArmorBonusItem armorBonusItem && armorItem.getSlot() == slot) {
            armorBonusItem.getPieceBonni().stream().filter(CollectionHelper.triFilter(living, slot, MultiPieceBonus::isActive)).forEach(list::add);
            list.add(armorBonusItem.getExtraBonus(slot));
            list.add(armorBonusItem.getPieceBonusForSlot(slot));
        }
        return list;
    }



    private static void doForSlot(BiConsumer<ItemStack, EquipmentSlot> stackConsumer, LivingEntity living, BiPredicate<ItemStack, EquipmentSlot> usagePredicate) {
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            ItemStack stack = living.getItemBySlot(slot);
            if (usagePredicate.test(stack, slot)) stackConsumer.accept(stack, slot);
        }
    }
}
