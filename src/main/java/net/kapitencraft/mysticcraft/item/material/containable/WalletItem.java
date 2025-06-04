package net.kapitencraft.mysticcraft.item.material.containable;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.containable.ContainableCapabilityProvider;
import net.kapitencraft.mysticcraft.item.capability.containable.WalletCapability;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;

public class WalletItem extends ContainableItem<Item, WalletCapability> {
    public WalletItem() {
        super(MiscHelper.rarity(Rarity.UNCOMMON), 16);
    }

    @Override
    public TabGroup getGroup() {
        return null;
    }

    @Override
    public boolean canApply(Item item) {
        return item == Items.EMERALD;
    }

    @Override
    public ContainableCapabilityProvider<Item, WalletCapability> makeCapabilityProvider() {
        return new ContainableCapabilityProvider<>(new WalletCapability(), CapabilityHelper.WALLET);
    }
}
