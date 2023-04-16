package net.kapitencraft.mysticcraft.enchantments;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.enchantments.abstracts.StatBoostEnchantment;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.UUID;

public class FirmStandEnchantment extends StatBoostEnchantment {
    public FirmStandEnchantment() {
        super(Rarity.UNCOMMON, EnchantmentCategory.WEARABLE, EquipmentSlot.FEET, EquipmentSlot.LEGS);
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(UUID.randomUUID(), "firmStand", level * 0.1, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }

    @Override
    public boolean hasModifiersForThatSlot(EquipmentSlot slot) {
        return MiscUtils.arrayContains(MiscUtils.ARMOR_EQUIPMENT, slot);
    }

    @Override
    public Object[] getDescriptionMods(int level) {
        return new Object[]{level*0.1};
    }
}
