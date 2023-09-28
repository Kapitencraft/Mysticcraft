package net.kapitencraft.mysticcraft.commands;


import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.kapitencraft.mysticcraft.utils.MathUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

import java.util.Map;

public class TestCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> main = dispatcher.register(Commands.literal("my_test")
                .then(Commands.literal("ench_upgrades")
                        .then(Commands.argument("count", IntegerArgumentType.integer(1)))
                        .executes(context -> exeEnchantmentUpgrades(context.getSource(), IntegerArgumentType.getInteger(context, "count")))
                )
        );
    }

    private static int exeEnchantmentUpgrades(CommandSourceStack stack, int amount) {
        Player player = stack.getPlayer();
        if (player != null) {
            ItemStack mainhand = player.getMainHandItem();
            if (mainhand.isEnchanted()) {
                Map<Enchantment, Integer> enchantments = mainhand.getAllEnchantments();
                while (amount > 0) {
                    for (Enchantment ench : enchantments.keySet()) {
                        if (ench.getMaxLevel() > 1) {
                            if (MathUtils.chance(0.1, player) && amount > 0) {
                                int value = enchantments.get(ench);
                                enchantments.remove(ench);
                                enchantments.put(ench, value + 1);
                                amount--;
                            }
                        }
                    }
                }
                stack.sendSuccess(Component.translatable("test.success.enchantment"), true);
                return 1;
            }
            stack.sendFailure(Component.translatable("test.failed.not_enchanted"));
        }
        return 0;
    }
}
