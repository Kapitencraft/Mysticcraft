package net.kapitencraft.mysticcraft.guild.requests;

import com.mojang.datafixers.util.Pair;
import net.kapitencraft.mysticcraft.guild.Guild;
import net.kapitencraft.mysticcraft.networking.SimpleSuccessResult;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public class MutePlayerRequestable extends MemberRequestable<Pair<UUID, Long>> {

    public MutePlayerRequestable() {
        super(Guild.Rank.MUTE_MEMBERS);
    }

    @Override
    public void writeRequest(Pair<UUID, Long> target, FriendlyByteBuf buf) {
        buf.writeUUID(target.getFirst());
        buf.writeLong(target.getSecond());
    }

    @Override
    public Pair<UUID, Long> readRequest(FriendlyByteBuf buf) {
        return new Pair<>(buf.readUUID(), buf.readLong());
    }

    @Override
    SimpleSuccessResult packAfterScan(Guild guild, Pair<UUID, Long> source, ServerPlayer player) {
        guild.muteMember(source.getFirst(), source.getSecond());
        return SimpleSuccessResult.success("success");
    }
}