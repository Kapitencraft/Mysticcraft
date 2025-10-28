package net.kapitencraft.mysticcraft.requirement.condition;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.kapitencraft.kap_lib.io.serialization.RegistrySerializer;
import net.kapitencraft.kap_lib.requirements.conditions.abstracts.ReqCondition;
import net.kapitencraft.mysticcraft.registry.ModAttachmentTypes;
import net.kapitencraft.mysticcraft.rpg.skill.Skill;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class SkillLevelRequirementCondition extends ReqCondition<SkillLevelRequirementCondition> {
    private final Skill skill;
    private final int level;

    private static final MapCodec<SkillLevelRequirementCondition> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Skill.CODEC.fieldOf("skill").forGetter(c -> c.skill),
            Codec.INT.fieldOf("level").forGetter(c -> c.level)
            ).apply(i, SkillLevelRequirementCondition::new));
    private static final StreamCodec<RegistryFriendlyByteBuf, SkillLevelRequirementCondition> STREAM_CODEC = StreamCodec.composite(
            Skill.STREAM_CODEC, c -> c.skill,
            ByteBufCodecs.INT, c -> c.level,
            SkillLevelRequirementCondition::new
    );
    public static final RegistrySerializer<SkillLevelRequirementCondition> SERIALIZER = new RegistrySerializer<>(CODEC, STREAM_CODEC);

    public SkillLevelRequirementCondition(Skill skill, int level) {
        this.skill = skill;
        this.level = level;
    }

    @Override
    public boolean matches(LivingEntity player) {
        return player.hasData(ModAttachmentTypes.SKILLS) && player.getData(ModAttachmentTypes.SKILLS).get(skill).getLevel() >= level;
    }

    @Override
    public RegistrySerializer<SkillLevelRequirementCondition> getSerializer() {
        return SERIALIZER;
    }

    @Override
    protected @NotNull Component cacheDisplay() {
        return Component.translatable("skill_level_requirement.display", Component.translatable("skill." + this.skill.getSerializedName()), level);
    }
}
