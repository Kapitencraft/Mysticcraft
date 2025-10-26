package net.kapitencraft.mysticcraft.item.combat.spells.necron_sword;

import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.kap_lib.util.attribute.BaseAttributeLocations;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneHandler;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class Scylla extends NecronSword {
    public Scylla() {
        super(new Properties().attributes(createNecronAttributes(REFINED_BASE_DAMAGE, BASE_INTEL, BASE_FEROCITY, BASE_STRENGHT)
                .add(
                        ExtraAttributes.CRIT_DAMAGE,
                        new AttributeModifier(BaseAttributeLocations.CRIT_DAMAGE, 35, AttributeModifier.Operation.ADD_VALUE),
                        EquipmentSlotGroup.MAINHAND
                ).build()
        ).component(ModDataComponentTypes.EMBEDDED_GEMSTONES, GemstoneHandler.create(GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.COMBAT)));
    }
}
