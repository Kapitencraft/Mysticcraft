package net.kapitencraft.mysticcraft.helpers;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class NetworkingHelper {

    public static void writeVec3(FriendlyByteBuf buf, Vec3 vec3) {
        buf.writeDouble(vec3.x);
        buf.writeDouble(vec3.y);
        buf.writeDouble(vec3.z);
    }

    public static void writeVector3f(FriendlyByteBuf buf, Vector3f vec) {
        buf.writeFloat(vec.x);
        buf.writeFloat(vec.y);
        buf.writeFloat(vec.z);
    }
}
