package net.kapitencraft.mysticcraft.requirements.type;

import net.minecraft.server.level.ServerPlayer;

import java.util.function.ToIntFunction;

public class RequirementType {
    private final ToIntFunction<ServerPlayer> toId;
    private final int minLevel;

    public RequirementType(ToIntFunction<ServerPlayer> toId, int minLevel) {
        this.toId = toId;
        this.minLevel = minLevel;
    }

    public boolean matchesPlayer(ServerPlayer player) {
        return minLevel >= toId.applyAsInt(player);
    }
}
