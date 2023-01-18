package net.kapitencraft.mysticcraft.misc;


import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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
        } else if (event.getSlotType() == EquipmentSlot.HEAD) {
            if (stack.getItem() == ModItems.WIZARD_HAT.get()) {
                event.addModifier(ModAttributes.MANA_COST.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_MUL_FOR_SLOT[0], ModifierName, -0.05, AttributeModifier.Operation.MULTIPLY_TOTAL));
                event.addModifier(ModAttributes.ABILITY_DAMAGE.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODIFIER_MUL_FOR_SLOT[0], ModifierName, 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }
    }
}