package net.kapitencraft.mysticcraft.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.kapitencraft.mysticcraft.gui.screen.ChangeGUILocationsScreen;
import net.kapitencraft.mysticcraft.helpers.ClientHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class ChangeGUILocationsCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("change_gui_locations")
                .executes(ChangeGUILocationsCommand::execute));
        dispatcher.register(Commands.literal("cgl")
                .executes(ChangeGUILocationsCommand::execute));
    }

    private static int execute(CommandContext<CommandSourceStack> context) {
        ClientHelper.postCommandScreen = new ChangeGUILocationsScreen();
        return 1;
    }
}
