package net.kapitencraft.mysticcraft.guild.requests;

import net.kapitencraft.mysticcraft.guild.Guild;
import net.kapitencraft.mysticcraft.guild.GuildHandler;
import net.kapitencraft.mysticcraft.networking.IRequestable;
import net.kapitencraft.mysticcraft.networking.SimpleSuccessResult;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public abstract class MemberRequestable<T> implements IRequestable<SimpleSuccessResult, T> {
    private final int actionId;

    protected MemberRequestable(int actionId) {
        this.actionId = actionId;
    }

    @Override
    public void writeToNetwork(SimpleSuccessResult target, FriendlyByteBuf buf) {

    }

    @Override
    public SimpleSuccessResult getFromNetwork(FriendlyByteBuf buf) {
        return null;
    }

    @Override
    public final SimpleSuccessResult pack(T source, ServerPlayer player) {
        Guild guild = GuildHandler.getInstance(player.getLevel()).getGuildForPlayer(player);
        if (guild != null) {
            if (!guild.canDoAction(player, actionId)) return SimpleSuccessResult.fail("missingPermission");
            return packAfterScan(guild, source, player);
        }
        return SimpleSuccessResult.fail("noGuild");
    }

    abstract SimpleSuccessResult packAfterScan(Guild guild, T source, ServerPlayer player);
}
