package net.kapitencraft.mysticcraft.enchantments;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IUltimateEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IWeaponEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ModEnchantmentCategories;
import net.kapitencraft.mysticcraft.enchantments.abstracts.StatBoostEnchantment;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.utils.MiscUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class UltimateWiseEnchantment extends StatBoostEnchantment implements IWeaponEnchantment, IUltimateEnchantment {
    public UltimateWiseEnchantment() {
        super(Rarity.RARE, ModEnchantmentCategories.SPELL_ITEM, EquipmentSlot.MAINHAND);
    }


    public int getMaxLevel() {
        return 5;
    }

    @Override
    public Consumer<Multimap<Attribute, AttributeModifier>> getModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        return multimap -> multimap.put(ModAttributes.MANA_COST.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MiscUtils.createCustomIndex(slot)], "ultimateWise", level * -0.1, AttributeModifier.Operation.MULTIPLY_TOTAL));
    }

    @Override
    public boolean hasModifiersForThatSlot(EquipmentSlot slot) {
        return MiscUtils.arrayContains(MiscUtils.WEAPON_SLOT, slot);
    }

    @Override
    public Object[] getDescriptionMods(int level) {
        return new Object[] {level*-10};
    }
}
