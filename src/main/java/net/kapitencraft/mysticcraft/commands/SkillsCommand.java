package net.kapitencraft.mysticcraft.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import net.kapitencraft.kap_lib.core.helpers.CommandHelper;
import net.kapitencraft.mysticcraft.commands.args.SkillArgType;
import net.kapitencraft.mysticcraft.rpg.skill.PlayerSkills;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class SkillsCommand {

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("skills")
                        .then(Commands.literal("grant")
                                .then(Commands.argument("skill", SkillArgType.create())
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0))
                                                .executes(SkillsCommand::grant)
                                        )
                                )
                        ).then(Commands.literal("set")
                                .then(Commands.argument("skill", SkillArgType.create())
                                        .then(Commands.argument("value", IntegerArgumentType.integer(0))
                                                .executes(SkillsCommand::set)
                                        )
                                )
                        )
        );
    }

    private static int set(CommandContext<CommandSourceStack> context) {
        return CommandHelper.checkNonConsoleCommand(context, (player, commandSourceStack) -> {
            PlayerSkills.set(player, SkillArgType.getSkill(context, "skill"), IntegerArgumentType.getInteger(context, "value"));
            return 1;
        });
    }

    private static int grant(CommandContext<CommandSourceStack> context) {
        return CommandHelper.checkNonConsoleCommand(context, (player, commandSourceStack) -> {
            PlayerSkills.reward(player, SkillArgType.getSkill(context, "skill"), IntegerArgumentType.getInteger(context, "amount"), false);
            return 1;
        });
    }
}
