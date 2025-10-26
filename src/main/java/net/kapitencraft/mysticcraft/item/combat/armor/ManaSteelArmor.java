package net.kapitencraft.mysticcraft.item.combat.armor;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.item.combat.armor.AbstractArmorItem;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.kap_lib.util.ExtraRarities;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.ModArmorMaterials;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.EnumMap;
import java.util.Map;

public class ManaSteelArmor extends AbstractArmorItem {
    private static final EnumMap<ArmorItem.Type, Integer> MAX_MANA_MODIFIERS = new EnumMap<>(Map.of(
            Type.BOOTS, 150,
            Type.LEGGINGS, 220,
            Type.CHESTPLATE, 300,
            Type.HELMET, 160
    ));
    private static final EnumMap<Type, Float> MANA_REGEN_MODIFIERS = new EnumMap<>(Map.of(
            Type.BOOTS, .3f,
            Type.LEGGINGS, .5f,
            Type.CHESTPLATE, 1f,
            Type.HELMET, .4f
    ));

    public ManaSteelArmor(ArmorItem.Type type) {
        super(ModArmorMaterials.MANA_STEEL, type, MiscHelper.rarity(ExtraRarities.MYTHIC).durability(type.getDurability(12)));
    }

    @Override
    public boolean withCustomModel() {
        return false;
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        EquipmentSlotGroup group = EquipmentSlotGroup.bySlot(type.getSlot());
        return super.getDefaultAttributeModifiers(stack).withModifierAdded( //this is extremely inefficient :prayge:
                ExtraAttributes.MAX_MANA,
                new AttributeModifier(MysticcraftMod.res("mana_boost"), MAX_MANA_MODIFIERS.get(this.type), AttributeModifier.Operation.ADD_VALUE),
                group
        ).withModifierAdded(
                ExtraAttributes.MANA_REGEN,
                new AttributeModifier(MysticcraftMod.res("mana_regen_boost"), MANA_REGEN_MODIFIERS.get(this.type), AttributeModifier.Operation.ADD_VALUE),
                group
        );
    }

    protected static ManaSteelArmor create(ArmorItem.Type slot) {
        return new ManaSteelArmor(slot);
    }
}
