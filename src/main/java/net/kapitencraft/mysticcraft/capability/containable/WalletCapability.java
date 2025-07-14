package net.kapitencraft.mysticcraft.capability.containable;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.Tags;

public class WalletCapability extends ContainableCapability<Item> {

    @Override
    public boolean checkCanInsert(Item item) {
        return item.builtInRegistryHolder().is(Tags.Items.GEMS_EMERALD);
    }
}
