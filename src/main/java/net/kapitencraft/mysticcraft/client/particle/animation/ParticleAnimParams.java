package net.kapitencraft.mysticcraft.client.particle.animation;

import net.kapitencraft.mysticcraft.helpers.NetworkingHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class ParticleAnimParams {
    public static final ParticleAnimParam<Vec3> DELTA = new ParticleAnimParam<>("delta", NetworkingHelper::writeVec3, (buf, clientLevel) -> NetworkingHelper.readVec3(buf));
    public static final ParticleAnimParam<Entity> SOURCE = new ParticleAnimParam<>("source", (buf, entity) -> buf.writeInt(entity.getId()), (buf, level) -> level.getEntity(buf.readInt()));
    public static final ParticleAnimParam<Entity> TARGET = new ParticleAnimParam<>("target", SOURCE::accept, SOURCE);
    public static final ParticleAnimParam<Boolean> LOOP = new ParticleAnimParam<>("loop", FriendlyByteBuf::writeBoolean, (buf, level) -> buf.readBoolean());
    public static final ParticleAnimParam<Integer> ROTATION = new ParticleAnimParam<>("rotation", FriendlyByteBuf::writeInt, (buf, level) -> buf.readInt());
}