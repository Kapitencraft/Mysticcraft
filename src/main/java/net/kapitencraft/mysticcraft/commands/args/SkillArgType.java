package net.kapitencraft.mysticcraft.commands.args;

import com.mojang.brigadier.context.CommandContext;
import net.kapitencraft.mysticcraft.rpg.skill.Skill;

public class SkillArgType extends SerializableEnumArgType<Skill> {
    protected SkillArgType() {
        super(Skill.values());
    }

    public static SkillArgType create() {
        return new SkillArgType();
    }

    public static Skill getSkill(CommandContext<?> context, String name) {
        return context.getArgument(name, Skill.class);
    }
}
