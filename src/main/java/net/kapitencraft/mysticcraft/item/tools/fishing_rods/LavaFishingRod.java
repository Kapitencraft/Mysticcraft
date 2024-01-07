package net.kapitencraft.mysticcraft.item.tools.fishing_rods;

import net.kapitencraft.mysticcraft.entity.LavaFishingHook;
import net.kapitencraft.mysticcraft.entity.ModFishingHook;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

public class LavaFishingRod extends ModFishingRod {
    public LavaFishingRod(Rarity rarity) {
        super(new Properties().rarity(rarity));
    }

    @Override
    public TabGroup getGroup() {
        return null;
    }

    @Override
    public ModFishingHook create(Player player, Level level, int luck, int lureSpeed) {
        return LavaFishingHook.create(player, level, luck, lureSpeed);
    }
}
