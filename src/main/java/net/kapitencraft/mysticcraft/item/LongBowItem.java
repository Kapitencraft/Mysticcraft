package net.kapitencraft.mysticcraft.item;

import net.minecraft.world.item.*;

public class LongBowItem extends ModdedBows {
    public final double DIVIDER = 40;
    public static final double ARROW_SPEED_MUL = 5;

    public LongBowItem() {
        super(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT).durability(1320).rarity(Rarity.RARE));
    }
}
