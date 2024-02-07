package net.kapitencraft.mysticcraft.item.capability.gemstone;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.capability.ICapability;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;

@AutoRegisterCapability
public interface IGemstoneHandler extends ICapability<GemstoneCapability> {

    boolean putGemstone(GemstoneType gemstoneType, GemstoneType.Rarity rarity, int slotIndex);

    default boolean putGemstoneFromStack(ItemStack gem, int slotIndex) {
        if (gem.getItem() != ModItems.GEMSTONE.get()) return false;
        GemstoneType type = IGemstoneItem.getGemstone(gem);
        GemstoneType.Rarity rarity = IGemstoneItem.getGemRarity(gem);
        return this.putGemstone(type, rarity, slotIndex);
    }

    default void getDisplay(List<Component> list) {
        MutableComponent component = null;
        try {
            for (@Nullable GemstoneSlot slot : this.getSlots()) {
                if (slot != null && slot != GemstoneSlot.BLOCKED) {
                    if (component == null) {
                        component = slot.getDisplay();
                    } else {
                        component.append(slot.getDisplay());
                    }
                }
            }
        } catch (NullPointerException e) {
            MysticcraftMod.LOGGER.warn(Markers.GEMSTONE, "unable to read Gemstone slots: {}", e.getMessage());
            e.printStackTrace();
        }
        if (component != null) {
            list.add(component);
        }
    }


    int getSlotAmount();

    GemstoneSlot[] getSlots();

    void setDefault(GemstoneSlot[] slots);



    default HashMap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
        HashMap<Attribute, Double> attributeModifier = new HashMap<>();
        HashMap<Attribute, AttributeModifier> modifierHashMap = new HashMap<>();
        if (MiscHelper.getSlotForStack(stack) == equipmentSlot) {
            double gemstoneModifier;
            @Nullable Attribute attribute;
            @Nullable GemstoneType gemstoneType;
            try {
                for (@Nullable GemstoneSlot slot : this.getSlots()) {
                    if (slot != null) {
                        gemstoneType = slot.getAppliedGemstone();
                        if (gemstoneType != null) {
                            attribute = gemstoneType.modifiedAttribute.get();
                            gemstoneModifier = gemstoneType.BASE_VALUE * slot.getGemRarity().modMul * (1 + stack.getEnchantmentLevel(ModEnchantments.EFFICIENT_JEWELLING.get()) * 0.02);
                            if (attributeModifier.containsKey(attribute)) {
                                attributeModifier.put(attribute, attributeModifier.get(attribute) + gemstoneModifier);
                            } else {
                                attributeModifier.put(attribute, gemstoneModifier);
                            }
                        }
                    }
                }
            } catch (NullPointerException e) {
                MysticcraftMod.LOGGER.warn(Markers.GEMSTONE, "unable to read Gemstone slots: {}", e.getMessage());
            }
            for (Attribute attribute1 : attributeModifier.keySet()) {
                modifierHashMap.put(attribute1, AttributeHelper.createModifier("Gemstone Modifications", AttributeModifier.Operation.ADDITION, attributeModifier.get(attribute1)));
            }
        }
        return modifierHashMap;
    }
}