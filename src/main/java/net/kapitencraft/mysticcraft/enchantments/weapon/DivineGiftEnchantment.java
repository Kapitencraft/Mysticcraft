package net.kapitencraft.mysticcraft.enchantments.weapon;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.enchantments.abstracts.WeaponStatBoostEnchantment;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;
import java.util.function.Consumer;

public class DivineGiftEnchantment extends WeaponStatBoostEnchantment {
    public DivineGiftEnchantment() {
        super(Rarity.VERY_RARE);
    }

    @Override
    public Consumer<Multimap<Attribute, AttributeModifier>> getModifiers(int level, ItemStack enchanted, EquipmentSlot slot) {
        return multimap -> multimap.put(ModAttributes.MAGIC_FIND.get(), new AttributeModifier(UUID.randomUUID(), "divineGift", level * 2, AttributeModifier.Operation.ADDITION));
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }

    @Override
    public boolean isTreasureOnly() {
        return true;
    }

    @Override
    public String[] getDescriptionMods(int level) {
        return new String[] {"+" + level*2};
    }
}
