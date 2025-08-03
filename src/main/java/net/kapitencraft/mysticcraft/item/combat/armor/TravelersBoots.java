package net.kapitencraft.mysticcraft.item.combat.armor;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.Rarity;

import java.util.UUID;

public class TravelersBoots extends ArmorItem {
    private static final UUID SPEED_MODIFIER = UUID.fromString("32b97e7e-52de-4167-8f56-b8e1578bbf9f");

    public TravelersBoots() {
        super(ArmorMaterials.LEATHER, Type.BOOTS, MiscHelper.rarity(Rarity.RARE));
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        HashMultimap<Attribute, AttributeModifier> multimap = HashMultimap.create();
        if (pEquipmentSlot == EquipmentSlot.FEET) multimap.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(SPEED_MODIFIER, "Traveler's Boots speeed", .25, AttributeModifier.Operation.MULTIPLY_BASE));
        multimap.putAll(super.getDefaultAttributeModifiers(pEquipmentSlot));
        return multimap;
    }
}
