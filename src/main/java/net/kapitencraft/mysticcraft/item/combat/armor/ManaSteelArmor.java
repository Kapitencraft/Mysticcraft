package net.kapitencraft.mysticcraft.item.combat.armor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.misc.ModRarities;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class ManaSteelArmor extends ModArmorItem {
    public ManaSteelArmor(EquipmentSlot p_40387_) {
        super(ModArmorMaterials.MANA_STEEL, p_40387_, new Properties().rarity(ModRarities.MYTHIC));
    }

    @Override
    public boolean withCustomModel() {
        return false;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot) {
        HashMultimap<Attribute, AttributeModifier> builder = HashMultimap.create();
        builder.put(ModAttributes.INTELLIGENCE.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[MiscHelper.createCustomIndex(this.slot)], "Intelligence", 300, AttributeModifier.Operation.ADDITION));
        return builder;
    }

    protected static ManaSteelArmor create(EquipmentSlot slot) {
        return new ManaSteelArmor(slot);
    }

    @Override
    public TabGroup getGroup() {
        return null;
    }
}
