package net.kapitencraft.mysticcraft.enchantments.weapon.melee;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.enchantments.abstracts.IWeaponEnchantment;
import net.kapitencraft.mysticcraft.enchantments.abstracts.StatBoostEnchantment;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Consumer;

public class NecroticTouchEnchantment extends StatBoostEnchantment implements IWeaponEnchantment {
    public NecroticTouchEnchantment() {
        super(Rarity.COMMON, EnchantmentCategory.WEAPON, DEFAULT_SLOT);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public Consumer<Multimap<Attribute, AttributeModifier>> getModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        return multimap -> multimap.put(ModAttributes.LIVE_STEAL.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[5], "Live Steal Mod", level, AttributeModifier.Operation.ADDITION));
    }

    @Override
    public String[] getDescriptionMods(int level) {
        return new String[] {"+" + level};
    }
}