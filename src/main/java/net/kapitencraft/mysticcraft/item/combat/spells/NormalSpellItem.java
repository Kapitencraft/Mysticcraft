package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.kap_lib.attribute.BaseAttributeLocations;
import net.kapitencraft.kap_lib.attribute.ExtraAttributes;
import net.kapitencraft.kap_lib.mana.ManaAttributes;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public abstract class NormalSpellItem extends SpellItem {
    public NormalSpellItem(Properties p_41383_) {
        super(p_41383_);
    }

    protected static ItemAttributeModifiers createAttributes(int maxMana, int magicDamage) {
        return ItemAttributeModifiers.builder()
                .add(
                        ManaAttributes.MAX_MANA,
                        new AttributeModifier(
                                MysticcraftMod.res("tool_max_mana_modifier"), maxMana, AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                ).add(
                        ExtraAttributes.MAGIC_DAMAGE,
                        new AttributeModifier(
                                BaseAttributeLocations.MAGIC_DAMAGE, magicDamage, AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                ).build();
    }
}
