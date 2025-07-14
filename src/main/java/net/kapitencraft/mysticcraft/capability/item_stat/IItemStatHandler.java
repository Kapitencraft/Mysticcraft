package net.kapitencraft.mysticcraft.capability.item_stat;

import net.kapitencraft.kap_lib.item.capability.AbstractCapability;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

import java.util.Map;

@AutoRegisterCapability
public interface IItemStatHandler extends AbstractCapability<Map<ItemStatCapability.Type, Long>> {

    long getCurrentValue(ItemStatCapability.Type type);

    void increase(ItemStatCapability.Type type, long value);
}
