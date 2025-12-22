package net.kapitencraft.mysticcraft.item.combat.armor;

import net.kapitencraft.kap_lib.item.creative_tab.ArmorTabGroup;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.combat.armor.client.NetherArmorItem;
import net.kapitencraft.mysticcraft.registry.ModArmorMaterials;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class TerrorArmorItem extends NetherArmorItem {
    //region attributes
    private static final EnumMap<Type, Integer> STRENGHT = new EnumMap<>(Map.of(
            Type.HELMET, 24,
            Type.CHESTPLATE, 48,
            Type.LEGGINGS, 36,
            Type.BOOTS, 24
    ));
    private static final EnumMap<Type, Integer> CRIT_DAMAGE = new EnumMap<>(Map.of(
            Type.HELMET, 8,
            Type.CHESTPLATE, 16,
            Type.LEGGINGS, 12,
            Type.BOOTS, 8
    ));
    private static final EnumMap<Type, Double> HEALTH = new EnumMap<>(Map.of(
            Type.HELMET, 1d,
            Type.CHESTPLATE, 2d,
            Type.LEGGINGS, 1.5,
            Type.BOOTS, 1d
    ));
    //endregion

    public static final ArmorTabGroup TAB = ArmorTabGroup.create();

    public TerrorArmorItem(Type type) {
        super(ModArmorMaterials.TERROR, type, NETHER_ARMOR_PROPERTIES);
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        EquipmentSlotGroup group = EquipmentSlotGroup.bySlot(this.type.getSlot());
        ResourceLocation location = MysticcraftMod.res("armor." + this.type.getName());
        ItemTier tier = stack.getOrDefault(ModDataComponentTypes.TIER, ItemTier.DEFAULT);

        return super.getDefaultAttributeModifiers(stack)
                .withModifierAdded(ExtraAttributes.STRENGTH, new AttributeModifier(location, STRENGHT.get(this.type) * tier.getValueMul(), AttributeModifier.Operation.ADD_VALUE), group)
                .withModifierAdded(ExtraAttributes.CRIT_DAMAGE, new AttributeModifier(location, CRIT_DAMAGE.get(this.type) * tier.getValueMul(), AttributeModifier.Operation.ADD_VALUE), group)
                .withModifierAdded(Attributes.MAX_HEALTH, new AttributeModifier(location, HEALTH.get(this.type) * tier.getValueMul(), AttributeModifier.Operation.ADD_VALUE), group);
    }

    @Override
    public List<ItemStack> getMatCost(ItemStack stack) {
        return null;
    }
}
