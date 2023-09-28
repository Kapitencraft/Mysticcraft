package net.kapitencraft.mysticcraft.item.tools.fishing_rods;

import net.kapitencraft.mysticcraft.entity.LavaFishingHook;
import net.minecraft.world.item.Rarity;

public class LavaFishingRod extends ModFishingRod {
    public LavaFishingRod(Rarity rarity) {
        super(new Properties().rarity(rarity));
    }

    @Override
    public HookCreator creator() {
        return LavaFishingHook::create;
    }
}
