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

public class UltimateWiseEnchantment extends StatBoostEnchantment implements IWeaponEnchantment, IUltimateEnchantment {
    public UltimateWiseEnchantment() {
        super(Rarity.RARE, MiscUtils.SPELL_ITEM, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }


    public int getMaxLevel() {
        return 5;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        if (slot == EquipmentSlot.MAINHAND) {
            builder.put(ModAttributes.MANA_COST.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MiscUtils.createCustomIndex(slot)], "ultimateWise", level * -0.1, AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        return builder.build();
    }

    @Override
    public boolean hasModifiersForThatSlot(EquipmentSlot slot) {
        return MiscUtils.arrayContains(MiscUtils.WEAPON_SLOT, slot);
    }

    @Override
    public double getValueMultiplier() {
        return -10;
    }

    @Override
    public boolean isPercentage() {
        return true;
    }
}
