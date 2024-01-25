package net.kapitencraft.mysticcraft.item.capability.elytra;

import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface IElytraData {

    ElytraData getData();

    int getLevel();

    void setLevel(int level);

    default int getLevelForData(ElytraData data) {
        return data == getData() ? getLevel() : 0;
    }
}