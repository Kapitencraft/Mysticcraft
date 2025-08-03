package net.kapitencraft.mysticcraft.rpg.classes;

import net.kapitencraft.mysticcraft.network.ModMessages;
import net.kapitencraft.mysticcraft.network.packets.S2C.UpdateClassProgressionPacket;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

public class PlayerClass {

    private @Nullable RPGClass rpgClass;
    private final ServerPlayer player;
    private int level;
    private float xp;
    private boolean dirty = false;

    public PlayerClass(ServerPlayer player) {
        this.player = player;
    }

    public void selectClass(RPGClass rpgClass) {
        this.rpgClass = rpgClass;
        rpgClass.select(this.player);
    }

    public void awardXp(float amount) {
        if ((this.xp += amount) >= 100) {
            this.level++;
            this.xp -= 100;
        }
        dirty = true;
    }

    public void setXp(float amount) {
        this.xp = amount;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void flush() {
        if (dirty) {
            ModMessages.sendToClientPlayer(new UpdateClassProgressionPacket(level, xp), this.player);
        }
    }
}
