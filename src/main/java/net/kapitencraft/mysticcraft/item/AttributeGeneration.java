package net.kapitencraft.mysticcraft.item;


import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class AttributeGeneration {

    @SubscribeEvent
    public static void onAttributesCreated(ItemAttributeModifierEvent event) {
        if (event.getSlotType() == EquipmentSlot.MAINHAND) {
            if (event.getItemStack().getItem() == ModItems.SLIVYRA.get()) {
                event.addModifier(ModAttributes.MANA_COST.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODDIFIER_ADD, "Modded Attribute Modifier", 300, AttributeModifier.Operation.ADDITION));
                event.addModifier(ModAttributes.INTELLIGENCE.get(), new AttributeModifier(MysticcraftMod.ITEM_ATTRIBUTE_MODDIFIER_ADD, "Modded Attribute Modifier", 500, AttributeModifier.Operation.ADDITION));
            }

        }
    }
}
