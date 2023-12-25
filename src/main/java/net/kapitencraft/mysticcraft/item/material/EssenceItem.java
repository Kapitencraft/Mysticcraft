package net.kapitencraft.mysticcraft.item.material;

import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.data.essence.IEssenceData;
import net.kapitencraft.mysticcraft.item.misc.IModItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.misc.content.EssenceType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

public class EssenceItem extends Item implements IEssenceData, IModItem {

    public EssenceItem() {
        super(MiscHelper.rarity(Rarity.RARE));
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        EssenceType id = loadData(stack, i -> {});
        return id.getName().append(super.getName(stack));
    }

    @Override
    public TabGroup getGroup() {
        return null;
    }
}
