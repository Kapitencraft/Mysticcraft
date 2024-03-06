package net.kapitencraft.mysticcraft.commands.args;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.kapitencraft.mysticcraft.gui.screen.ChangeGUILocationsScreen;
import net.kapitencraft.mysticcraft.helpers.ClientHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class ChangeGUILocationsCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> main = dispatcher.register(Commands.literal("change_gui_locations")
                .executes(ChangeGUILocationsCommand::execute));
        dispatcher.register(Commands.literal("cgl").redirect(main));
    }

    //TODO fix re-route not working

    private static int execute(CommandContext<CommandSourceStack> context) {
        ClientHelper.postCommandScreen = new ChangeGUILocationsScreen();
        return 1;
    }
}
