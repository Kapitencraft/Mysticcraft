package net.kapitencraft.mysticcraft.guild.requests;

import net.kapitencraft.mysticcraft.guild.GuildHandler;
import net.kapitencraft.mysticcraft.networking.IRequestable;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class CreateGuildRequestable implements IRequestable<CreateGuildRequestable.GuildCreatingResult, CreateGuildRequestable.CreateGuildData> {
    @Override
    public void writeToNetwork(GuildCreatingResult target, FriendlyByteBuf buf) {
        target.save(buf);
    }

    @Override
    public GuildCreatingResult getFromNetwork(FriendlyByteBuf buf) {
        return GuildCreatingResult.load(buf);
    }

    @Override
    public void writeRequest(CreateGuildData target, FriendlyByteBuf buf) {
        target.save(buf);
    }

    @Override
    public CreateGuildData readRequest(FriendlyByteBuf buf) {
        return CreateGuildData.load(buf);
    }

    @Override
    public GuildCreatingResult pack(CreateGuildData source, ServerPlayer player) {
        ServerLevel level = player.getLevel();
        GuildHandler handler = GuildHandler.getInstance(level);
        return handler.addNewGuild(source, player);
    }

    public record CreateGuildData(String name, boolean isPublic, ItemStack banner) {
        void save(FriendlyByteBuf buf) {
                buf.writeUtf(name);
                buf.writeBoolean(isPublic);
                buf.writeItem(banner);
        }

        static CreateGuildData load(FriendlyByteBuf buf) {
            return new CreateGuildData(buf.readUtf(), buf.readBoolean(), buf.readItem());
        }
    }

    public record GuildCreatingResult(String name, boolean success) {
        void save(FriendlyByteBuf buf) {
            buf.writeUtf(name);
            buf.writeBoolean(success);
        }

        static GuildCreatingResult load(FriendlyByteBuf buf) {
            return new GuildCreatingResult(buf.readUtf(), buf.readBoolean());
        }
    }
}
