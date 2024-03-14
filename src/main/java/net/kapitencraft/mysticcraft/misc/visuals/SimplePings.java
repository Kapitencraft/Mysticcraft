package net.kapitencraft.mysticcraft.misc.visuals;

import net.minecraft.world.entity.player.Player;

import java.util.function.Function;

public enum SimplePings implements Pingable {
    EVERYONE(player -> true, "everyone")
    ;

    private final Function<Player, Boolean> mapper;
    private final String display;

    SimplePings(Function<Player, Boolean> mapper, String display) {
        this.mapper = mapper;
        this.display = display;
    }

    @Override
    public boolean hasPinged(Player player) {
        return mapper.apply(player);
    }

    @Override
    public String display() {
        return display;
    }
}
