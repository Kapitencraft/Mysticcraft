package net.kapitencraft.mysticcraft.enchantments.weapon.ranged;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.enchantments.abstracts.ModEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.StatBoostEnchantment;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Consumer;

public class OverloadEnchantment extends StatBoostEnchantment implements ModEnchantment {
    public OverloadEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.BOW, MiscHelper.WEAPON_SLOT);
    }


    @Override
    public Consumer<Multimap<Attribute, AttributeModifier>> getModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        return multimap -> {
            multimap.put(ModAttributes.DRAW_SPEED.get(), AttributeHelper.createModifier("Overload Enchantment", AttributeModifier.Operation.ADDITION, -2 * level));
            multimap.put(ModAttributes.CRIT_DAMAGE.get(), AttributeHelper.createModifier("Overload Enchantment", AttributeModifier.Operation.ADDITION, level));
        };
    }

    @Override
    public boolean hasModifiersForThatSlot(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public String[] getDescriptionMods(int level) {
        return new String[]{"" + level * 2, "+" + level, "10%", level * 10 + "%"};
    }
}