package net.kapitencraft.mysticcraft.networking.packets.S2C;

import net.kapitencraft.mysticcraft.helpers.NetworkingHelper;
import net.kapitencraft.mysticcraft.networking.packets.ModPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public abstract class MakeParticlePacket implements ModPacket {
    protected Vec3 pos;
    protected Vec3 delta;
    private final ParticleOptions options;
    protected boolean cancelled = false;
    public MakeParticlePacket(ParticleOptions options, double x, double y, double z, double dx, double dy, double dz) {
        this.pos = new Vec3(x, y, z);
        this.delta = new Vec3(dx, dy, dz);
        this.options = options;
    }

    public MakeParticlePacket(FriendlyByteBuf buf) {
        this(readParticle(buf, buf.readById(BuiltInRegistries.PARTICLE_TYPE)), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    abstract void changeValues(ClientLevel toUse);

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        NetworkingHelper.writeVec3(buf, pos);
        NetworkingHelper.writeVec3(buf, delta);
        buf.writeId(BuiltInRegistries.PARTICLE_TYPE, this.options.getType());
        options.writeToNetwork(buf);
    }

    private static <T extends ParticleOptions> T readParticle(FriendlyByteBuf p_132305_, @Nullable ParticleType<T> particleType) {
        if (particleType != null) {
            return particleType.getDeserializer().fromNetwork(particleType, p_132305_);
        } else {
            throw new NullPointerException("reading Particle Options returned null");
        }
    }

    @Override
    public boolean handle(Supplier<NetworkEvent.Context> sup) {
        ClientLevel level = Minecraft.getInstance().level;
        if (level != null && Minecraft.getInstance().getCameraEntity() != null) {
            changeValues(level);
            level.addParticle(options,  pos.x, pos.y, pos.z, delta.x, delta.y, delta.z);
            return true;
        }
        return false;
    }
}
