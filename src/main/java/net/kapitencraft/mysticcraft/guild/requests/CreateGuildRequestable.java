package net.kapitencraft.mysticcraft.guild.requests;

import net.kapitencraft.mysticcraft.guild.GuildHandler;
import net.kapitencraft.mysticcraft.networking.IRequestable;
import net.kapitencraft.mysticcraft.networking.SimpleSuccessResult;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class CreateGuildRequestable implements IRequestable<SimpleSuccessResult, CreateGuildRequestable.CreateGuildData> {
    @Override
    public void writeToNetwork(SimpleSuccessResult target, FriendlyByteBuf buf) {
        target.save(buf);
    }

    @Override
    public SimpleSuccessResult getFromNetwork(FriendlyByteBuf buf) {
        return SimpleSuccessResult.load(buf);
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
    public SimpleSuccessResult pack(CreateGuildData source, ServerPlayer player) {
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
}
