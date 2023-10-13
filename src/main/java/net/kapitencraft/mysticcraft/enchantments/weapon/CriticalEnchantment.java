package net.kapitencraft.mysticcraft.enchantments.weapon;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.enchantments.abstracts.WeaponStatBoostEnchantment;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
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
        return multimap -> multimap.put(ModAttributes.CRIT_DAMAGE.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MiscHelper.createCustomIndex(slot)], "critical", level * 10, AttributeModifier.Operation.ADDITION));
    }

    @Override
    public String[] getDescriptionMods(int level) {
        return new String[] {"+" + (10*level)};
    }
}
