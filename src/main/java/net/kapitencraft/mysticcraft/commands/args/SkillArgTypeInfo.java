package net.kapitencraft.mysticcraft.commands.args;

import com.google.gson.JsonObject;
import net.kapitencraft.mysticcraft.registry.ModCommandArgumentTypes;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;

public class SkillArgTypeInfo implements ArgumentTypeInfo<SkillArgType, SkillArgTypeInfo.Template> {
    @Override
    public void serializeToNetwork(Template template, FriendlyByteBuf buffer) {

    }

    @Override
    public Template deserializeFromNetwork(FriendlyByteBuf buffer) {
        return new Template();
    }

    @Override
    public void serializeToJson(Template template, JsonObject json) {

    }

    @Override
    public Template unpack(SkillArgType argument) {
        return new Template();
    }

    public static final class Template implements ArgumentTypeInfo.Template<SkillArgType> {

        @Override
        public SkillArgType instantiate(CommandBuildContext context) {
            return new SkillArgType();
        }

        @Override
        public ArgumentTypeInfo<SkillArgType, ?> type() {
            return ModCommandArgumentTypes.SKILL.get();
        }
    }
}
