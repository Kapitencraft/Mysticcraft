package net.kapitencraft.mysticcraft.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;

public class VillagerFleshItem extends Item implements IRNGDrop {
    public VillagerFleshItem() {
        super(new Item.Properties().rarity(Rarity.UNCOMMON));
    }

    @Override
    public int getOdds() {
        return 100;
    }

    @Override
    public DropRarity getRarity() {
        return DropRarity.RARE;
    }
}
