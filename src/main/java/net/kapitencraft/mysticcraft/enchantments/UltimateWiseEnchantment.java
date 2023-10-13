package net.kapitencraft.mysticcraft.enchantments;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IUltimateEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IWeaponEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ModEnchantmentCategories;
import net.kapitencraft.mysticcraft.enchantments.abstracts.StatBoostEnchantment;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.CollectionHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
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
        return multimap -> multimap.put(ModAttributes.MANA_COST.get(), AttributeHelper.createModifier("Ultimate Wise Enchantment", AttributeModifier.Operation.MULTIPLY_TOTAL, level * -0.1));
    }

    @Override
    public boolean hasModifiersForThatSlot(EquipmentSlot slot) {
        return CollectionHelper.arrayContains(MiscHelper.WEAPON_SLOT, slot);
    }

    @Override
    public String[] getDescriptionMods(int level) {
        return new String[] {level*10 + "%"};
    }
}
