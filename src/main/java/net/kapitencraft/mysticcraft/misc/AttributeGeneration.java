package net.kapitencraft.mysticcraft.misc;


import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.bow.ModdedBows;
import net.kapitencraft.mysticcraft.item.spells.SpellItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AttributeGeneration {
    private static final String ModifierName = "Modded Attribute Modifier";

    @SubscribeEvent
    public static void onAttributesCreated(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        if (event.getSlotType() == EquipmentSlot.MAINHAND) {
            if (stack.getItem() instanceof ModdedBows moddedBows) {
                event.addModifier(Attributes.ATTACK_DAMAGE, new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT[5], ModifierName, moddedBows.getDamage(), AttributeModifier.Operation.ADDITION));
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
        }
    }
    public static AttributeModifier UltimateWiseMod(ItemStack stack) {
        if (stack.getEnchantmentLevel(ModEnchantments.ULTIMATE_WISE.get()) > 0) {
            return new AttributeModifier(SpellItem.ULTIMATE_WISE_MOD, ModifierName, stack.getEnchantmentLevel(ModEnchantments.ULTIMATE_WISE.get()) * -0.1, AttributeModifier.Operation.MULTIPLY_TOTAL);
        } else {
            return null;
        }
    }
}