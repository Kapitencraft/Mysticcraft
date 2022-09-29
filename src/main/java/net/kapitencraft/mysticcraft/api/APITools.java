package net.kapitencraft.mysticcraft.api;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class APITools {

    public static LivingEntity[] entityListToArray(List<LivingEntity> list) {
        LivingEntity[] ret = new LivingEntity[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ret[i] = list.get(i);
        }
        return ret;
    }
}
