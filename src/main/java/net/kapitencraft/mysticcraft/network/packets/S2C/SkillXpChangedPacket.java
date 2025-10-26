package net.kapitencraft.mysticcraft.network.packets.S2C;

import io.netty.buffer.ByteBuf;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.ModAttachmentTypes;
import net.kapitencraft.mysticcraft.rpg.skill.PlayerSkills;
import net.kapitencraft.mysticcraft.rpg.skill.Skill;
import net.kapitencraft.mysticcraft.rpg.skill.client.XpGainedToast;
import net.minecraft.client.Minecraft;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public record SkillXpChangedPacket(Skill skill, float xp, int maxXp, int level, float gainedXp) implements CustomPacketPayload {
    public static final Type<SkillXpChangedPacket> TYPE = new Type<>(MysticcraftMod.res("skill_xp_changed"));
    public static final StreamCodec<ByteBuf, SkillXpChangedPacket> STREAM_CODEC = StreamCodec.composite(
            Skill.STREAM_CODEC, SkillXpChangedPacket::skill,
            ByteBufCodecs.FLOAT, SkillXpChangedPacket::xp,
            ByteBufCodecs.INT, SkillXpChangedPacket::maxXp,
            ByteBufCodecs.INT, SkillXpChangedPacket::level,
            ByteBufCodecs.FLOAT, SkillXpChangedPacket::gainedXp,
            SkillXpChangedPacket::new
    );

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft minecraft = Minecraft.getInstance();
            PlayerSkills skills = minecraft.player.getData(ModAttachmentTypes.SKILLS);
            skills.update(this.skill, this.xp, this.maxXp, this.level);
            XpGainedToast.addOrUpdate(minecraft.getToasts(), this.skill, this.xp, this.maxXp, this.level, this.gainedXp);
        });
    }
}
