package net.kapitencraft.mysticcraft.api;

import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class APITools {

    public static @NotNull LivingEntity[] entityListToArray(@NotNull List<LivingEntity> list) {
        LivingEntity[] ret = new LivingEntity[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ret[i] = list.get(i);
        }
        return ret;
    }
}