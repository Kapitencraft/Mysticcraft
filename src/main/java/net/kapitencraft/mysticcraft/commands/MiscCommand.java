package net.kapitencraft.mysticcraft.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.kapitencraft.kap_lib.helpers.CommandHelper;
import net.kapitencraft.kap_lib.helpers.InventoryHelper;
import net.kapitencraft.mysticcraft.capability.dungeon.IPrestigeAbleItem;
import net.kapitencraft.mysticcraft.capability.dungeon.IStarAbleItem;
import net.kapitencraft.mysticcraft.item.misc.SoulbindHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.EnchantmentTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;

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
                HolderLookup.RegistryLookup<Enchantment> lookup = player.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
                Map<Holder<Enchantment>, Integer> enchantments = new HashMap<>();
                Holder.Reference<Enchantment> sharp = lookup.getOrThrow(Enchantments.SHARPNESS);
                if (sharp.value().canEnchant(stack1)) enchantments.put(sharp, 5);
                Holder.Reference<Enchantment> fortune = lookup.getOrThrow(Enchantments.FORTUNE);
                if (fortune.value().canEnchant(stack1)) enchantments.put(fortune, 3);
                Holder.Reference<Enchantment> prot = lookup.getOrThrow(Enchantments.PROTECTION);
                if (prot.value().canEnchant(stack1)) {
                    enchantments.put(prot, 4);
                }
                for (Holder<Enchantment> enchantment : lookup.listElements().toList()) {
                    if (enchantment.value().canEnchant(stack1) && isCompatible(enchantments, enchantment) && !enchantment.is(EnchantmentTags.CURSE)) {
                        enchantments.put(enchantment, enchantment.value().getMaxLevel());
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

    private static boolean isCompatible(Map<Holder<Enchantment>, Integer> map, Holder<Enchantment> enchantment) {
        for (Holder<Enchantment> enchantment1 : map.keySet()) {
            if (!enchantment.value().exclusiveSet().contains(enchantment1)) {
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
            ItemEnchantments enchantments = mainHandItem.getAllEnchantments(serverPlayer.registryAccess().lookupOrThrow(Registries.ENCHANTMENT));
            Map<Holder<Enchantment>, Integer> newEnchantments = new HashMap<>();
            for (Map.Entry<Holder<Enchantment>, Integer> entry : enchantments.entrySet()) {
                Holder<Enchantment> key = entry.getKey();
                if (entry.getValue() < Math.floor(key.value().getMaxLevel() * 1.5)) {
                    newEnchantments.put(key, (int) (key.value().getMaxLevel() * 1.5));
                } else {
                    newEnchantments.put(key, entry.getValue());
                }
            }

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
}
