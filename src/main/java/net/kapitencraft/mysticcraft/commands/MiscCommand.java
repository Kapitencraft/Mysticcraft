package net.kapitencraft.mysticcraft.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;

import java.util.HashMap;
import java.util.Map;

public class MiscCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> main = dispatcher.register(Commands.literal("misc")
                .requires(stack -> stack.hasPermission(4))
                .then(Commands.literal("max_enchant")
                        .executes(commandContext -> register(commandContext.getSource()))
                )
        );
    }

    private static int register(CommandSourceStack stack) {
        ServerPlayer player = stack.getPlayer();
        if (player != null) {
            ItemStack stack1 = player.getMainHandItem();
            if (stack1.isEnchantable()) {
                int i = 0;
                Map<Enchantment, Integer> enchantments = new HashMap<>();
                if (Enchantments.SHARPNESS.canEnchant(stack1)) enchantments.put(Enchantments.SHARPNESS, 5);
                if (Enchantments.BLOCK_FORTUNE.canEnchant(stack1)) enchantments.put(Enchantments.BLOCK_FORTUNE, 3);
                if (Enchantments.ALL_DAMAGE_PROTECTION.canEnchant(stack1)) enchantments.put(Enchantments.ALL_DAMAGE_PROTECTION, 4);
                enchantments.put(Enchantments.SHARPNESS, 5);
                for (Enchantment enchantment : BuiltInRegistries.ENCHANTMENT) {
                    if (enchantment.canEnchant(stack1) && isCompatible(enchantments, enchantment)) {
                        enchantments.put(enchantment, enchantment.getMaxLevel());
                        i++;
                    }
                }
                enchantments.forEach(stack1::enchant);
                ModCommands.sendSuccess(stack, "command.misc.max_enchant.success", stack1.getHoverName(), i);
                return 1;
            }
            stack.sendFailure(Component.translatable("command.misc.max_enchant.failed"));
        }
        stack.sendFailure(Component.translatable("command.failed.console"));
        return 0;
    }

    private static boolean isCompatible(Map<Enchantment, Integer> map, Enchantment enchantment) {
        for (Enchantment enchantment1 : map.keySet()) {
            if (!enchantment.isCompatibleWith(enchantment1)) {
                return false;
            }
        }
        return true;
    }
}
