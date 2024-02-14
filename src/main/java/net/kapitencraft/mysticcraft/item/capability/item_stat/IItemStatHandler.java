package net.kapitencraft.mysticcraft.item.capability.item_stat;

import net.kapitencraft.mysticcraft.item.capability.ICapability;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface IItemStatHandler extends ICapability<ItemStatCapability> {

    long getCurrentValue(ItemStatCapability.Type type);

    void increase(ItemStatCapability.Type type, long value);
}
