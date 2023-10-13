package net.kapitencraft.mysticcraft.networking.packets.S2C;

import net.kapitencraft.mysticcraft.init.ModParticleTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

public class CircleParticlePacket extends MakeParticlePacket {
    public CircleParticlePacket(double x, double y, double z, int color, double riseSpeed, double riseSize) {
        super(ModParticleTypes.CIRCLE.get(), x, y, z, color, riseSpeed, riseSize);
    }

    public CircleParticlePacket(Vec3 pos, int color, double riseSpeed, double riseSize) {
        this(pos.x, pos.y, pos.z, color, riseSpeed, riseSize);
    }

    public CircleParticlePacket(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    void changeValues(ClientLevel toUse) {
    }
}
