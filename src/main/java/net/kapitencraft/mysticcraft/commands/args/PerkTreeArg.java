package net.kapitencraft.mysticcraft.commands.args;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.kapitencraft.mysticcraft.rpg.perks.PerkTree;
import net.kapitencraft.mysticcraft.rpg.perks.ServerPerksManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class PerkTreeArg implements ArgumentType<PerkTree> {
    private static final SimpleCommandExceptionType ERROR_UNKNOWN = new SimpleCommandExceptionType(Component.translatable("argument.perk_tree.unknown"));
    private final ServerPerksManager manager;

    private PerkTreeArg() {
        manager = ServerPerksManager.getOrCreateInstance();
    }

    public static PerkTreeArg perkTree() {
        return new PerkTreeArg();
    }

    @Override
    public PerkTree parse(StringReader reader) throws CommandSyntaxException {
        ResourceLocation location = ResourceLocation.read(reader);
        PerkTree tree = manager.getTree(location);
        if (tree == null) throw ERROR_UNKNOWN.createWithContext(reader);
        return tree;
    }

    public static PerkTree getTree(CommandContext<CommandSourceStack> context, String name) {
        return context.getArgument(name, PerkTree.class);
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return manager.createTreeSuggestions(builder);
    }
}
