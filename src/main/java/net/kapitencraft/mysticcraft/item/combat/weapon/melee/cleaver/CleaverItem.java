package net.kapitencraft.mysticcraft.item.combat.weapon.melee.cleaver;

import net.kapitencraft.kap_lib.attribute.BaseAttributeLocations;
import net.kapitencraft.kap_lib.attribute.ExtraAttributes;
import net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword.ModSwordItem;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public abstract class CleaverItem extends ModSwordItem {

    public CleaverItem(Tier p_43269_, Properties p_43272_) {
        super(p_43269_, p_43272_);
    }

    protected static ItemAttributeModifiers createAttributes(Tier tier, int damage, int armorShredder) {
        return ItemAttributeModifiers.builder()
                .add(
                        Attributes.ATTACK_DAMAGE,
                        new AttributeModifier(
                                BASE_ATTACK_DAMAGE_ID, damage + tier.getAttackDamageBonus(), AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                ).add(
                        Attributes.ATTACK_SPEED,
                        new AttributeModifier(
                                BASE_ATTACK_SPEED_ID, -2.8, AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                ).add(
                        ExtraAttributes.ARMOR_SHREDDER,
                        new AttributeModifier(
                                BaseAttributeLocations.ARMOR_SHREDDER, armorShredder, AttributeModifier.Operation.ADD_VALUE
                        ),
                        EquipmentSlotGroup.MAINHAND
                ).build();
    }
}
