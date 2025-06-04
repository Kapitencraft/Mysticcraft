package net.kapitencraft.mysticcraft.item.capability.elytra;

import com.mojang.datafixers.util.Pair;
import net.kapitencraft.kap_lib.item.capability.AbstractCapability;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;

@AutoRegisterCapability
public interface IElytraData extends AbstractCapability<Pair<ElytraData, Integer>> {

    int getLevel();

    void setLevel(int level);

    default int getLevelForData(ElytraData data) {
        return data == getDataType() ? getLevel() : 0;
    }

    ElytraData getDataType();
}