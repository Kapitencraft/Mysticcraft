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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class TerrorArmorItem extends NetherArmorItem {
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
                .withModifierAdded(ExtraAttributes.CRIT_DAMAGE, new AttributeModifier(location, CRIT_DAMAGE.get(this.type) * tier.getValueMul(), AttributeModifier.Operation.ADD_VALUE), group);
    }

    //@Override TODO
    //public Consumer<Multimap<Attribute, AttributeModifier>> getModifiersForSlot(ItemStack stack, ItemTier tier) {
    //    return multimap -> {
    //        multimap.put(Attributes.MAX_HEALTH, AttributeHelper.createModifierForSlot("Terror Armor", AttributeModifier.Operation.ADDITION,
    //                this.getMaterial().getDefenseForType(this.type) * 0.4 * tier.getValueMul(), getEquipmentSlot()));
    //    };
    //}

    @Override
    public List<ItemStack> getMatCost(ItemStack stack) {
        return null;
    }
}
