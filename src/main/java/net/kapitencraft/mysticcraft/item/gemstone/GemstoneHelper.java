package net.kapitencraft.mysticcraft.item.gemstone;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.misc.MISCTools;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GemstoneHelper {
    private static final String DATA_ID = "GemstoneData";

    private final GemstoneSlot[] defaultSlots;
    private final int slotAmount;
    private GemstoneSlot[] cachedSlots = null;


    public GemstoneHelper(GemstoneSlot[] defaultSlots) {
        this.defaultSlots = defaultSlots;
        this.slotAmount = defaultSlots.length;
    }

    public GemstoneSlot[] getGemstoneSlots(ItemStack stack) {
        if (this.cachedSlots != null) {
            return this.cachedSlots;
        } else {
            return this.cachedSlots = this.loadData(stack);
        }
    }

    public int getGemstoneSlotAmount() {
        return slotAmount;
    }

    public boolean putGemstone(GemstoneType gemstoneType, GemstoneType.Rarity rarity, int slotIndex, ItemStack stack) {
        boolean flag = this.getGemstoneSlots(stack)[slotIndex].putGemstone(gemstoneType, rarity);
        this.saveData(stack);
        return flag;

    }

    private boolean hasData(ItemStack stack) {
        if (stack.getTag() == null) {
            //MysticcraftMod.sendInfo("found Tag = null");
        } else if (stack.getTagElement(DATA_ID) == null) {
            //MysticcraftMod.sendInfo("found Gemstone Data = null");
        } else {
            return true;
        }
        return false;
    }

    private void setSlots(GemstoneSlot[] slots, ItemStack stack) {
        for (int i = 0; i < slots.length; i++) {
            if (!(slots[i].getType() == this.defaultSlots[i].getType())) {
                return;
            }
        }
        this.cachedSlots = slots;
        this.saveData(stack);
    }
    public GemstoneSlot[] loadData(ItemStack current) {
        //MysticcraftMod.sendInfo("loading Data:");
        if (hasData(current)) {
            //MysticcraftMod.sendInfo("Loading now...");
            CompoundTag tag = current.getTagElement(DATA_ID);
            if (tag == null) {
                throw new IllegalStateException("Existing Tag is not Existing :D");
            }
            if (!tag.contains("Size")) {
                throw new IllegalStateException("Found Modification Data without Size");
            }
            GemstoneSlot[] slots = new GemstoneSlot[tag.getShort("Size")];
            for (int i = 0; i < tag.getShort("Size"); i++) {
                if (!(tag.get("slot" + i) == null)) {
                    slots[i] = GemstoneSlot.fromNBT((CompoundTag) tag.get("slot" + i));
                    //MysticcraftMod.sendInfo("Slot " + (i + 1) + " contains: " + (slots[i] == null ? null : slots[i].getDisplay()));
                } else {
                    //MysticcraftMod.sendInfo("isNull");
                }
            }
            this.putGemstones(slots, current);
            return slots;
        } else {
            return this.defaultSlots;
        }
    }

    private void saveData(ItemStack current) {
        GemstoneSlot[] slots = this.cachedSlots;
        MysticcraftMod.sendInfo("saving Data with Size: " + slots.length);
        CompoundTag tag = new CompoundTag();
        for (int i = 0; i < slots.length; i++) {
            GemstoneSlot slot = slots[i];
            tag.put("slot" + i, slot.toNBT());
        }
        tag.putShort("Size", (short) slots.length);
        MysticcraftMod.sendInfo("saving Data Now");
        current.addTagElement(DATA_ID, tag);
    }


    public void getDisplay(ItemStack itemStack, List<Component> list) {
        StringBuilder gemstoneText = new StringBuilder();
        if (itemStack.getItem() instanceof IGemstoneApplicable gemstoneApplicable) {
            for (@Nullable GemstoneSlot slot : gemstoneApplicable.getGemstoneSlots(itemStack)) {
                if (slot != null) {
                    gemstoneText.append(slot.getDisplay());
                }
            }
        }
        if (!gemstoneText.toString().equals("")) {
            list.add(Component.literal(gemstoneText.toString()));
        }
    }

    private boolean putGemstones(GemstoneSlot[] slots, ItemStack stack) {
        if (slots.length == this.slotAmount) {
            this.setSlots(slots, stack);
            return true;
        }
        return false;
    }

    public HashMap<Attribute, AttributeModifier> getAttributeModifiers(ItemStack stack) {
        HashMap<Attribute, Double> attributeModifier = new HashMap<>();
        double gemstoneModifier;
        @Nullable Attribute attribute;
        @Nullable GemstoneType gemstoneType;
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
        HashMap<Attribute, AttributeModifier> modifierHashMap = new HashMap<>();
        for (Attribute attribute1 : attributeModifier.keySet()) {
            modifierHashMap.put(attribute1, MysticcraftMod.createModifier(AttributeModifier.Operation.ADDITION, attributeModifier.get(attribute1), MISCTools.getSlotForStack(stack)));
        }
        return modifierHashMap;
    }

    public void getModInfo(ItemStack stack, List<Component> list) {
        GemstoneSlot[] slots = this.getGemstoneSlots(stack);
        if (slots != null) {
            boolean flag1 = false;
            for (@Nullable GemstoneSlot slot : slots) {
                flag1 = slot != null && slot.getAppliedGemstone() != null;
                if (flag1) {
                    break;
                }
            }
            if (flag1) {
                ArrayList<Attribute> modified = new ArrayList<>(this.getAttributeModifiers(stack).keySet());
                if (Screen.hasShiftDown()) {
                    list.add(Component.literal("Gemstone Modifications:").withStyle(ChatFormatting.GREEN));
                    for (Attribute attribute : modified) {
                        list.add(Component.translatable(attribute.getDescriptionId()).append(Component.literal(": " + this.getAttributeModifiers(stack).get(attribute))));
                    }
                } else {
                    list.add(Component.literal("press [SHIFT] for Gemstone Information").withStyle(ChatFormatting.GREEN));
                }
            }
        }
    }

    public ArrayList<Attribute> getAttributesModified(ItemStack stack) {
        return new ArrayList<>(getAttributeModifiers(stack).keySet());
    }
}
