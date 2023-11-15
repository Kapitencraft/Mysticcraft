package net.kapitencraft.mysticcraft.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.kapitencraft.mysticcraft.commands.args.type.EquipmentSlotArgumentType;
import net.kapitencraft.mysticcraft.helpers.TextHelper;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class ShowItemCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> main = dispatcher.register(Commands.literal("show").requires(ModCommands::isSocialEnabled)
                .then(Commands.argument("slot", EquipmentSlotArgumentType.slot())
                        .executes(commandContext -> showItem(commandContext.getSource(), EquipmentSlotArgumentType.getSlot(commandContext, "slot")))
                )
        );
    }

    private static int showItem(CommandSourceStack stack, EquipmentSlot slot) {
        ServerPlayer player = stack.getPlayer();
        if (player == null) {
            stack.sendFailure(Component.translatable("command.failed.console"));
            return 0;
        }
        ItemStack itemStack = player.getItemBySlot(slot);
        if (itemStack.isEmpty()) {
            stack.sendFailure(Component.translatable("command.show.empty_item"));
        }
        if (player.getServer() != null) {
            PlayerList list = player.getServer().getPlayerList();
            for (ServerPlayer serverPlayer : list.getPlayers()) {
                MutableComponent stackData = (MutableComponent) itemStack.getDisplayName();
                if (serverPlayer.hasPermissions(2)) {
                    String command = TextHelper.createGiveFromStack(serverPlayer.getGameProfile().getName(), itemStack);
                    stackData.withStyle(stackData.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command)));
                }
                serverPlayer.sendSystemMessage(Component.translatable("command.show." + slot.getName(), player.getName(), stackData), false);
            }
            return list.getPlayers().size();
        }
        return 0;
    }
}
