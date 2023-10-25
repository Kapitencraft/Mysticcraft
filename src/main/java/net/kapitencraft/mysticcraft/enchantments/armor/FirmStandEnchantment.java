package net.kapitencraft.mysticcraft.enchantments.armor;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.enchantments.abstracts.StatBoostEnchantment;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.CollectionHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Consumer;

public class FirmStandEnchantment extends StatBoostEnchantment {
    public FirmStandEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.WEARABLE, EquipmentSlot.FEET, EquipmentSlot.LEGS);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public Consumer<Multimap<Attribute, AttributeModifier>> getModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        return multimap -> multimap.put(Attributes.KNOCKBACK_RESISTANCE, AttributeHelper.createModifier("Firm Stand Enchantment", AttributeModifier.Operation.ADDITION, level));
    }

    @Override
    public boolean hasModifiersForThatSlot(EquipmentSlot slot) {
        return CollectionHelper.arrayContains(MiscHelper.ARMOR_EQUIPMENT, slot);
    }

    @Override
    public String[] getDescriptionMods(int level) {
        return new String[]{"+" + level*0.1};
    }
}
