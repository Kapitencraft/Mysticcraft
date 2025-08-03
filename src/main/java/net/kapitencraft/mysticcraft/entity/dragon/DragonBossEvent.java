package net.kapitencraft.mysticcraft.entity.dragon;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;

public class DragonBossEvent extends ServerBossEvent {

    public DragonBossEvent(Component pName) {
        super(pName, BossBarColor.RED, BossBarOverlay.NOTCHED_20);
    }
}
