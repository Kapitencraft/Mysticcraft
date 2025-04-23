package net.kapitencraft.mysticcraft.enchantments;

import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.enchantments.abstracts.IUltimateEnchantment;
import net.kapitencraft.kap_lib.enchantments.abstracts.IWeaponEnchantment;
import net.kapitencraft.kap_lib.enchantments.abstracts.StatBoostEnchantment;
import net.kapitencraft.kap_lib.helpers.AttributeHelper;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class UltimateWiseEnchantment extends Enchantment implements StatBoostEnchantment, IWeaponEnchantment, IUltimateEnchantment {
    public UltimateWiseEnchantment() {
        super(Rarity.RARE, ModEnchantmentCategories.SPELL_ITEM, new EquipmentSlot[] {EquipmentSlot.MAINHAND});
    }


    public int getMaxLevel() {
        return 5;
    }

    @Override
    public List<EquipmentSlot> slots() {
        return List.of(EquipmentSlot.MAINHAND);
    }

    @Override
    public Consumer<Multimap<Attribute, AttributeModifier>> getModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        return multimap -> multimap.put(ExtraAttributes.MANA_COST.get(), AttributeHelper.createModifier("Ultimate Wise Enchantment", AttributeModifier.Operation.MULTIPLY_TOTAL, level * -0.1));
    }

    @Override
    public boolean hasModifiersForThatSlot(EquipmentSlot slot, ItemStack stack) {
        return MiscHelper.getSlotForStack(stack) == slot && slot == EquipmentSlot.MAINHAND;
    }


    @Override
    protected boolean checkCompatibility(@NotNull Enchantment ench) {
        return !(ench instanceof IUltimateEnchantment);
    }

    @Override
    public String[] getDescriptionMods(int level) {
        return new String[] {level*10 + "%"};
    }
}
