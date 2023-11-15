package net.kapitencraft.mysticcraft.enchantments.weapon.ranged;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IWeaponEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.StatBoostEnchantment;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Consumer;

public class FastArrowsEnchantment extends StatBoostEnchantment implements IWeaponEnchantment {

    public FastArrowsEnchantment() {
        super(Enchantment.Rarity.RARE, EnchantmentCategory.BOW, MiscHelper.WEAPON_SLOT);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public Consumer<Multimap<Attribute, AttributeModifier>> getModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        return multimap -> multimap.put(ModAttributes.ARROW_SPEED.get(), AttributeHelper.createModifier("Fast Arrows Enchantment", AttributeModifier.Operation.ADDITION, level * 10));
    }

    @Override
    public boolean hasModifiersForThatSlot(EquipmentSlot slot, ItemStack stack) {
        return slot == EquipmentSlot.MAINHAND;
    }

    @Override
    public String[] getDescriptionMods(int level) {
        return new String[]{"+" + 10*level};
    }
}
