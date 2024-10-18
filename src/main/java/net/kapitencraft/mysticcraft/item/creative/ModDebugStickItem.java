package net.kapitencraft.mysticcraft.item.creative;

import net.kapitencraft.mysticcraft.item.misc.IModItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

public class ModDebugStickItem extends Item implements IModItem {
    public ModDebugStickItem() {
        super(new Properties().rarity(Rarity.EPIC));
    }

    @Override
    public boolean isFoil(@NotNull ItemStack p_41453_) {
        return true;
    }

    @Override
    public int getUseDuration(@NotNull ItemStack p_41454_) {
        return 72;
    }

    @Override
    public TabGroup getGroup() {
        return null;
    }
}
