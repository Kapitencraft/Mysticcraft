package net.kapitencraft.mysticcraft.inst;

import net.kapitencraft.mysticcraft.misc.visuals.Pingable;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;

public class MysticcraftPlayerInstance implements Pingable {

    private final Player player;

    public MysticcraftPlayerInstance(Player player) {
        this.player = player;
        MinecraftForge.EVENT_BUS.register(this);
        Pingable.allPingables.add(this);
    }

    @Override
    public String toString() {
        return this.display() + "-" + (this.player.level().isClientSide() ? "Client" : "Server") + "-Mysticcraft Player Instance";
    }

    public void remove() {
        MinecraftForge.EVENT_BUS.unregister(this);
        Pingable.allPingables.remove(this);
    }

    @Override
    public boolean pinges(Player player) {
        return player == this.player;
    }

    @Override
    public String display() {
        return this.player.getGameProfile().getName();
    }
}