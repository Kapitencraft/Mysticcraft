package net.kapitencraft.mysticcraft.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.kapitencraft.mysticcraft.config.ServerModConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.BiFunction;

@Mod.EventBusSubscriber
public class ModCommands {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        GuildCommand.register(dispatcher);
        MiscCommand.exeMaxEnchant(dispatcher);
        ShowItemCommand.register(dispatcher);
    }

    public static void sendSuccess(CommandSourceStack stack, String msg, Object... args) {
        stack.sendSuccess(Component.translatable(msg, args).withStyle(ChatFormatting.GREEN), true);
    }

    public static int checkNonConsoleCommand(CommandContext<CommandSourceStack> context, BiFunction<ServerPlayer, CommandSourceStack, Integer> function) {
        CommandSourceStack stack = context.getSource();
        if (stack.getPlayer() != null) {
            return function.apply(stack.getPlayer(), stack);
        }
        stack.sendFailure(Component.translatable("command.failed.console"));
        return 0;
    }

    public static boolean isModerator(CommandSourceStack stack) {
        return stack.hasPermission(1);
    }

    public static boolean isGameMaster(CommandSourceStack stack) {
        return stack.hasPermission(2);
    }

    public static boolean isAdmin(CommandSourceStack stack) {
        return stack.hasPermission(3);
    }

    public static boolean isOwner(CommandSourceStack stack) {
        return stack.hasPermission(4);
    }

    public static boolean isSocialEnabled(CommandSourceStack stack) {
        return ServerModConfig.enableSocial;
    }
}