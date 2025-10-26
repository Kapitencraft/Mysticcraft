package net.kapitencraft.mysticcraft.item.combat.spells.necron_sword;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneHandler;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Astraea extends NecronSword {
    public Astraea() {
        super(new Properties().attributes(
                createNecronAttributes(REFINED_BASE_DAMAGE, BASE_INTEL, BASE_FEROCITY, BASE_STRENGHT)
                        .add(
                                Attributes.ARMOR,
                                new AttributeModifier(
                                        MysticcraftMod.res("astraea_armor"),
                                        7,
                                        AttributeModifier.Operation.ADD_VALUE
                                ),
                                EquipmentSlotGroup.MAINHAND
                        ).build()
                ).component(ModDataComponentTypes.EMBEDDED_GEMSTONES, GemstoneHandler.create(GemstoneSlot.Type.COMBAT, GemstoneSlot.Type.DEFENCE))
        );
    }
}
