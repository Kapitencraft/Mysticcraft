package net.kapitencraft.mysticcraft.item.misc.lore_categories;

import net.kapitencraft.mysticcraft.item.combat.spells.SpellScrollItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword.LongSwordItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.QuiverItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow.ShortBowItem;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.Nullable;

public class ItemTypeCategory extends ItemLoreCategory {

    public ItemTypeCategory() {
        super(1);
    }

    @Override
    public @Nullable Component apply(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof SpellScrollItem) {
            return indicator("spell_scroll");
        }
        if (item instanceof LongSwordItem) {
            return indicator("longsword");
        } else if (item instanceof ShortBowItem) {
            return indicator("short_bow");
        } else if (item instanceof SwordItem) {
            return indicator("sword");
        } else if (item instanceof PickaxeItem) {
            return indicator("pickaxe");
        } else if (item instanceof AxeItem) {
            return indicator("axe");
        } else if (item instanceof ShovelItem) {
            return indicator("shovel");
        } else if (item instanceof HoeItem) {
            return indicator("hoe");
        } else if (item instanceof BowItem) {
            return indicator("bow");
        } else if (item instanceof CrossbowItem) {
            return indicator("crossbow");
        } else if (item instanceof EnchantedBookItem) {
            return indicator("enchanted_book");
        } else if (item instanceof ArmorItem armorItem) {
            if (armorItem.getSlot() == EquipmentSlot.FEET) {
                return indicator("boots");
            } else if (armorItem.getSlot() == EquipmentSlot.LEGS) {
                return indicator("legs");
            } else if (armorItem.getSlot() == EquipmentSlot.CHEST) {
                return indicator("chestplate");
            } else if (armorItem.getSlot() == EquipmentSlot.HEAD) {
                return indicator("helmet");
            }
        } else if (item instanceof BlockItem) {
            return indicator("block");
        } else if (item instanceof BoatItem) {
            return indicator("boat");
        } else if (item instanceof FishingRodItem) {
            return indicator("fishing_rod");
        } else if (item instanceof QuiverItem) {
            return indicator("quiver");
        } else if (item instanceof ShieldItem) {
            return indicator("shield");
        }
        return indicator("item");
    }

    @SuppressWarnings("all")
    private static MutableComponent indicator(String id) {
        return Component.translatable("item.indicator." + id);
    }
}
