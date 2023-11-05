package net.kapitencraft.mysticcraft.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ModCommands {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        GuildCommand.register(dispatcher);
        TestCommand.register(dispatcher);
        MiscCommand.register(dispatcher);
    }

    public static void sendSuccess(CommandSourceStack stack, String msg, Object... args) {
        stack.sendSuccess(Component.translatable(msg, args).withStyle(ChatFormatting.GREEN), true);
    }
}