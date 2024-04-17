package net.kapitencraft.mysticcraft.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.kapitencraft.mysticcraft.client.render.overlay.OverlayController;
import net.kapitencraft.mysticcraft.gui.screen.OverlaysScreen;
import net.kapitencraft.mysticcraft.helpers.ClientHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class OverlaysCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("overlays")
                .then(Commands.literal("change_locs")
                        .executes(OverlaysCommand::execute))
                .then(Commands.literal("cl")
                        .executes(OverlaysCommand::execute))
                .then(Commands.literal("reset")
                        .executes(OverlaysCommand::reset)
                )
        );
    }

    private static int execute(CommandContext<CommandSourceStack> context) {
        ClientHelper.postCommandScreen = new OverlaysScreen();
        return 1;
    }

    private static int reset(CommandContext<CommandSourceStack> context) {
        OverlayController.resetAll();
        ModCommands.sendSuccess(context.getSource(), "command.overlays.reset.success");
        return 1;
    }
}
