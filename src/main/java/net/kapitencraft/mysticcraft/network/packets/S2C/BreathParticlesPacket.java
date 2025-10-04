package net.kapitencraft.mysticcraft.network.packets.S2C;

import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.kap_lib.helpers.NetworkHelper;
import net.kapitencraft.kap_lib.io.network.SimplePacket;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record BreathParticlesPacket(ParticleOptions options, int entityId) implements SimplePacket {

    public BreathParticlesPacket(FriendlyByteBuf buf) {
        this(NetworkHelper.readParticleOptions(buf), buf.readInt());
    }

    @Override
    public void toBytes(FriendlyByteBuf friendlyByteBuf) {
        NetworkHelper.writeParticleOptions(friendlyByteBuf, options);
        friendlyByteBuf.writeInt(entityId);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
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
}
