package net.kapitencraft.mysticcraft.commands.args;

import com.google.gson.JsonObject;
import com.mojang.brigadier.context.CommandContext;
import net.kapitencraft.mysticcraft.guild.Guild;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GuildRankArg extends SimpleEnumArg<Guild.GuildRank> {
    protected GuildRankArg() {
        super(Guild.GuildRank::getRegistryName, Guild.GuildRank.values());
    }

    public static GuildRankArg rank() {
        return new GuildRankArg();
    }

    public static Guild.GuildRank getRank(CommandContext<CommandSourceStack> context, String name) {
        return context.getArgument(name, Guild.GuildRank.class);
    }

    public static class Info implements ArgumentTypeInfo<GuildRankArg, GuildRankArg.Info.Template> {

        @Override
        public void serializeToNetwork(GuildRankArg.Info.Template p_235375_, FriendlyByteBuf p_235376_) {

        }

        @Override
        public GuildRankArg.Info.Template deserializeFromNetwork(FriendlyByteBuf p_235377_) {
            return new GuildRankArg.Info.Template();
        }

        @Override
        public void serializeToJson(GuildRankArg.Info.Template p_235373_, JsonObject p_235374_) {

        }

        @Override
        public GuildRankArg.Info.Template unpack(GuildRankArg p_235372_) {
            return new GuildRankArg.Info.Template();
        }

        public class Template implements ArgumentTypeInfo.Template<GuildRankArg> {

            @Override
            public GuildRankArg instantiate(CommandBuildContext p_235378_) {
                return new GuildRankArg();
            }

            @Override
            public ArgumentTypeInfo<GuildRankArg, ?> type() {
                return GuildRankArg.Info.this;
            }
        }
    }
}
