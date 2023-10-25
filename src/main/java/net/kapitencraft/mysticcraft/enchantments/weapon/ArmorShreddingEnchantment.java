package net.kapitencraft.mysticcraft.enchantments.weapon;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.enchantments.abstracts.WeaponStatBoostEnchantment;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class ArmorShreddingEnchantment extends WeaponStatBoostEnchantment {
    public ArmorShreddingEnchantment() {
        super(Rarity.VERY_RARE);
    }

    @Override
    public Consumer<Multimap<Attribute, AttributeModifier>> getModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        return multimap -> multimap.put(ModAttributes.ARMOR_SHREDDER.get(), AttributeHelper.createModifier("Armor Shredder Enchantment", AttributeModifier.Operation.ADDITION, level));
    }
    @Override
    public String[] getDescriptionMods(int level) {
        return new String[] {"+" + level};
    }
}