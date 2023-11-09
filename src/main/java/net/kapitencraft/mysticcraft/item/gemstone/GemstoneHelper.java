package net.kapitencraft.mysticcraft.item.gemstone;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.item.persistant.ItemStackSaveable;
import net.kapitencraft.mysticcraft.item.persistant.SaveableVariant;
import net.kapitencraft.mysticcraft.misc.serialization.NbtSerializer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GemstoneHelper extends ItemStackSaveable {
    public static final Codec<GemstoneHelper> CODEC = RecordCodecBuilder.create(gemstoneHelperInstance ->
            gemstoneHelperInstance.group(
                    GemstoneSlot.CODEC.listOf().fieldOf("slots").forGetter(i -> Arrays.asList(i.curSlots))
            ).apply(gemstoneHelperInstance, GemstoneHelper::new)
    );
    private static final SaveableVariant<GemstoneHelper> VARIANT = new SaveableVariant<>(new NbtSerializer<>(CODEC), "GemstoneData");
    private static final String DATA_ID = "GemstoneData";
    private final GemstoneSlot[] curSlots;

    GemstoneHelper(List<GemstoneSlot> currentSlots) {
        this.curSlots = currentSlots.toArray(GemstoneSlot[]::new);
    }


    GemstoneHelper(GemstoneSlot[] slots) {
        this.curSlots = slots;
    }

    public GemstoneSlot[] getGemstoneSlots() {
        return curSlots;
    }

    public static GemstoneHelper get(ItemStack stack) {
        return ItemStackSaveable.get(stack, VARIANT);
    }

    public boolean putGemstone(GemstoneType gemstoneType, GemstoneType.Rarity rarity, int slotIndex, ItemStack stack) {
        GemstoneSlot[] slots = this.getGemstoneSlots();
        GemstoneSlot slot = slots[slotIndex];
        boolean flag = slot.putGemstone(gemstoneType, rarity);
        this.saveData(stack, slots);
        return flag;
    }

    private void saveData(ItemStack current, GemstoneSlot[] slots) {
        CompoundTag tag = new CompoundTag();
        ListTag slotsTag = new ListTag();
        for (int i = 0; i < slots.length; i++) {
            GemstoneSlot slot = slots[i];
            slotsTag.add(i, slot.toNBT());
        }
        tag.putShort("Size", (short) slots.length);
        tag.put("slots", slotsTag);
        current.addTagElement(DATA_ID, tag);
    }


    public void getDisplay(List<Component> list) {
        MutableComponent component = null;
        try {
            for (@Nullable GemstoneSlot slot : this.getGemstoneSlots()) {
                if (slot != null && slot != GemstoneSlot.BLOCKED) {
                    if (component == null) {
                        component = slot.getDisplay();
                    } else {
                        component.append(slot.getDisplay());
                    }
                }
            }
        } catch (NullPointerException e) {
            MysticcraftMod.sendWarn("unable to read Gemstone slots: " + e.getMessage());
        }
        if (component != null) {
            list.add(component);
        }
    }

    public HashMap<Attribute, AttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot equipmentSlot) {
        HashMap<Attribute, Double> attributeModifier = new HashMap<>();
        HashMap<Attribute, AttributeModifier> modifierHashMap = new HashMap<>();
        if (MiscHelper.getSlotForStack(stack) == equipmentSlot) {
            double gemstoneModifier;
            @Nullable Attribute attribute;
            GemstoneType gemstoneType;
            try {
                for (@Nullable GemstoneSlot slot : this.getGemstoneSlots()) {
                    if (slot != null) {
                        gemstoneType = slot.getAppliedGemstone();
                        if (gemstoneType != GemstoneType.EMPTY) {
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
                MysticcraftMod.sendWarn("unable to read Gemstone slots: " + e.getMessage());
            }
            for (Attribute attribute1 : attributeModifier.keySet()) {
                modifierHashMap.put(attribute1, AttributeHelper.createModifierForSlot("Gemstone Modifications", AttributeModifier.Operation.ADDITION, attributeModifier.get(attribute1), equipmentSlot));
            }
        }
        return modifierHashMap;
    }
}
