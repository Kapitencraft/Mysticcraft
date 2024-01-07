package net.kapitencraft.mysticcraft.item.tools.fishing_rods;

import net.kapitencraft.mysticcraft.entity.ModFishingHook;
import net.kapitencraft.mysticcraft.item.misc.IModItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.level.Level;

public abstract class ModFishingRod extends FishingRodItem implements IModItem {
    public ModFishingRod(Properties p_41285_) {
        super(p_41285_.stacksTo(1));
    }

    public abstract ModFishingHook create(Player player, Level level, int luck, int lureSpeed);
}
