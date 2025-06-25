package net.kapitencraft.mysticcraft.item.material;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.capability.essence.IEssenceData;
import net.kapitencraft.mysticcraft.misc.content.EssenceType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import org.jetbrains.annotations.NotNull;

public class EssenceItem extends Item implements IEssenceData {

    public EssenceItem() {
        super(MiscHelper.rarity(Rarity.RARE));
    }

    @Override
    public @NotNull Component getName(@NotNull ItemStack stack) {
        EssenceType id = IEssenceData.loadData(stack);
        return id.getName().append(super.getName(stack));
    }
}
