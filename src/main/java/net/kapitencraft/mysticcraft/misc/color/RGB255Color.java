package net.kapitencraft.mysticcraft.misc.color;

import net.minecraft.world.phys.Vec3;

public class RGB255Color {
    int r, g, b;

    public RGB255Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Vec3 toColor() {
        return new Vec3(r, g, b);
    }

    public static RGB255Color fromInt(int intColor) {
        int i = (intColor & 16711680) >> 16;
        int j = (intColor & '\uff00') >> 8;
        int k = (intColor & 255);
        return new RGB255Color(i, j, k);
    }

    public int toInt() {
        return  (((r << 8) + g) << 8) + b;
    }

    public static RGB255Color fromPercentColor(RGBColor color) {
        return new RGB255Color((int) (color.r * 255), (int) (color.g * 255), (int) (color.b * 255));
    }

    public RGBColor toPercentColor() {
        return new RGBColor(r / 255f, g / 255f, b / 255f);
    }
}
