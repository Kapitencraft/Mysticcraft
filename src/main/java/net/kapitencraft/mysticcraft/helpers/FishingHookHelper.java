package net.kapitencraft.mysticcraft.helpers;

import net.kapitencraft.mysticcraft.entity.ModFishingHook;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.HashMap;

public class FishingHookHelper {
    private static final HashMap<Player, ModFishingHook> fishingHooks = new HashMap<>();


    public static @Nullable ModFishingHook getActiveHook(Player player) {
        return fishingHooks.getOrDefault(player, null);
    }

    public static void setActiveHook(Player player, @Nullable ModFishingHook hook) {
        if (hook == null || hook.level instanceof ServerLevel)  {
            fishingHooks.put(player, hook);
        }
    }

    public static void removeHook(Player player) {
        fishingHooks.remove(player);
    }
}
