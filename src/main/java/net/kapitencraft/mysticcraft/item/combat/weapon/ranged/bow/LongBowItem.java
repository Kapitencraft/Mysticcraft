package net.kapitencraft.mysticcraft.item.combat.weapon.ranged.bow;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class LongBowItem extends ModBowItem {
    public final double DIVIDER = 40;
    public static final double ARROW_SPEED_MUL = 5;


    public LongBowItem() {
        super(new Item.Properties().durability(1320).rarity(Rarity.RARE));
    }


    @Override
    public double getDivider() {
        return DIVIDER;
    }

    @Override
    public int getKB() {
        return 3;
    }

    @Override
    public double getDamage() {
        return 5;
    }
}
