package net.kapitencraft.mysticcraft.item.weapon.ranged.bow;

import net.kapitencraft.mysticcraft.item.gemstone.GemstoneHelper;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class LongBowItem extends ModdedBows implements IGemstoneApplicable {
    public final double DIVIDER = 40;
    private GemstoneSlot[] gemstoneSlots;
    public static final double ARROW_SPEED_MUL = 5;

    public LongBowItem() {
        super(new Item.Properties().durability(1320).rarity(Rarity.RARE));
    }

    @Override
    public GemstoneHelper getHelper() {
        return new GemstoneHelper(getDefaultSlots());
    }

    @Override
    public int getGemstoneSlotAmount() {
        return 1;
    }

    @Override
    public GemstoneSlot[] getDefaultSlots() {
        return new GemstoneSlot[]{GemstoneSlot.OFFENSIVE};
    }

    @Override
    public int getKB() {
        return 3;
    }

    @Override
    public double getDamage() {
        return 9;
    }
}
