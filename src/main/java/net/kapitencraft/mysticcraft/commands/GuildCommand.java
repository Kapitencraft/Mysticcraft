package net.kapitencraft.mysticcraft.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.gui.browse.browsables.GuildScreen;
import net.kapitencraft.mysticcraft.gui.screen.guild.CreateGuildScreen;
import net.kapitencraft.mysticcraft.guild.Guild;
import net.kapitencraft.mysticcraft.guild.GuildHandler;
import net.kapitencraft.mysticcraft.helpers.ClientHelper;
import net.kapitencraft.mysticcraft.networking.ModMessages;
import net.kapitencraft.mysticcraft.networking.packets.S2C.SyncGuildsPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.function.TriFunction;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Objects;

public class GuildCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> main = dispatcher.register(Commands.literal("guild")
                .executes(context -> {
                    GuildHandler handler = GuildHandler.getClientInstance();
                    LocalPlayer player = Minecraft.getInstance().player;
                    if (player != null) {
                        Guild guild = handler.getGuildForPlayer(player);
                        if (guild != null) {
                            try {
                                ClientHelper.postCommandScreen = new GuildScreen(guild);
                            } catch (ClassCastException e) {
                                MysticcraftMod.LOGGER.warn("Your guild's corrupted; removing from handler");
                                handler.removeGuild(guild.getGuildName());
                                return 0;
                            }
                            return 1;
                        }
                    }
                    ClientHelper.postCommandScreen = new CreateGuildScreen();
                    return 1;
                })
        );

        dispatcher.register(Commands.literal("g").redirect(main));
    }

    private static int checkGuildCommand(CommandContext<CommandSourceStack> context, TriFunction<ServerPlayer, CommandSourceStack, @NotNull Guild, Integer> guildConsumer) {
        return ModCommands.checkNonConsoleCommand(context, (player, stack) -> {
            Guild guild = getGuild(player);
            if (guild != null) {
                return guildConsumer.apply(player, stack, guild);
            } else {
                stack.sendFailure(Component.translatable("command.guild.fail.noGuild"));
                return 0;
            }
        });
    }

    private static int disbandGuild(CommandContext<CommandSourceStack> context) {
        return checkGuildCommand(context, (player, stack, guild) -> {
            if (guild.isOwner(player)) {
                String result = GuildHandler.getInstance(player.getLevel()).removeGuild(guild.getGuildName());
                if (Objects.equals(result, "success")) {
                    ModMessages.sendToAllConnectedPlayers(value -> SyncGuildsPacket.removeGuild(guild), player.getLevel());
                    ModCommands.sendSuccess(stack, "command.guild.disband.success", guild.getGuildName());
                    return guild.getMemberAmount();
                } else if (Objects.equals(result, "noSuchGuild")) {
                    throw new IllegalArgumentException("Found Guild with Wrong Name!");
                }
            }
            stack.sendFailure(Component.translatable("command.guild.disband.fail.notOwner", guild.getGuildName()));
            return 0;
        });
    }

    private static int joinGuild(Guild guild, CommandContext<CommandSourceStack> context, @Nullable String inviteKey) {
        return ModCommands.checkNonConsoleCommand(context, (player, stack) -> {
            String guildName = guild.getGuildName();
            if (inviteKey != null) {
                if (guild.acceptInvitation(player, inviteKey)) {
                    ModMessages.sendToAllConnectedPlayers(value -> SyncGuildsPacket.addPlayer(player, guild), player.getLevel());
                    ModCommands.sendSuccess(stack, "command.guild.join.invite.accept", guildName);
                    return 1;
                }
                stack.sendFailure(Component.translatable("command.guild.join.invite.failed", guildName));
                return 0;
            }
            if (guild.containsMember(player.getUUID())) {
                stack.sendFailure(Component.translatable("command.guild.join.already_in", guildName));
            }
            if (guild.isPublic()) {
                guild.addMember(player);
                ModMessages.sendToAllConnectedPlayers(value -> SyncGuildsPacket.addPlayer(player, guild), player.getLevel());
                ModCommands.sendSuccess(stack, "command.guild.join.success", guildName);
                return 1;
            }
            stack.sendFailure(Component.translatable("command.guild.join.failed", guildName));
            return 0;
        });
    }

    private static int inviteToGuild(Player target, CommandContext<CommandSourceStack> context) {
        return checkGuildCommand(context, (player, stack, guild) -> {
            MutableComponent component = (MutableComponent) player.getDisplayName();
            String inviteKey = guild.addInvitation(target);
            if (Objects.equals(inviteKey, "isMember")) {
                stack.sendFailure(Component.translatable("command.guild.invite.isMember", target.getName()));
                return 0;
            } else if (Objects.equals(inviteKey, "isInvited")) {
                stack.sendFailure(Component.translatable("command.guild.invite.isInvited", target.getName()));
                return 0;
            }
            target.sendSystemMessage(Component.translatable("guild.invite", component, guild.getGuildName()).withStyle(ChatFormatting.GREEN));
            target.sendSystemMessage(Component.translatable("text.click_here").withStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/guild join " + guild.getGuildName() + " " + inviteKey)).withColor(ChatFormatting.YELLOW)).append(Component.translatable("guild.invite.accept.append").withStyle(ChatFormatting.GREEN)));
            ModCommands.sendSuccess(stack, "command.guild.invite.success", player.getName());
            return 1;
        });
    }

    private static int leaveGuild(CommandContext<CommandSourceStack> context) {
        return checkGuildCommand(context, (player, stack, guild) -> {
            if (guild.memberLeave(player)) {
                ModCommands.sendSuccess(stack, "command.guild.leave.success", guild.getGuildName());
                return 1;
            }
            stack.sendFailure(Component.translatable("command.guild.leave.fail", guild.getGuildName()));
            return 0;
        });
    }

    private static int promotePlayer(Player target, CommandContext<CommandSourceStack> context, @Nullable Guild.Rank rank) {
        return checkGuildCommand(context, (player, stack, guild) -> {
            if (guild.isOwner(target)) {
                stack.sendFailure(Component.translatable("command.guild.promote.isOwner", target.getName()));
            }
            String result = guild.promote(target, rank);
            if (Objects.equals(result, "success")) {
                Guild.IRank newRank = guild.getRank(target);
                ModMessages.sendToAllConnectedPlayers(value -> SyncGuildsPacket.changeRank(target, newRank), stack.getLevel());
                ModCommands.sendSuccess(stack, "command.guild.promote.success", target.getName(), newRank);
                return 1;
            } else {
                stack.sendFailure(Component.translatable("command.guild.promote." + result));
                return 0;
            }
        });
    }

    private static @Nullable Guild getGuild(@Nullable Player target) {
        return target == null ? null : GuildHandler.getInstance(target.getLevel()).getGuildForPlayer(target);
    }

    private static int declareWar(Guild guild, CommandContext<CommandSourceStack> context) {
        return checkGuildCommand(context, (player, stack, ownerGuild) -> {
            ownerGuild.getWarInstance().startWar(guild);
            return ownerGuild.getMemberAmount() + guild.getMemberAmount();
        });
    }

    private static int surrenderWar(Guild guild, CommandContext<CommandSourceStack> context) {
        return checkGuildCommand(context, (player, stack, ownerGuild) -> {
            ownerGuild.getWarInstance().finalizeWar(guild);
            return 0;
        });
    }
}