package net.kapitencraft.mysticcraft.misc.visuals;

import net.minecraft.world.entity.player.Player;

public interface Pingable {
    boolean hasPinged(Player player);

    String display();
}
