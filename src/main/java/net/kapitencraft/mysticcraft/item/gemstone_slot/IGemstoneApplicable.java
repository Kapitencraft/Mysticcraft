package net.kapitencraft.mysticcraft.item.gemstone_slot;

public interface IGemstoneApplicable {
    int getGemstoneSlotAmount();
    GemstoneSlot getGemstoneSlot(int slotLoc);
    GemstoneSlot[] getGemstoneSlots();

}
