package net.kapitencraft.mysticcraft.item.data.gemstone;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.item.data.ItemData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class GemstoneHelper implements ItemData<GemstoneSlot[], GemstoneHelper> {
    private static final String DATA_ID = "GemstoneData";

    private final GemstoneSlot[] defaultSlots;


    public GemstoneHelper(GemstoneSlot[] defaultSlots) {
        this.defaultSlots = defaultSlots;
    }

    public GemstoneSlot[] getGemstoneSlots(ItemStack stack) {
        return this.loadData(stack, stack1 -> {});
    }

    public boolean putGemstone(GemstoneType gemstoneType, GemstoneType.Rarity rarity, int slotIndex, ItemStack stack) {
        GemstoneSlot[] slots = this.getGemstoneSlots(stack);
        GemstoneSlot slot = slots[slotIndex];
        boolean flag = slot.putGemstone(gemstoneType, rarity);
        this.saveData(stack, slots);
        return flag;
    }

    private void setSlots(GemstoneSlot[] slots, ItemStack stack) {
        for (int i = 0; i < slots.length; i++) {
            if (!(slots[i].getType() == this.defaultSlots[i].getType())) {
                return;
            }
        }
        this.saveData(stack, slots);
    }
    public GemstoneSlot[] loadData(ItemStack current, Consumer<GemstoneHelper> ifNull) {
        if (!(current.getTag() == null || current.getTagElement(DATA_ID) == null)) {
            CompoundTag tag = current.getOrCreateTagElement(DATA_ID);
            GemstoneSlot[] slots;
            if (!tag.contains("Size")) {
                MysticcraftMod.sendWarn("Found Modification Data without Size, using default");
                slots = defaultSlots;
            } else {
                slots = new GemstoneSlot[tag.getShort("Size")];
                ListTag slotTag = tag.getList("slots", Tag.TAG_COMPOUND);
                if (slotTag.size() < tag.getShort("Size")) {
                    MysticcraftMod.sendWarn("tried loading malformed gemstone helper");
                } else for (int i = 0; i < tag.getShort("Size"); i++) {
                    slots[i] = GemstoneSlot.fromNBT(slotTag.getCompound(i));
                }
            }
            this.putGemstones(slots, current);
            return slots;
        } else {
            ifNull.accept(this);
            return this.defaultSlots;
        }
    }

    public void saveData(ItemStack current, GemstoneSlot[] slots) {
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

    public void getDisplay(ItemStack itemStack, List<Component> list) {
        MutableComponent component = null;
        try {
            for (@Nullable GemstoneSlot slot : this.getGemstoneSlots(itemStack)) {
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

    @Override
    public String getTagId() {
        return DATA_ID;
    }

    private boolean putGemstones(GemstoneSlot[] slots, ItemStack stack) {
        if (slots.length == this.defaultSlots.length) {
            this.setSlots(slots, stack);
            return true;
        }
        return false;
    }

    public HashMap<Attribute, AttributeModifier> getAttributeModifiers(ItemStack stack, EquipmentSlot equipmentSlot) {
        HashMap<Attribute, Double> attributeModifier = new HashMap<>();
        HashMap<Attribute, AttributeModifier> modifierHashMap = new HashMap<>();
        if (MiscHelper.getSlotForStack(stack) == equipmentSlot) {
            double gemstoneModifier;
            @Nullable Attribute attribute;
            @Nullable GemstoneType gemstoneType;
            try {
                for (@Nullable GemstoneSlot slot : this.getGemstoneSlots(stack)) {
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
                MysticcraftMod.sendWarn("unable to read Gemstone slots: " + e.getMessage());
            }
            for (Attribute attribute1 : attributeModifier.keySet()) {
                modifierHashMap.put(attribute1, AttributeHelper.createModifier("Gemstone Modifications", AttributeModifier.Operation.ADDITION, attributeModifier.get(attribute1)));
            }
        }
        return modifierHashMap;
    }


    private EquipmentSlot getSlotForStack(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem ? armorItem.getSlot() : EquipmentSlot.MAINHAND;
    }
}
