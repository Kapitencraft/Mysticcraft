package net.kapitencraft.mysticcraft.enchantments.armor;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ArmorStatBoostEnchantment;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.utils.MiscUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Consumer;

public class RejuvenateEnchantment extends ArmorStatBoostEnchantment {
    public RejuvenateEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.WEARABLE, MiscUtils.ARMOR_EQUIPMENT);
    }

    public int getMaxLevel() {
        return 10;
    }

    @Override
    public Consumer<Multimap<Attribute, AttributeModifier>> getArmorModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        return multimap -> multimap.put(ModAttributes.HEALTH_REGEN.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MiscUtils.createCustomIndex(slot)], "rejuvenate", level * 2, AttributeModifier.Operation.ADDITION));
    }


    @Override
    public Object[] getDescriptionMods(int level) {
        return new Object[] {level*2};
    }
}