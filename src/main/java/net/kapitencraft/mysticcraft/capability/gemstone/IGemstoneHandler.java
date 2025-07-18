package net.kapitencraft.mysticcraft.capability.gemstone;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.helpers.AttributeHelper;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.item.capability.AbstractCapability;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.kapitencraft.mysticcraft.registry.ModEnchantments;
import net.kapitencraft.mysticcraft.registry.ModItems;
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
public interface IGemstoneHandler extends AbstractCapability<List<GemstoneSlot>> {

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

    default Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot equipmentSlot, ItemStack stack) {
        HashMap<Attribute, Double> attributeModifier = new HashMap<>();
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();
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
                            gemstoneModifier = gemstoneType.BASE_VALUE * slot.getGemRarity().modMul * (1 + stack.getEnchantmentLevel(ModEnchantments.EFFICIENT_JEWELLING.get()) * 0.08);
                            if (attributeModifier.containsKey(attribute))
                                attributeModifier.put(attribute, attributeModifier.get(attribute) + gemstoneModifier);
                            else
                                attributeModifier.put(attribute, gemstoneModifier);
                        }
                    }
                }
            } catch (NullPointerException e) {
                MysticcraftMod.LOGGER.warn(Markers.GEMSTONE, "unable to read Gemstone slots: {}", e.getMessage());
            }
            for (Attribute attribute1 : attributeModifier.keySet()) {
                modifiers.put(attribute1, AttributeHelper.createModifier("Gemstone Modifications", AttributeModifier.Operation.ADDITION, attributeModifier.get(attribute1)));
            }
        }
        return modifiers;
    }

    default void copyFrom(List<GemstoneSlot> data) {
        GemstoneSlot[] slots = getSlots();
        for (int i = 0; i < data.size(); i++) {
            slots[i] = data.get(i);
        }
    }

    @Override
    default List<GemstoneSlot> getData() {
        return List.of(getSlots());
    }
}