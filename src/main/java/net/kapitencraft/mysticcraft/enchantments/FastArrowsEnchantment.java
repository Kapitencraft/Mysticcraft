package net.kapitencraft.mysticcraft.enchantments;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.enchantments.abstracts.StatBoostEnchantment;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class FastArrowsEnchantment extends StatBoostEnchantment implements IWeaponEnchantment {

    public FastArrowsEnchantment() {
        super(Enchantment.Rarity.RARE, EnchantmentCategory.BOW, MiscUtils.WEAPON_SLOT);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.put(ModAttributes.ARROW_SPEED.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MiscUtils.createCustomIndex(slot)], "Arrow Speed Mod", 10 * level, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }

    @Override
    public boolean hasModifiersForThatSlot(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND;
    }
}
