package net.kapitencraft.mysticcraft.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.kapitencraft.mysticcraft.commands.args.GuildArg;
import net.kapitencraft.mysticcraft.commands.args.GuildRankArg;
import net.kapitencraft.mysticcraft.guild.Guild;
import net.kapitencraft.mysticcraft.guild.GuildHandler;
import net.kapitencraft.mysticcraft.networking.ModMessages;
import net.kapitencraft.mysticcraft.networking.packets.S2C.SyncGuildsPacket;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
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
        LiteralCommandNode<CommandSourceStack> main = dispatcher.register(net.minecraft.commands.Commands.literal("guild")
                .then(Commands.literal("create")
                        .then(Commands.argument("name", StringArgumentType.greedyString())
                                .executes(context -> addGuild(StringArgumentType.getString(context, "name"), context))
                        )
                ).then(Commands.literal("join")
                        .then(Commands.argument("name", GuildArg.guild())
                            .executes(context -> joinGuild(GuildArg.getGuild(context, "name"), context, null))
                                .then(Commands.argument("inviteKey", StringArgumentType.word())
                                        .executes(context -> joinGuild(GuildArg.getGuild(context, "name"), context, StringArgumentType.getString(context, "inviteKey")))
                                )
                        )
                ).then(Commands.literal("invite")
                        .then(Commands.argument("toInvite", EntityArgument.player())
                                .executes(context -> inviteToGuild(EntityArgument.getPlayer(context, "toInvite"), context))
                        )
                ).then(Commands.literal("kick")
                        .then(Commands.argument("name", EntityArgument.player())
                                .executes(context -> kickPlayer(EntityArgument.getPlayer(context, "name"), context))
                        )
                ).then(Commands.literal("promote")
                        .then(Commands.argument("name", EntityArgument.player())
                            .executes(context -> promotePlayer(EntityArgument.getPlayer(context, "name"), context, null))
                                .then(Commands.argument("rank", GuildRankArg.rank())
                                        .executes(context -> promotePlayer(EntityArgument.getPlayer(context, "name"), context, GuildRankArg.getRank(context, "rank")))
                                )
                        )
                ).then(Commands.literal("disband")
                        .executes(GuildCommand::disbandGuild)
                ).then(Commands.literal("war")
                        .then(Commands.literal("declare")
                                .then(Commands.argument("target", GuildArg.guild())
                                        .executes(context -> declareWar(GuildArg.getGuild(context, "target"), context))
                                )
                        ).then(Commands.literal("surrender")
                                .then(Commands.argument("target", GuildArg.guild())
                                        .executes(context -> surrenderWar(GuildArg.getGuild(context, "target"), context))
                                )
                        )
                )
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

    private static int addGuild(String name, CommandContext<CommandSourceStack> context) {
        return ModCommands.checkNonConsoleCommand(context, (player, stack) -> {
            Guild guild = getGuild(player);
            if (guild != null) {
                stack.sendFailure(Component.translatable("command.guild.add.alreadyMember", guild.getName()));
                return 0;
            }
            String guildAdd = GuildHandler.getInstance().addNewGuild(name, player);
            if (Objects.equals(guildAdd, "success")) {
                ModCommands.sendSuccess(stack, "command.guild.add.success", name);
                return 1;
            } else {
                stack.sendFailure(Component.translatable("command.guild.add." + guildAdd, name));
                return 0;
            }
        });
    }

    private static int disbandGuild(CommandContext<CommandSourceStack> context) {
        return checkGuildCommand(context, (player, stack, guild) -> {
            if (guild.isOwner(player)) {
                String result = GuildHandler.getInstance().removeGuild(guild.getName());
                if (Objects.equals(result, "success")) {
                    ModMessages.sendToAllConnectedPlayers(value -> SyncGuildsPacket.removeGuild(guild), player.getLevel());
                    ModCommands.sendSuccess(stack, "guild.disband.success");
                    return guild.getMemberAmount();
                } else if (Objects.equals(result, "noSuchGuild")) {
                    throw new IllegalArgumentException("Found Guild with Wrong Name!");
                }
            }
            return 0;
        });
    }

    private static int joinGuild(Guild guild, CommandContext<CommandSourceStack> context, @Nullable String inviteKey) {
        return ModCommands.checkNonConsoleCommand(context, (player, stack) -> {
            String guildName = guild.getName();
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
            target.sendSystemMessage(Component.translatable("guild.invite", component, guild.getName()).withStyle(ChatFormatting.GREEN));
            target.sendSystemMessage(Component.translatable("text.click_here").withStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/guild join " + guild.getName() + " " + inviteKey)).withColor(ChatFormatting.YELLOW)).append(Component.translatable("guild.invite.accept.append").withStyle(ChatFormatting.GREEN)));
            ModCommands.sendSuccess(stack, "command.guild.invite.success", player.getName());
            return 1;
        });
    }


    private static int kickPlayer(Player target, CommandContext<CommandSourceStack> context) {
        return checkGuildCommand(context, (player, stack, guild) -> {
            if (guild.isOwner(target)) {
                stack.sendFailure(Component.translatable("command.guild.kick.isOwner"));
            }
            if (guild.kickMember(target)) {
                ModMessages.sendToAllConnectedPlayers(value -> SyncGuildsPacket.leaveGuild(target), player.getLevel());
                ModCommands.sendSuccess(stack, "command.guild.kick.success", target.getName());
                return 1;
            }
            stack.sendFailure(Component.translatable("command.guild.kick.failed", target.getName()));
            return 0;
        });
    }

    private static int promotePlayer(Player target, CommandContext<CommandSourceStack> context, @Nullable Guild.GuildRank rank) {
        return checkGuildCommand(context, (player, stack, guild) -> {
            if (guild.isOwner(target)) {
                stack.sendFailure(Component.translatable("command.guild.promote.isOwner", target.getName()));
            }
            String result = guild.promote(target, rank);
            if (Objects.equals(result, "success")) {
                Guild.GuildRank newRank = guild.getRank(target);
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
        return target == null ? null : GuildHandler.getInstance().getGuildForPlayer(target);
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