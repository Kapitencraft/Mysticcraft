package net.kapitencraft.mysticcraft.item.capability.elytra;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface IElytraData {

    ElytraData getData();

    int getLevel();

    default int getLevelForData(ElytraData data) {
        return data == getData() ? getLevel() : 0;
    }
}