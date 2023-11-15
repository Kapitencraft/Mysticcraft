package net.kapitencraft.mysticcraft.item.data.gemstone;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GemstoneHelper {
    private static final String DATA_ID = "GemstoneData";

    private final GemstoneSlot[] defaultSlots;


    public GemstoneHelper(GemstoneSlot[] defaultSlots) {
        this.defaultSlots = defaultSlots;
    }

    public GemstoneSlot[] getGemstoneSlots(ItemStack stack) {
        return this.loadData(stack);
    }

    public boolean putGemstone(GemstoneType gemstoneType, GemstoneType.Rarity rarity, int slotIndex, ItemStack stack) {
        GemstoneSlot[] slots = this.getGemstoneSlots(stack);
        GemstoneSlot slot = slots[slotIndex];
        boolean flag = slot.putGemstone(gemstoneType, rarity);
        this.saveData(stack, slots);
        return flag;
    }

    public void removeGemstone(int slotIndex, ItemStack stack) {
        GemstoneSlot[] slots = this.getGemstoneSlots(stack);
        slots[slotIndex] = this.defaultSlots[slotIndex];
        this.saveData(stack, slots);
    }

    private boolean hasData(ItemStack stack) {
        return !(stack.getTag() == null || stack.getTagElement(DATA_ID) == null);
    }

    private void setSlots(GemstoneSlot[] slots, ItemStack stack) {
        for (int i = 0; i < slots.length; i++) {
            if (!(slots[i].getType() == this.defaultSlots[i].getType())) {
                return;
            }
        }
        this.saveData(stack, slots);
    }
    public GemstoneSlot[] loadData(ItemStack current) {
        if (hasData(current)) {
            CompoundTag tag = current.getTagElement(DATA_ID);
            if (tag == null) {
                throw new IllegalStateException("Existing Tag is not Existing :D");
            }
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
            return this.defaultSlots;
        }
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

    public void addModInfo(ItemStack stack, List<Component> list) {
        GemstoneSlot[] slots = this.getGemstoneSlots(stack);
        EquipmentSlot equipmentSlot = getSlotForStack(stack);
        if (slots != null) {
            boolean flag1 = false;
            for (@Nullable GemstoneSlot slot : slots) {
                flag1 = slot != null && slot.getAppliedGemstone() != null;
                if (flag1) {
                    break;
                }
            }
            if (flag1) {
                ArrayList<Attribute> modified = new ArrayList<>(this.getAttributeModifiers(stack, equipmentSlot).keySet());
                if (Screen.hasShiftDown()) {
                    list.add(Component.literal("Gemstone Modifications:").withStyle(ChatFormatting.GREEN));
                    HashMap<Attribute, AttributeModifier> modifiers = this.getAttributeModifiers(stack, equipmentSlot);
                    for (Attribute attribute : modified) {
                        double amount = modifiers.get(attribute).getAmount();
                        list.add(Component.translatable(attribute.getDescriptionId()).append(Component.literal(": " + (amount > 0 ? "+" : "") + amount)));
                    }
                } else {
                    list.add(Component.literal("Press [SHIFT] for Gemstone Information").withStyle(ChatFormatting.GREEN));
                }
            }
        }
    }

    private EquipmentSlot getSlotForStack(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem armorItem ? armorItem.getSlot() : EquipmentSlot.MAINHAND;
    }
}