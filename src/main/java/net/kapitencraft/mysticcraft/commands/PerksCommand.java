package net.kapitencraft.mysticcraft.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.kapitencraft.kap_lib.helpers.CommandHelper;
import net.kapitencraft.mysticcraft.commands.args.PerkTreeArg;
import net.kapitencraft.mysticcraft.rpg.perks.PerkTree;
import net.kapitencraft.mysticcraft.rpg.perks.ServerPerksManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class PerksCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("perks")
                        .then(Commands.literal("award")
                                .then(Commands.argument("tree", PerkTreeArg.perkTree())
                                        .executes(PerksCommand::awardSingle)
                                        .then(Commands.argument("count", IntegerArgumentType.integer())
                                                .executes(PerksCommand::awardMultiple)
                                        )
                                )
                        )
        );
    }

    private static int awardMultiple(CommandContext<CommandSourceStack> context) {
        return CommandHelper.checkNonConsoleCommand(context, (serverPlayer, commandSourceStack) -> {
            PerkTree tree = PerkTreeArg.getTree(context, "tree");
            int amount = IntegerArgumentType.getInteger(context, "count");
            ServerPerksManager.getOrCreateInstance().getPerks(serverPlayer).award(tree.id(), amount);
            CommandHelper.sendSuccess(commandSourceStack, "command.perks.award_multiple.success", tree.id(), amount);
            return 1;
        });
    }

    private static int awardSingle(CommandContext<CommandSourceStack> context) {
        return CommandHelper.checkNonConsoleCommand(context, (serverPlayer, commandSourceStack) -> {
            PerkTree tree = PerkTreeArg.getTree(context, "tree");
            ServerPerksManager.getOrCreateInstance().getPerks(serverPlayer).award(tree.id(), 1);
            CommandHelper.sendSuccess(commandSourceStack, "command.perks.award.success", tree.id());
            return 1;
        });
    }
}
