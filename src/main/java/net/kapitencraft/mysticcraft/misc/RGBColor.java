package net.kapitencraft.mysticcraft.misc;

import net.minecraft.world.phys.Vec3;

public class RGBColor {
    float r;
    float g;
    float b;

    private RGBColor(float r, float g, float b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Vec3 toColor() {
        return new Vec3(r, g, b);
    }

    public static RGBColor fromInt(int intColor) {
        int i = (intColor & 16711680) >> 16;
        int j = (intColor & '\uff00') >> 8;
        int k = (intColor & 255);
        return new RGBColor((float) i / 255, (float) j / 255, (float) k / 255);
    }
}
