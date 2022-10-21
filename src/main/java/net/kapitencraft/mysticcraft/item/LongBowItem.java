package net.kapitencraft.mysticcraft.item;

import net.kapitencraft.mysticcraft.item.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.*;

import java.util.ArrayList;
import java.util.HashMap;

public class LongBowItem extends ModdedBows implements IGemstoneApplicable {
    public final double DIVIDER = 40;
    private HashMap<Attribute, Double> attributeModifiers;
    private ArrayList<Attribute> attributesModified;
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

    @Override
    public HashMap<Attribute, Double> getAttributeModifiers() {
        return attributeModifiers;
    }

    @Override
    public ArrayList<Attribute> getAttributesModified() {
        return attributesModified;
    }
}
