package net.kapitencraft.mysticcraft.commands.args;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.kapitencraft.mysticcraft.guild.Guild;
import net.kapitencraft.mysticcraft.guild.GuildHandler;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.CompletableFuture;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GuildArg implements ArgumentType<Guild> {

    //TODO fix not working on the server due to missing `level` object
    @Override
    public Guild parse(StringReader reader) throws CommandSyntaxException {
        return GuildHandler.getInstance().getGuild(reader.readString());
    }

    public static Guild getGuild(CommandContext<CommandSourceStack> stack, String name) {
        return stack.getArgument(name, Guild.class);
    }

    public static GuildArg guild() {
        return new GuildArg();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(GuildHandler.getInstance().allGuilds().stream().map(Guild::getName), builder);
    }

    public static class Info implements ArgumentTypeInfo<GuildArg, GuildArg.Info.Template> {

        @Override
        public void serializeToNetwork(Template p_235375_, FriendlyByteBuf p_235376_) {
        }

        @Override
        public Template deserializeFromNetwork(FriendlyByteBuf p_235377_) {
            return new Template();
        }

        @Override
        public void serializeToJson(Template p_235373_, JsonObject p_235374_) {

        }

        @Override
        public Template unpack(GuildArg p_235372_) {
            return new Template();
        }

        public class Template implements ArgumentTypeInfo.Template<GuildArg> {
            @Override
            public GuildArg instantiate(CommandBuildContext p_235378_) {
                return new GuildArg();
            }

            @Override
            public ArgumentTypeInfo<GuildArg, ?> type() {
                return GuildArg.Info.this;
            }
        }
    }
}
