package net.kapitencraft.mysticcraft.guild.requests;

import net.kapitencraft.mysticcraft.guild.Guild;
import net.kapitencraft.mysticcraft.guild.GuildHandler;
import net.kapitencraft.mysticcraft.networking.IRequestable;
import net.kapitencraft.mysticcraft.networking.SimpleSuccessResult;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public class KickMemberRequestable implements IRequestable<SimpleSuccessResult, UUID> {
    @Override
    public void writeToNetwork(SimpleSuccessResult target, FriendlyByteBuf buf) {
        target.save(buf);
    }

    @Override
    public SimpleSuccessResult getFromNetwork(FriendlyByteBuf buf) {
        return SimpleSuccessResult.load(buf);
    }

    @Override
    public void writeRequest(UUID target, FriendlyByteBuf buf) {
        buf.writeUUID(target);
    }

    @Override
    public UUID readRequest(FriendlyByteBuf buf) {
        return buf.readUUID();
    }

    @Override
    public SimpleSuccessResult pack(UUID source, ServerPlayer player) {
        ServerLevel level = player.getLevel();
        Guild guild = GuildHandler.getInstance(level).getGuildForPlayer(player);
        if (guild != null) {
            boolean flag = guild.kickMember(player.level.getPlayerByUUID(source), Guild.KickStatus.KICK);
            return flag ? SimpleSuccessResult.success("success") : SimpleSuccessResult.fail("fail");
        }
        return SimpleSuccessResult.fail("noGuild");
    }
}
