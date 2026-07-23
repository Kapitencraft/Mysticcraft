package net.kapitencraft.mysticcraft.data_gen.registry;

import net.kapitencraft.kap_lib.core.helpers.EnchantmentHelperExtras;
import net.kapitencraft.kap_lib.mana.ManaAttributes;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.enchantments.components.ManaSyphon;
import net.kapitencraft.mysticcraft.tags.ModTags;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentEffectComponents;
import net.minecraft.world.item.enchantment.EnchantmentTarget;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentAttributeEffect;

public interface ModEnchantments {
    ResourceKey<Enchantment> EFFICIENT_JEWELLING = key("efficient_jewelling");
    ResourceKey<Enchantment> MANA_SYPHON = key("mana_syphon");
    ResourceKey<Enchantment> ULTIMATE_WISE = key("ultimate_wise");
    ResourceKey<Enchantment> CAPACITY = key("capacity");

    static ResourceKey<Enchantment> key(String name) {
        return ResourceKey.create(Registries.ENCHANTMENT, MysticcraftMod.res(name));
    }

    static void bootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> items = context.lookup(Registries.ITEM);
        HolderGetter<Enchantment> enchantments = context.lookup(Registries.ENCHANTMENT);
        register(context, EFFICIENT_JEWELLING, Enchantment.enchantment(
                Enchantment.definition(
                        items.getOrThrow(ItemTags.DURABILITY_ENCHANTABLE),
                        5,
                        25,
                        Enchantment.dynamicCost(5, 10),
                        Enchantment.dynamicCost(7, 11),
                        1,
                        EquipmentSlotGroup.ANY
                )
        ));
        register(context, MANA_SYPHON, Enchantment.enchantment(
                Enchantment.definition(
                        items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                        1,
                        3,
                        Enchantment.dynamicCost(2, 5),
                        Enchantment.dynamicCost(3, 9),
                        4,
                        EquipmentSlotGroup.MAINHAND
                )
        ).withEffect(EnchantmentEffectComponents.POST_ATTACK, EnchantmentTarget.ATTACKER, EnchantmentTarget.ATTACKER, new ManaSyphon()));
        register(context, ULTIMATE_WISE, EnchantmentHelperExtras.ultimate(enchantments, Enchantment.definition(
                items.getOrThrow(ItemTags.WEAPON_ENCHANTABLE),
                2,
                5,
                Enchantment.dynamicCost(5, 10),
                Enchantment.dynamicCost(6, 10),
                4,
                EquipmentSlotGroup.MAINHAND
        )).withEffect(EnchantmentEffectComponents.ATTRIBUTES, new EnchantmentAttributeEffect(
                MysticcraftMod.res("ultimate_wise"), ManaAttributes.MANA_COST, LevelBasedValue.perLevel(-.1f), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        )));
        register(context, CAPACITY, Enchantment.enchantment(Enchantment.definition(
                items.getOrThrow(ModTags.Items.CONTAINER_ENCHANTABLE),
                5,
                3,
                Enchantment.dynamicCost(2, 5),
                Enchantment.dynamicCost(4, 7),
                4,
                EquipmentSlotGroup.MAINHAND
        )));
    }

    private static void register(BootstrapContext<Enchantment> context, ResourceKey<Enchantment> key, Enchantment.Builder builder) {
        context.register(key, builder.build(key.location()));
    }

}
