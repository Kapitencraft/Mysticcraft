package net.kapitencraft.mysticcraft.content;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class CrimsonDeathRayHelper {
    private static final List<CrimsonDeathRay> RAYS = new ArrayList<>();

    public static void tickAll() {
        RAYS.removeIf(CrimsonDeathRay::tick);
    }

    public static void add(LivingEntity source) {
        RAYS.add(new CrimsonDeathRay(source));
    }
}
