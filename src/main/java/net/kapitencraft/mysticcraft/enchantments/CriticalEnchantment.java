package net.kapitencraft.mysticcraft.enchantments;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.UUID;

public class CriticalEnchantment extends StatBoostEnchantment{
    public CriticalEnchantment() {
        super(Rarity.COMMON, EnchantmentCategory.WEAPON, MISCTools.WEAPON_SLOT);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = new ImmutableMultimap.Builder<>();
        if (slot == EquipmentSlot.MAINHAND) {
            builder.put(ModAttributes.CRIT_DAMAGE.get(), new AttributeModifier(UUID.randomUUID(), "critical", level * 10, AttributeModifier.Operation.ADDITION));
        }
        return builder.build();

    }
}
