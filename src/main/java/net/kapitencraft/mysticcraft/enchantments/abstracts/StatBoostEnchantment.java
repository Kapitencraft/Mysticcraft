package net.kapitencraft.mysticcraft.enchantments.abstracts;

import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.List;
import java.util.function.Consumer;

public abstract class StatBoostEnchantment extends Enchantment implements ModEnchantment {
    private final List<EquipmentSlot> slots;
    protected StatBoostEnchantment(Rarity p_44676_, EnchantmentCategory p_44677_, EquipmentSlot... sl) {
        super(p_44676_, p_44677_, sl);
        slots = List.of(sl);
    }

    public abstract Consumer<Multimap<Attribute, AttributeModifier>> getModifiers(int level, ItemStack enchanted, EquipmentSlot slot);
    public boolean hasModifiersForThatSlot(EquipmentSlot slot) {
        return this.slots.contains(slot);
    };
}
