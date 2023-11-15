package net.kapitencraft.mysticcraft.commands.args.type;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.world.entity.EquipmentSlot;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class EquipmentSlotArgumentType implements ArgumentType<EquipmentSlot> {
    @Override
    public EquipmentSlot parse(StringReader stringReader) throws CommandSyntaxException {
        return EquipmentSlot.byName(stringReader.readString());
    }

    public static EquipmentSlot getSlot(CommandContext<CommandSourceStack> stack, String name) {
        return stack.getArgument(name, EquipmentSlot.class);
    }

    public static EquipmentSlotArgumentType slot() {
        return new EquipmentSlotArgumentType();
    }

    @Override
    public Collection<String> getExamples() {
        return Arrays.stream(EquipmentSlot.values()).map(EquipmentSlot::getName).toList();
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return suggestItem(builder);
    }

    private CompletableFuture<Suggestions> suggestItem(SuggestionsBuilder builder) {
        getExamples().forEach(builder::suggest);
        return builder.buildFuture();
    }
}
