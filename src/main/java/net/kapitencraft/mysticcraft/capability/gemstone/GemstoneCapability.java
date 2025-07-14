package net.kapitencraft.mysticcraft.capability.gemstone;

import com.google.common.collect.Multimap;
import net.kapitencraft.kap_lib.item.modifier_display.EquipmentDisplayExtension;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;

public class GemstoneCapability implements IGemstoneHandler, EquipmentDisplayExtension {
    private GemstoneSlot[] slots;
    private GemstoneSlot[] defaultSlots;
    private final ItemStack stack;
    private final EquipmentSlot slot;

    public GemstoneCapability(ItemStack stack) {
        this.stack = stack;
        this.slot = Mob.getEquipmentSlotForItem(stack);
    }

    @Override
    public boolean putGemstone(GemstoneType gemstoneType, GemstoneType.Rarity rarity, int slotIndex) {
        GemstoneSlot slot = slots[slotIndex];
        return slot.putGemstone(gemstoneType, rarity);
    }



    @Override
    public GemstoneSlot[] getSlots() {
        return slots;
    }

    public GemstoneSlot[] getDefaultSlots() {
        return defaultSlots;
    }

    public int getSlotAmount() {
        return getDefaultSlots().length;
    }

    @Override
    public void setDefault(GemstoneSlot[] slots) {
        defaultSlots = slots;
        ensureFilledSlots();
    }

    private void ensureFilledSlots() {
        if (defaultSlots == null) throw new IllegalStateException("default Slots may not be null!");
        if (slots == null) {
            slots = defaultSlots;
        }
    }

    public List<GemstoneSlot> listSlots() {
        return Arrays.asList(slots);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(EquipmentSlot equipmentSlot) {
        if (slot == equipmentSlot) {
            return getAttributeModifiers(slot, stack);
        }
        return null;
    }

    @Override
    public Style getStyle() {
        return Style.EMPTY.withColor(ChatFormatting.LIGHT_PURPLE);
    }

    @Override
    public Type getType() {
        return Type.DEFAULT;
    }
}
