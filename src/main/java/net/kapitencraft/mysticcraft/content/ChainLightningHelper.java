package net.kapitencraft.mysticcraft.content;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.api.Queue;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class ChainLightningHelper {

    public static void spawnLightnings(int level, LivingEntity target, LivingEntity owner, float damage) {
        int spawnAmount = (int) (Math.sqrt(level));
        MiscHelper.repeat(spawnAmount, integer -> lightnings.add(new ChainLightning(target, owner, damage * (1 + level * 0.05f))));
    }

    private static final Queue<ChainLightning> lightnings = Queue.create();

    public static void tick() {
        List<ChainLightning> list = lightnings.getAll();
        lightnings.startQueuing();
        list.removeIf(ChainLightning::tick);
        lightnings.stopQueuing();
    }
}
