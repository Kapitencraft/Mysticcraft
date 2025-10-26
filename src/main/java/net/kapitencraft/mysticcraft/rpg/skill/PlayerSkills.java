package net.kapitencraft.mysticcraft.rpg.skill;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.kapitencraft.kap_lib.helpers.ExtraStreamCodecs;
import net.kapitencraft.mysticcraft.network.packets.S2C.SkillXpChangedPacket;
import net.kapitencraft.mysticcraft.registry.ModAttachmentTypes;
import net.kapitencraft.mysticcraft.registry.ModAttributes;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentSync;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.EnumMap;
import java.util.Map;

public class PlayerSkills {
    public static final Logger LOGGER = LogUtils.getLogger();

    private final EnumMap<Skill, Progression> skillProgression = new EnumMap<>(Skill.class);

    public static final Codec<PlayerSkills> CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.unboundedMap(Skill.CODEC, Progression.CODEC).fieldOf("progression").forGetter(s -> s.skillProgression)
    ).apply(i, PlayerSkills::fromCodec));
    public static final StreamCodec<ByteBuf, PlayerSkills> STREAM_CODEC = StreamCodec.composite(
            Progression.STREAM_CODEC.apply(ExtraStreamCodecs.map(Skill.STREAM_CODEC)), s -> s.skillProgression,
            PlayerSkills::fromCodec
    );

    public static PlayerSkills fromCodec(Map<Skill, Progression> skillsProgressionMap) {
        PlayerSkills skills = new PlayerSkills();
        skills.skillProgression.putAll(skillsProgressionMap);
        return skills;
    }

    public Progression get(Skill skill) {
        return skillProgression.computeIfAbsent(skill, s-> Progression.create());
    }

    public void update(Skill skill, float xp, int maxXp, int level) {
        Progression progression = this.get(skill);
        progression.xp = xp;
        progression.requiredXp = maxXp;
        progression.level += level;
    }

    public static class Progression {
        int level;
        float xp;
        int requiredXp;

        private static final Codec<Progression> CODEC = RecordCodecBuilder.create(i -> i.group(
                Codec.INT.fieldOf("level").forGetter(Progression::getLevel),
                Codec.FLOAT.fieldOf("xp").forGetter(Progression::getXp),
                Codec.INT.fieldOf("requiredXp").forGetter(Progression::getRequiredXp)
        ).apply(i, Progression::new));
        private static final StreamCodec<ByteBuf, Progression> STREAM_CODEC = StreamCodec.composite(
                ByteBufCodecs.INT, Progression::getLevel,
                ByteBufCodecs.FLOAT, Progression::getXp,
                ByteBufCodecs.INT, Progression::getRequiredXp,
                Progression::new
        );

        public Progression(int level, float xp, int requiredXp) {
            this.level = level;
            this.xp = xp;
            this.requiredXp = requiredXp;
        }

        public static Progression create() {
            return new Progression(0, 0, PlayerSkills.getRequiredXp(1));
        }

        public int getLevel() {
            return level;
        }

        public float getXp() {
            return xp;
        }

        public int getRequiredXp() {
            return requiredXp;
        }

        public void rewardXp(float xp, Player owner) {
            int level = this.level;
            this.xp += xp;
            while (this.xp >= this.requiredXp) { //ensure the level is increased several times
                this.level++;
                this.xp -= this.requiredXp;
                this.requiredXp = PlayerSkills.getRequiredXp(this.level + 1);
            }
            if (this.level > level) {
                owner.getData(ModAttachmentTypes.CHARACTER).awardXp((this.level - level) * 10);
            }
        }

        public void set(int amount, Player owner) {
            this.level = 0;
            this.xp = 0;
            this.requiredXp = PlayerSkills.getRequiredXp(1);
            this.rewardXp(amount, owner);
        }
    }

    private static int getRequiredXp(int level) {
        return 100 * (level + 1);
    }

    public static void reward(@NotNull ServerPlayer player, Skill skill, float amount, boolean scaleWithBoosts) {
        PlayerSkills skills = player.getData(ModAttachmentTypes.SKILLS);
        PlayerSkills.Progression progression = skills.get(skill);
        int level = progression.level;
        if (scaleWithBoosts) {
            double boost = player.getAttributeValue(ModAttributes.XP_BOOSTS.get(skill));
            amount = (float) (amount * (boost / 100));
        }
        progression.rewardXp(amount, player);
        PacketDistributor.sendToPlayer(player, new SkillXpChangedPacket(skill, progression.xp, progression.requiredXp, progression.level - level, amount));
    }

    @SuppressWarnings("UnstableApiUsage")
    public static void set(@NotNull ServerPlayer player, Skill skill, int amount) {
        PlayerSkills skills = player.getData(ModAttachmentTypes.SKILLS);
        skills.get(skill).rewardXp(amount, player);
        AttachmentSync.syncEntityUpdate(player, ModAttachmentTypes.SKILLS.get());
    }

    public static Progression getProgression(Player player, Skill skill) {
        PlayerSkills skills = player.getData(ModAttachmentTypes.SKILLS);
        return skills.get(skill);
    }
}
