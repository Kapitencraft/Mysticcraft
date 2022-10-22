package net.kapitencraft.mysticcraft.misc;


import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.spells.SlivyraItem;
import net.kapitencraft.mysticcraft.item.spells.SpellItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber
public class AttributeGeneration {
    private static final String ModifierName = "Modded Attribute Modifier";

    @SubscribeEvent
    public static void onAttributesCreated(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        if (event.getSlotType() == EquipmentSlot.MAINHAND) {
            if (stack.getItem() instanceof SlivyraItem slivyraItem) {
                MakeSpellItem(slivyraItem, 500, event);
            }
            AttributeModifier ultWiseMod = UltimateWiseMod(stack);
            if (ultWiseMod != null) {
                event.addModifier(ModAttributes.MANA_COST.get(), ultWiseMod);
            }
        } else if (event.getSlotType() == EquipmentSlot.HEAD) {
            if (stack.getItem() == ModItems.WIZARD_HAT.get()) {
                event.addModifier(ModAttributes.MANA_COST.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_MUL_FOR_SLOT[0], ModifierName, -0.05, AttributeModifier.Operation.MULTIPLY_TOTAL));
                event.addModifier(ModAttributes.ABILITY_DAMAGE.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_MUL_FOR_SLOT[0], ModifierName, 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
            if (stack.getItem() instanceof ArmorItem armorItem && armorItem.getSlot() == EquipmentSlot.HEAD) {
                InsertEnchantmentRegister(0, stack, event);
            }
        } else if (event.getSlotType() == EquipmentSlot.CHEST) {
            if (stack.getItem() instanceof ArmorItem armorItem && armorItem.getSlot() == EquipmentSlot.CHEST) {
                InsertEnchantmentRegister(1, stack, event);
            }
        } else if (event.getSlotType() == EquipmentSlot.LEGS ) {
            if (stack.getItem() instanceof ArmorItem armorItem && armorItem.getSlot() == EquipmentSlot.LEGS) {
                if (stack.getEnchantmentLevel(ModEnchantments.FIRM_STAND.get()) > 0) {
                    event.addModifier(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[2], ModifierName, stack.getEnchantmentLevel(ModEnchantments.FIRM_STAND.get()) * 0.01, AttributeModifier.Operation.ADDITION));
                }
                InsertEnchantmentRegister(2, stack, event);
            }
        } else if (event.getSlotType() == EquipmentSlot.FEET) {
            if (stack.getItem() instanceof ArmorItem armorItem && armorItem.getSlot() == EquipmentSlot.FEET) {
                if (stack.getEnchantmentLevel(ModEnchantments.FIRM_STAND.get()) > 0) {
                    event.addModifier(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[3], ModifierName, stack.getEnchantmentLevel(ModEnchantments.FIRM_STAND.get()) * 0.01, AttributeModifier.Operation.ADDITION));
                }
                InsertEnchantmentRegister(3, stack, event);
            }
        }
    }

    private static void InsertEnchantmentRegister(int id, ItemStack stack, ItemAttributeModifierEvent event) {
        if (stack.getEnchantmentLevel(ModEnchantments.REJUVENATE.get()) > 0) {
            event.addModifier(ModAttributes.HEALTH_REGEN.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[id], ModifierName, stack.getEnchantmentLevel(ModEnchantments.REJUVENATE.get()) * 2, AttributeModifier.Operation.ADDITION));
        }
        if (stack.getEnchantmentLevel(ModEnchantments.GROWTH.get()) > 0) {
            event.addModifier(Attributes.MAX_HEALTH, new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[id], ModifierName, stack.getEnchantmentLevel(ModEnchantments.GROWTH.get()), AttributeModifier.Operation.ADDITION));
        }
    }

    private static void MakeSpellItem(SpellItem spellItem, double intelligence, ItemAttributeModifierEvent event) {
        event.addModifier(ModAttributes.MANA_COST.get(), new AttributeModifier(SpellItem.MANA_COST_MOD, ModifierName, spellItem.getManaCost(), AttributeModifier.Operation.ADDITION));
        event.addModifier(ModAttributes.INTELLIGENCE.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[5], ModifierName, intelligence, AttributeModifier.Operation.ADDITION));
    }

    public static AttributeModifier UltimateWiseMod(ItemStack stack) {
        if (stack.getEnchantmentLevel(ModEnchantments.ULTIMATE_WISE.get()) > 0) {
            return new AttributeModifier(SpellItem.ULTIMATE_WISE_MOD, ModifierName, stack.getEnchantmentLevel(ModEnchantments.ULTIMATE_WISE.get()) * -0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);
        } else {
            return null;
        }
    }
}