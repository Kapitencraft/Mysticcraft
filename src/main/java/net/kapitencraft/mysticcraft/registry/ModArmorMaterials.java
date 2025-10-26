package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Supplier;

public interface ModArmorMaterials {
    DeferredRegister<ArmorMaterial> REGISTRY = MysticcraftMod.registry(Registries.ARMOR_MATERIAL);

    private static Holder<ArmorMaterial> register(String name, int[] ints, int enchantmentValue, Holder<SoundEvent> sound, List<ArmorMaterial.Layer> layers, float toughness, float knockBackResistance, Supplier<Ingredient> ingredient) {
        EnumMap<ArmorItem.Type, Integer> map = new EnumMap<>(ArmorItem.Type.class);
        for (int i = 0; i < 4; i++) { //full armor
            map.put(ArmorItem.Type.values()[i], ints[3 - i]);
        }
        return REGISTRY.register(name, () -> new ArmorMaterial(map, enchantmentValue, sound, ingredient, layers, toughness, knockBackResistance));
    }

    private static Holder<ArmorMaterial> register(
            String name,
            int[] defense,
            int enchantmentValue,
            Holder<SoundEvent> equipSound,
            float toughness,
            float knockbackResistance,
            Supplier<Ingredient> repairIngredient
    ) {
        List<ArmorMaterial.Layer> list = List.of(new ArmorMaterial.Layer(ResourceLocation.withDefaultNamespace(name)));
        return register(name, defense, enchantmentValue, equipSound, list, toughness, knockbackResistance, repairIngredient);
    }

    Holder<ArmorMaterial> WIZARD_HAT = register("wizard_hat", new int[]{0, 0, 0, 7}, 15, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F, () -> Ingredient.of(Items.LEATHER));
    Holder<ArmorMaterial> FROZEN_BLAZE = register("frozen_blaze", new int[]{6, 9, 13, 7}, 29, SoundEvents.ARMOR_EQUIP_ELYTRA, 0.9f, 0.1f, () -> Ingredient.of(Items.BLAZE_ROD));

    Holder<ArmorMaterial> ENDER_KNIGHT = register("ender_knight", new int[]{8, 12, 19, 9}, 12, SoundEvents.ARMOR_EQUIP_NETHERITE, 1.2f, 0.9f, () -> Ingredient.of(Items.NETHERITE_INGOT));
    Holder<ArmorMaterial> MANA_STEEL = register("mana_steel", new int[]{6, 9, 13, 6}, 4, SoundEvents.ARMOR_EQUIP_DIAMOND, 0.1f, 0f, () -> Ingredient.of(ModItems.MANA_STEEL_INGOT.get()));
    Holder<ArmorMaterial> SHADOW_ASSASSIN = register("shadow_assassin", new int[]{7, 11, 15, 6}, 6, SoundEvents.ARMOR_EQUIP_LEATHER, 0.5f, 0f, ()-> Ingredient.of(Items.LEATHER));
    Holder<ArmorMaterial> SOUL_MAGE = register("soul_mage", new int[]{8,11, 18, 7}, 34, SoundEvents.ARMOR_EQUIP_DIAMOND, 0.8f, 0.2f, ()-> Ingredient.of(Items.SOUL_SOIL));
    Holder<ArmorMaterial> CRIMSON = register("crimson", new int[]{9, 13, 16, 8}, 14, SoundEvents.ARMOR_EQUIP_NETHERITE, 2.3f, 1f, ()-> Ingredient.of(ModItems.CRIMSON_STEEL_INGOT.get()));
    Holder<ArmorMaterial> TERROR = register("terror", new int[]{9, 13, 16, 8}, 14, SoundEvents.ARMOR_EQUIP_NETHERITE, 2.3f, 1f, ()-> Ingredient.of(ModItems.TERROR_STEEL_INGOT.get()));
    Holder<ArmorMaterial> WARPED = register("warped", new int[]{8, 12, 16, 8}, 17, SoundEvents.ARMOR_EQUIP_NETHERITE, 1.8f, 1.4f, Ingredient::of);
    Holder<ArmorMaterial> WIZARD_CLOAK = register("wizard_cloak", new int[]{5, 7, 9, 4}, 30, SoundEvents.ARMOR_EQUIP_LEATHER, 0.2f, 0.4f, Ingredient::of);
}
