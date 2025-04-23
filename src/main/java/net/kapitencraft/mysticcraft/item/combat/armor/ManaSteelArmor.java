package net.kapitencraft.mysticcraft.item.combat.armor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.helpers.AttributeHelper;
import net.kapitencraft.kap_lib.item.combat.armor.ModArmorItem;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.kap_lib.util.ExtraRarities;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;

public class ManaSteelArmor extends ModArmorItem {
    public ManaSteelArmor(ArmorItem.Type type) {
        super(ModArmorMaterials.MANA_STEEL, type, new Properties().rarity(ExtraRarities.MYTHIC));
    }

    @Override
    public boolean withCustomModel() {
        return false;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeMods(EquipmentSlot slot) {
        HashMultimap<Attribute, AttributeModifier> builder = HashMultimap.create();
        builder.put(ExtraAttributes.INTELLIGENCE.get(), AttributeHelper.createModifier("ManaSteelIntelligence", AttributeModifier.Operation.ADDITION, 300));
        return builder;
    }

    protected static ManaSteelArmor create(ArmorItem.Type slot) {
        return new ManaSteelArmor(slot);
    }
}
