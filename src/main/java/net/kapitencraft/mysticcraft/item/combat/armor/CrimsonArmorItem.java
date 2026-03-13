package net.kapitencraft.mysticcraft.item.combat.armor;

import net.kapitencraft.kap_lib.attribute.ExtraAttributes;
import net.kapitencraft.kap_lib.item.combat.armor.client.provider.ArmorModelProvider;
import net.kapitencraft.kap_lib.item.combat.armor.client.provider.SimpleModelProvider;
import net.kapitencraft.kap_lib.item.creative_tab.ArmorTabGroup;
import net.kapitencraft.kap_lib.item.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.ITieredItem;
import net.kapitencraft.mysticcraft.capability.dungeon.IStarAbleItem;
import net.kapitencraft.mysticcraft.item.combat.armor.client.NetherArmorItem;
import net.kapitencraft.mysticcraft.item.combat.armor.client.model.CrimsonArmorModel;
import net.kapitencraft.mysticcraft.registry.ModArmorMaterials;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CrimsonArmorItem extends NetherArmorItem {
    private static final EnumMap<Type, Integer> STRENGHT_MODIFIERS = new EnumMap<>(Map.of(
            Type.BOOTS, 27,
            Type.LEGGINGS, 39,
            Type.CHESTPLATE, 48,
            Type.HELMET, 24
    )); //TODO fix texture?
    private static final EnumMap<Type, Integer> CRIT_DAMAGE_MODIFIERS = new EnumMap<>(Map.of(
            Type.BOOTS, 9,
            Type.LEGGINGS, 13,
            Type.CHESTPLATE, 16,
            Type.HELMET, 8
    ));
    private static final EnumMap<Type, Float> MAX_HEALTH_MODIFIER = new EnumMap<>(Map.of(
            Type.BOOTS, 4.5f,
            Type.LEGGINGS, 6f,
            Type.CHESTPLATE, 8f,
            Type.HELMET, 4f
    ));

    public static final TabGroup TAB = ArmorTabGroup.create();

    public CrimsonArmorItem(ArmorItem.Type p_40387_) {
        super(ModArmorMaterials.CRIMSON, p_40387_, NETHER_ARMOR_PROPERTIES);
    }

    public static ItemStack createAdvancementStack() {
        ItemStack stack = new ItemStack(ModItems.CRIMSON_ARMOR.get(Type.CHESTPLATE).get());
        ItemTier.INFERNAL.saveToStack(stack);
        return stack;
    }

    @Override
    protected ArmorModelProvider createModelProvider() {
        return new SimpleModelProvider(CrimsonArmorModel::createBodyLayer, CrimsonArmorModel::new);
    }

    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return makeCustomTextureLocation(MysticcraftMod.MOD_ID, "crimson_armor");
    }

    @Override
    public List<ItemStack> getMatCost(ItemStack stack) {
        int stars = IStarAbleItem.getStars(stack) + 5;
        int prestige = ITieredItem.getTier(stack).getNumber();
        return List.of();
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers(ItemStack stack) {
        EquipmentSlotGroup group = EquipmentSlotGroup.bySlot(this.type.getSlot());
        ItemTier tier = stack.getOrDefault(ModDataComponentTypes.TIER, ItemTier.DEFAULT);
        return super.getDefaultAttributeModifiers(stack).withModifierAdded(
                ExtraAttributes.STRENGTH,
                new AttributeModifier(
                        MysticcraftMod.res("strenght"),
                        STRENGHT_MODIFIERS.get(this.type) * tier.getValueMul(),
                        AttributeModifier.Operation.ADD_VALUE
                ),
                group
        ).withModifierAdded(
                ExtraAttributes.CRIT_DAMAGE,
                new AttributeModifier(
                        MysticcraftMod.res("crit_damage"),
                        CRIT_DAMAGE_MODIFIERS.get(this.type) * tier.getValueMul(),
                        AttributeModifier.Operation.ADD_VALUE
                ),
                group
        ).withModifierAdded(
                Attributes.MAX_HEALTH,
                new AttributeModifier(
                        MysticcraftMod.res("max_health"),
                        MAX_HEALTH_MODIFIER.get(this.type) * tier.getValueMul(),
                        AttributeModifier.Operation.ADD_VALUE
                ),
                group
        );
    }

    //@Override
    //public Consumer<Multimap<Attribute, AttributeModifier>> getModifiersForSlot(ItemStack stack, ItemTier tier) {
    //    return multimap -> {
    //        multimap.put(ExtraAttributes.STRENGTH, AttributeHelper.createModifierForSlot("Crimson Armor", AttributeModifier.Operation.ADDITION,
    //                3 * this.getMaterial().getDefenseForType(this.type) * tier.getValueMul(), this.getEquipmentSlot()));
    //        multimap.put(ExtraAttributes.CRIT_DAMAGE, AttributeHelper.createModifierForSlot("Crimson Armor", AttributeModifier.Operation.ADDITION,
    //                this.getMaterial().getDefenseForType(this.type) * tier.getValueMul(), this.getEquipmentSlot()));
    //        multimap.put(Attributes.MAX_HEALTH, AttributeHelper.createModifierForSlot("Crimson Armor", AttributeModifier.Operation.ADDITION,
    //                this.getMaterial().getDefenseForType(this.type) * 0.4 * tier.getValueMul(), this.getEquipmentSlot()));
    //    };
    //}
}