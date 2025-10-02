package net.kapitencraft.mysticcraft.network.packets.S2C;

import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.kap_lib.io.network.SimplePacket;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.client.particle.flame.FlamesForColors;
import net.kapitencraft.mysticcraft.entity.dragon.Dragon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public record DragonBreathFireParticlesPacket(int entityId) implements SimplePacket {

    public DragonBreathFireParticlesPacket(FriendlyByteBuf buf) {
        this(buf.readInt());
    }

    @Override
    public void toBytes(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(entityId);
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            Minecraft instance = Minecraft.getInstance();
            ClientLevel level = instance.level;
            if (level != null) {
                if (level.getEntity(this.entityId) instanceof Dragon dragon) {
                    Vec3 position = dragon.getEyePosition();
                    Vec2 rot = dragon.getRotationVector();
                    ParticleEngine particleEngine = instance.particleEngine;
                    RandomSource random = dragon.getRandom();
                    for (int i = 0; i < 100; i++) {
                        float vX = (random.nextFloat() - .5f) * 15 + rot.x;
                        float vY = (random.nextFloat() - .5f) * 15 + rot.y;
                        float speed = random.nextFloat() * 5 + .5f;
                        Vec3 vec3 = MathHelper.calculateViewVector(vX, vY).scale(speed);
                        particleEngine.createParticle(FlamesForColors.ORANGE, position.x, position.y, position.z, vec3.x, vec3.y, vec3.z);
                    }
                } else {
                    MysticcraftMod.LOGGER.warn("entity with id {} is not a dragon!", entityId);
                }
            }
        });
    }
}
