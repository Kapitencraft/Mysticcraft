package net.kapitencraft.mysticcraft.commands;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class TestCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> main = dispatcher.register(Commands.literal("my_test")
                .then(Commands.literal("main_test")
                        .executes(context -> exeEnchantmentUpgrades(context.getSource()))
                )
        );
    }

    private static int exeEnchantmentUpgrades(CommandSourceStack stack) {
        Player player = stack.getPlayer();
        if (player != null) {
            AttributeInstance maxHealth = player.getAttribute(Attributes.MAX_HEALTH);
            MysticcraftMod.sendInfo("heal");
        }
        return 0;
    }
}
