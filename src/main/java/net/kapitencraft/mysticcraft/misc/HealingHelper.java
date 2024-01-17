package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;

public class HealingHelper {
    private static final HashMap<LivingEntity, HealReason> healReasons = new HashMap<>();

    public static void setReason(LivingEntity living, HealReason reason) {
        healReasons.put(living, reason);
    }

    public static void setEffectReason(LivingEntity living) {
        healReasons.put(living, HealReason.EFFECT);
    }

    public static HealReason getAndRemoveReason(LivingEntity living) {
        HealReason reason = HealReason.ELSE;
        if (healReasons.containsKey(living)) {
            reason = healReasons.get(living);
            healReasons.remove(living);
        } else {
            MysticcraftMod.LOGGER.warn("unable to find Healing Reason for {}, using default", living.getName().getString());
        }
        return reason;
    }

    public enum HealReason {
        EFFECT("effect"),
        NATURAL("natural"),
        ELSE("else");

        private final String name;

        HealReason(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}