package net.kapitencraft.mysticcraft.item.tools.fishing_rods;

import net.kapitencraft.mysticcraft.entity.LavaFishingHook;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.minecraft.world.item.Rarity;

public class LavaFishingRod extends ModFishingRod {
    public LavaFishingRod(Rarity rarity) {
        super(new Properties().rarity(rarity));
    }

    @Override
    public HookCreator creator() {
        return LavaFishingHook::create;
    }

    @Override
    public TabGroup getGroup() {
        return null;
    }
}
