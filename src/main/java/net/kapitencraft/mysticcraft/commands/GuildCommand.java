package net.kapitencraft.mysticcraft.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.kapitencraft.mysticcraft.guild.Guild;
import net.kapitencraft.mysticcraft.guild.GuildHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;
import java.util.Objects;

public class GuildCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralCommandNode<CommandSourceStack> main = dispatcher.register(Commands.literal("guild")
                .then(Commands.literal("create")
                        .then(Commands.argument("name", StringArgumentType.greedyString())
                                .executes(context -> addGuild(StringArgumentType.getString(context, "name"), context.getSource()))
                        )
                ).then(Commands.literal("join")
                        .then(Commands.argument("name", StringArgumentType.word())
                            .executes(context -> joinGuild(StringArgumentType.getString(context, "name"), context.getSource(), null))
                                .then(Commands.argument("inviteKey", StringArgumentType.word())
                                        .executes(context -> joinGuild(StringArgumentType.getString(context, "name"), context.getSource(), StringArgumentType.getString(context, "inviteKey")))
                                )
                        )
                ).then(Commands.literal("invite")
                        .then(Commands.argument("toInvite", EntityArgument.player())
                                .executes(context -> inviteToGuild(EntityArgument.getPlayer(context, "toInvite"), context.getSource()))
                        )
                ).then(Commands.literal("kick")
                        .then(Commands.argument("name", EntityArgument.player())
                                .executes(context -> kickPlayer(EntityArgument.getPlayer(context, "name"), context.getSource()))
                        )
                ).then(Commands.literal("promote")
                        .then(Commands.argument("name", EntityArgument.player())
                                .executes(context -> promotePlayer(EntityArgument.getPlayer(context, "name"), context.getSource()))
                        )
                ).then(Commands.literal("disband")
                        .executes(context -> disbandGuild(context.getSource()))
                )
        );

        dispatcher.register(Commands.literal("g").redirect(main));
    }

    private static int addGuild(String name, CommandSourceStack stack) {
        Player source = stack.getPlayer();
        String guildAdd = GuildHandler.getInstance().addNewGuild(name, source);
        if (Objects.equals(guildAdd, "success")) {
            stack.sendSuccess(Component.translatable("command.guild.add.success", name), true);
            return 1;
        } else if (Objects.equals(guildAdd, "noBanner")) {
            stack.sendFailure(Component.translatable("command.guild.add.noBanner"));
            return 0;
        }
        stack.sendFailure(Component.translatable("command.guild.add.failed"));
        return 0;
    }

    private static int disbandGuild(CommandSourceStack stack) {
        Player player = stack.getPlayer();
        if (player != null) {
            Guild guild = GuildHandler.getInstance().getGuildForPlayer(player);
            if (guild.getOwner() == player) {
                String msg = GuildHandler.getInstance().removeGuild(guild.getName());
                if (Objects.equals(msg, "success")) {
                    stack.sendSuccess(Component.translatable("guild.disband.success"), true);
                    return guild.getMemberAmount();
                } else if (Objects.equals(msg, "noSuchGuild")) {
                    throw new IllegalArgumentException("Found Guild with Wrong Name!");
                }
            }
        }
        stack.sendFailure(Component.translatable(""));
        return 0;
    }

    private static int joinGuild(String name, CommandSourceStack stack, @Nullable String inviteKey) {
        Guild guild = GuildHandler.getInstance().getGuild(name);
        Player player = stack.getPlayer();
        String guildName = guild.getName();
        if (inviteKey != null) {
            if (guild.acceptInvitation(player, inviteKey)) {
                stack.sendSuccess(Component.translatable("command.guild.join.invite.accept", guildName), true);
                return 1;
            }
            stack.sendFailure(Component.translatable("command.guild.join.invite.failed", guildName));
            return 0;
        }
        if (guild.isPublic()) {
            guild.addMember(player);
            stack.sendSuccess(Component.translatable("command.guild.join.success", guildName), true);
            return 1;
        }
        stack.sendFailure(Component.translatable("command.guild.join.failed", guildName));
        return 0;
    }

    private static int inviteToGuild(Player target, CommandSourceStack stack) {
        Player source = stack.getPlayer();
        Guild guild = getGuild(source);
        if (guild != null) {
            MutableComponent component = (MutableComponent) source.getDisplayName();
            String inviteKey = guild.addInvitation(target);
            if (Objects.equals(inviteKey, "isMember")) {
                stack.sendFailure(Component.translatable("command.guild.invite.isMember", target.getName()));
                return 0;
            } else if (Objects.equals(inviteKey, "isInvited")) {
                stack.sendFailure(Component.translatable("command.guild.invite.isInvited", target.getName()));
                return 0;
            }
            target.sendSystemMessage(Component.translatable("guild.invite", component, guild.getName()));
            target.sendSystemMessage(Component.translatable("guild.invite.accept").withStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/guild join " + guild.getName() + " " + inviteKey))).append("guild.invite.accept.append"));
            return 1;
        }
        return 0;
    }

    private static int kickPlayer(Player target, CommandSourceStack stack) {
        Player source = stack.getPlayer();
        Guild guild = getGuild(source);
        if (guild != null) {
            if (guild.getOwner() == target) {
                stack.sendFailure(Component.translatable("command.guild.kick.isOwner"));
            }
            if (guild.kickMember(target)) {
                stack.sendSuccess(Component.translatable("command.guild.kick.success", target.getName()), true);
                return 1;
            }
        }
        stack.sendFailure(Component.translatable("command.guild.kick.failed", target.getName()));
        return 0;
    }

    private static int promotePlayer(Player target, CommandSourceStack stack) {
        Guild guild = getGuild(target);
        if (guild != null) {
            if (guild.getOwner() == target) {
                stack.sendFailure(Component.translatable("command.guild.promote.isOwner", target.getName()));
            }
            String promoteString = guild.promotePlayer(target);
            if (Objects.equals(promoteString, "success")) {
                stack.sendSuccess(Component.translatable("command.guild.promote.success", target.getName(), guild.getRank(target).getIGName()), true);
                return 1;
            } else if (Objects.equals(promoteString, "owner")) {
                stack.sendFailure(Component.translatable("command.guild.promote.failed.admin"));
                return 0;
            }
        }
        stack.sendFailure(Component.translatable("command.guild.promote.failed", target.getName()));
        return 0;
    }

    private static @Nullable Guild getGuild(@Nullable Player target) {
        if (target == null) return null;
        CompoundTag tag = target.getPersistentData();
        if (tag.contains("GuildName", 8)) {
            String guildName = tag.getString("GuildName");
            return GuildHandler.getInstance().getGuild(guildName);
        }
        return null;
    }

    private static int declareWar(Guild guild, CommandSourceStack stack) {
        Player player = stack.getPlayer();
        if (player != null) {
            Guild ownerGuild = GuildHandler.getInstance().getGuildForPlayer(player);
            if (ownerGuild != null) {
                ownerGuild.declareWar(guild);
                return ownerGuild.getMemberAmount() + guild.getMemberAmount();
            }
        }
        return 0;
    }
}