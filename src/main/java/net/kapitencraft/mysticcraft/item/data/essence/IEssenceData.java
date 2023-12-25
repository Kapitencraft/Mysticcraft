package net.kapitencraft.mysticcraft.item.data.essence;

import net.kapitencraft.mysticcraft.item.data.ItemData;
import net.kapitencraft.mysticcraft.misc.content.EssenceType;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Consumer;

public interface IEssenceData extends ItemData<EssenceType, IEssenceData> {
    @Override
    default EssenceType loadData(ItemStack stack, Consumer<IEssenceData> ifNull) {
        return EssenceType.CODEC.byName(stack.getOrCreateTag().getString(getTagId()), EssenceType.UNDEAD);
    }

    @Override
    default void getDisplay(ItemStack stack, List<Component> list) {
    }

    @Override
    default String getTagId() {
        return "EssenceData";
    }

    @Override
    default void saveData(ItemStack stack, EssenceType type) {
        stack.getOrCreateTag().putString(getTagId(), type.getSerializedName());
    }
}
