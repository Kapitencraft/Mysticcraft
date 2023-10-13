package net.kapitencraft.mysticcraft.commands;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.networking.ModMessages;
import net.kapitencraft.mysticcraft.networking.packets.S2C.CircleParticlePacket;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public class TestCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> main = dispatcher.register(Commands.literal("my_test")
                .then(Commands.literal("main_test")
                        .executes(context -> exeEnchantmentUpgrades(context.getSource()))
                )
        );
    }

    private static int exeEnchantmentUpgrades(CommandSourceStack stack) {
        ServerPlayer player = stack.getPlayer();
        if (player != null) ModMessages.sendToClientPlayer(new CircleParticlePacket(MathHelper.getEyePosition(player).add(0, 1, 0), MathHelper.RGBtoInt(255, 0, 0), 50, 50), player);
        return 1;
    }
}
