package net.kapitencraft.mysticcraft.item.combat.armor;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneHandler;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterials;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.NotNull;

public class TravelersBoots extends ArmorItem {

    public TravelersBoots(Properties properties) {
        super(ArmorMaterials.LEATHER, Type.BOOTS, properties.rarity(Rarity.RARE)
                .component(ModDataComponentTypes.EMBEDDED_GEMSTONES, GemstoneHandler.create(GemstoneSlot.Type.MOBILITY, GemstoneSlot.Type.MOBILITY))
        );
    }

    @Override
    public @NotNull ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        return super.getDefaultAttributeModifiers(stack).withModifierAdded(
                Attributes.MOVEMENT_SPEED,
                new AttributeModifier(MysticcraftMod.res("travelers_boots_speed"), .25, AttributeModifier.Operation.ADD_MULTIPLIED_BASE),
                EquipmentSlotGroup.FEET
        ).withModifierAdded(
                Attributes.STEP_HEIGHT,
                new AttributeModifier(MysticcraftMod.res("travelers_boots_step_height"), .5, AttributeModifier.Operation.ADD_VALUE),
                EquipmentSlotGroup.FEET
        );
    }
}
