package net.kapitencraft.mysticcraft.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.kapitencraft.kap_lib.helpers.CommandHelper;
import net.kapitencraft.kap_lib.helpers.InventoryHelper;
import net.kapitencraft.mysticcraft.capability.dungeon.IPrestigeAbleItem;
import net.kapitencraft.mysticcraft.capability.dungeon.IStarAbleItem;
import net.kapitencraft.mysticcraft.item.misc.SoulbindHelper;
import net.kapitencraft.mysticcraft.network.ModMessages;
import net.kapitencraft.mysticcraft.network.packets.S2C.ResetCooldownsPacket;
import net.minecraft.ChatFormatting;
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
                .requires(CommandHelper::isModerator)
                .then(Commands.literal("max_ench")
                        .executes(MiscCommand::register)
                ).then(Commands.literal("hyper_max_ench")
                        .executes(MiscCommand::exeEnchantmentUpgrades)
                ).then(Commands.literal("hyper_max")
                        .executes(MiscCommand::exeHyperMax)
                ).then(Commands.literal("reset_cooldowns")
                        .executes(MiscCommand::resetCooldowns)
                ).then(Commands.literal("soulbind_all")
                        .executes(MiscCommand::soulbindAll)
                )
        );
    }

    private static int soulbindAll(CommandContext<CommandSourceStack> context) {
        return CommandHelper.checkNonConsoleCommand(context, (player, stack) -> {
            InventoryHelper.allInventory(player.getInventory()).forEach(SoulbindHelper::setSoulbound);
            return 1;
        });
    }

    private static int register(CommandContext<CommandSourceStack> context) {
        return CommandHelper.checkNonConsoleCommand(context, (player, stack) -> {
            ItemStack stack1 = player.getMainHandItem();
            if (stack1.isEnchantable()) {
                int i = 0;
                Map<Enchantment, Integer> enchantments = new HashMap<>();
                if (Enchantments.SHARPNESS.canEnchant(stack1)) enchantments.put(Enchantments.SHARPNESS, 5);
                if (Enchantments.BLOCK_FORTUNE.canEnchant(stack1)) enchantments.put(Enchantments.BLOCK_FORTUNE, 3);
                if (Enchantments.ALL_DAMAGE_PROTECTION.canEnchant(stack1)) {
                    enchantments.put(Enchantments.ALL_DAMAGE_PROTECTION, 4);
                }
                for (Enchantment enchantment : BuiltInRegistries.ENCHANTMENT) {
                    if (enchantment.canEnchant(stack1) && isCompatible(enchantments, enchantment) && !enchantment.isCurse()) {
                        enchantments.put(enchantment, enchantment.getMaxLevel());
                        i++;
                    }
                }
                enchantments.forEach(stack1::enchant);
                CommandHelper.sendSuccess(stack, "command.misc.max_enchant.success", stack1.getHoverName(), i);
                return 1;
            }
            stack.sendFailure(Component.translatable("command.misc.max_enchant.failed"));
            return 0;
        });
    }

    private static boolean isCompatible(Map<Enchantment, Integer> map, Enchantment enchantment) {
        for (Enchantment enchantment1 : map.keySet()) {
            if (!enchantment.isCompatibleWith(enchantment1)) {
                return false;
            }
        }
        return true;
    }

    private static int exeEnchantmentUpgrades(CommandContext<CommandSourceStack> context) {
        CommandSourceStack stack = context.getSource();
        ServerPlayer serverPlayer = stack.getPlayer();
        if (serverPlayer != null) {
            ItemStack mainHandItem = serverPlayer.getMainHandItem();
            Map<Enchantment, Integer> enchantments = mainHandItem.getAllEnchantments();
            Map<Enchantment, Integer> newEnchantments = new HashMap<>();
            for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                Enchantment key = entry.getKey();
                if (entry.getValue() < Math.floor(key.getMaxLevel() * 1.5)) {
                    newEnchantments.put(key, (int) (key.getMaxLevel() * 1.5));
                } else {
                    newEnchantments.put(key, entry.getValue());
                }
            }
            mainHandItem.getOrCreateTag().remove("Enchantments");
            newEnchantments.forEach(mainHandItem::enchant);
            return 1;
        }
        stack.sendFailure(Component.translatable("command.failed.console"));
        return 0;
    }

    private static int exeHyperMax(CommandContext<CommandSourceStack> context) {
        return CommandHelper.checkNonConsoleCommand(context, (serverPlayer, stack) -> {
            register(context);
            exeEnchantmentUpgrades(context);
            exeExtraUpgrades(context);
            stack.sendSuccess(() -> Component.translatable("command.misc.hyper_max.success").withStyle(ChatFormatting.GREEN), true);
            return 1;
        });
    }

    private static int exeExtraUpgrades(CommandContext<CommandSourceStack> context) {
        return CommandHelper.checkNonConsoleCommand(context, (serverPlayer, stack) -> {
            ItemStack mainHand = serverPlayer.getMainHandItem();

            int prestiges = 0;
            if (mainHand.getItem() instanceof IPrestigeAbleItem) {
                ItemStack prestigedItem = mainHand;
                while (prestigedItem.getItem() instanceof IPrestigeAbleItem prestige && prestige.mayUpgrade(mainHand)) {
                    prestigedItem = prestige.upgrade(mainHand);
                    prestiges++;
                }
                mainHand = prestigedItem;
            }
            int stars = 0;
            if (mainHand.getItem() instanceof IStarAbleItem starAbleItem) {
                stars = starAbleItem.getMaxStars(mainHand) - IStarAbleItem.getStars(mainHand);
                IStarAbleItem.setStars(mainHand, starAbleItem.getMaxStars(mainHand));
            }
            Component component = Component.translatable("command.misc.extra_upgrade.success", prestiges, stars);
            stack.sendSuccess(() -> component, true);
            return 1;
        });
    }

    private static int resetCooldowns(CommandContext<CommandSourceStack> context) {
        return CommandHelper.checkNonConsoleCommand(context, (player, stack) -> {
            ResetCooldownsPacket.resetCooldowns(player);
            ModMessages.sendToClientPlayer(new ResetCooldownsPacket(), player);
            return 1;
        });
    }
}
