package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.kap_lib.attribute.BaseAttributeLocations;
import net.kapitencraft.kap_lib.attribute.ExtraAttributes;
import net.kapitencraft.kap_lib.item.creative_tab.TabGroup;
import net.kapitencraft.kap_lib.mana.ManaAttributes;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.misc.ModTiers;
import net.kapitencraft.mysticcraft.registry.ModCreativeModTabs;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public abstract class SpellItem extends SwordItem {
    public static final TabGroup SPELL_GROUP = TabGroup.create(ModCreativeModTabs.WEAPONS_AND_TOOLS);

    public SpellItem(Properties p_41383_) {
        super(ModTiers.SPELL_TIER, p_41383_.stacksTo(1));
    }

    protected static ItemAttributeModifiers createCatalystAttributes(int damage, float speed, int maxMana, int magicDamage) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                BASE_ATTACK_DAMAGE_ID, damage + ModTiers.SPELL_TIER.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                ).add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(
                                BASE_ATTACK_SPEED_ID, speed, AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                ).add(
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