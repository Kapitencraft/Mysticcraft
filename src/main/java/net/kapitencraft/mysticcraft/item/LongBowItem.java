package net.kapitencraft.mysticcraft.item;

import net.kapitencraft.mysticcraft.item.gemstone_slot.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.gemstone_slot.IGemstoneApplicable;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class LongBowItem extends ModdedBows implements IGemstoneApplicable {
    public final double DIVIDER = 40;
    private GemstoneSlot[] gemstoneSlots = new GemstoneSlot[]{GemstoneSlot.OFFENSIVE};
    public static final double ARROW_SPEED_MUL = 5;

    public LongBowItem() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1320).rarity(Rarity.RARE));
    }

    @Override
    public int getGemstoneSlotAmount() {
        return 1;
    }

    @Override
    public GemstoneSlot getGemstoneSlot(int slotLoc) {
        return this.gemstoneSlots[slotLoc];
    }

    @Override
    public GemstoneSlot[] getGemstoneSlots() {
        return this.gemstoneSlots;
    }
}
