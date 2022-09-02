package net.kapitencraft.mysticcraft.misc;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

import static java.lang.Math.sin;

public class MISCTools {
    static class Rotation {
        public final String relative;
        public final int vecrot;
        public final int vecnum;
        public Rotation(String relative, int vecrot, int vecnum) {
            this.relative = relative;
            this.vecrot = vecrot;
            this.vecnum = vecnum;


        }
    }
    public final Rotation EAST = new Rotation("x+", 90, 1);
    public final Rotation WEST = new Rotation("x-", 270,3);
    public final Rotation SOUTH = new Rotation("z+", 180, 2);
    public final Rotation NORTH = new Rotation("z-", 360, 4);

    public static ArrayList<Vec3> LineOfSight(Entity entity, double range, double scaling) {
        Vec3 vec3 = entity.position();
        Vec2 vec2 = entity.getRotationVector();
        double relativeX = 0;
        double relativeY = 0;
        double relativeZ = 0;
        boolean flag = vec2.x < 0;
        double B = flag ? vec2.x * -1 : vec2.x;
        double A = 90;
        double yaw = (double) vec2.y;
        yaw += 180;
        double C = 180 - A - B;
        double dist = scaling;
        double b;
        double c;
        ArrayList<Vec3> line = new ArrayList<>();
        int vector = (int) Math.ceil(yaw / 90);
        double relativeYaw = yaw % 90;
        relativeYaw = relativeYaw < 0 ? relativeYaw * -1 : relativeYaw;
        while (range >= dist) {
            b = dist * sin(B) / sin(A);
            c = dist * sin(C) / sin(A);
            relativeY = flag ? b * -1 : b;
            C = 180 - A - relativeYaw;
            b = c * sin(B) / sin(A);
            c = c * sin(C) / sin(A);
            switch (vector) {
                case 1 : relativeX +=c;
                    if (yaw < 90) {
                        relativeZ -=b;
                    } else {
                        relativeZ +=b;
                    }
                case 2 : relativeZ +=c;
                    if (yaw < 90) {
                        relativeX +=b;
                    } else {
                        relativeX -=b;
                    }
                case 3 : relativeX -=c;
                    if (yaw < 270) {
                        relativeZ +=b;
                    } else {
                        relativeZ -=b;
                    }
                case 4 : relativeZ -=c;
                    if (yaw < 90) {
                        relativeX -=b;
                    } else {
                        relativeX +=b;
                    }
            }
            line.add(new Vec3(vec3.x + relativeX, vec3.y + relativeY, vec3.z + relativeZ));
            dist += scaling;
        }
        return line;
    }

    public static  <V extends Object> ArrayList<V> invertList(ArrayList<V> list) {
        ArrayList<V> out = new ArrayList<>();
        for (int i = list.size(); i >= 0; i--) {
            out.add(list.get(i));
        }
        return out;
    }
}
