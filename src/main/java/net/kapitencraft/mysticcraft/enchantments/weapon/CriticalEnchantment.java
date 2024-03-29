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

public class CriticalEnchantment extends WeaponStatBoostEnchantment {
    public CriticalEnchantment() {
        super(Rarity.COMMON);
    }

    @Override
    public Consumer<Multimap<Attribute, AttributeModifier>> getModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        return multimap -> multimap.put(ModAttributes.CRIT_DAMAGE.get(), AttributeHelper.createModifier("Critical Enchantment", AttributeModifier.Operation.ADDITION, level * 10));
    }

    @Override
    public String[] getDescriptionMods(int level) {
        return new String[] {"+" + (10*level)};
    }
}
