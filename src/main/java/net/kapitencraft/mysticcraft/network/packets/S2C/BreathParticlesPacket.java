package net.kapitencraft.mysticcraft.network.packets.S2C;

import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record BreathParticlesPacket(ParticleOptions options, int entityId) implements CustomPacketPayload {
    public static final Type<BreathParticlesPacket> TYPE = new Type<>(MysticcraftMod.res("breath_particles"));
    public static final StreamCodec<RegistryFriendlyByteBuf, BreathParticlesPacket> STREAM_CODEC = StreamCodec.composite(
            ParticleTypes.STREAM_CODEC, BreathParticlesPacket::options,
            ByteBufCodecs.INT, BreathParticlesPacket::entityId,
            BreathParticlesPacket::new
    );

    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            Minecraft instance = Minecraft.getInstance();
            ClientLevel level = instance.level;
            if (level != null) {
                Entity entity = level.getEntity(this.entityId);
                if (entity != null) {
                    ParticleEngine particleEngine = instance.particleEngine;
                    RandomSource random = level.getRandom();
                    Vec3 pos = entity.getEyePosition();
                    Vec2 rot = entity.getRotationVector();
                    for (int i = 0; i < 100; i++) {
                        float vX = (random.nextFloat() - .5f) * 15 + rot.x;
                        float vY = (random.nextFloat() - .5f) * 15 + rot.y;
                        float speed = random.nextFloat() * 5 + .5f;
                        Vec3 vec3 = MathHelper.calculateViewVector(vX, vY).scale(speed);
                        particleEngine.createParticle(options, pos.x, pos.y, pos.z, vec3.x, vec3.y, vec3.z);
                    }
                } else {
                    MysticcraftMod.LOGGER.warn("entity with id {} is not a dragon!", entityId);
                }
            }
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return null;
    }
}
