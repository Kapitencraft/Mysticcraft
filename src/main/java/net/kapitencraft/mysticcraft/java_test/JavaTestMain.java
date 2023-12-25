package net.kapitencraft.mysticcraft.java_test;


import net.kapitencraft.mysticcraft.api.APITools;

public class JavaTestMain {

    public static void main(String... args) {
        String forty8Bit = APITools.transfer("2748Hello");
        System.out.println(forty8Bit);
        System.out.println(APITools.transfer(forty8Bit));
    }

    public static int RGBtoInt(int r, int g, int b) {
        int returnable = (r << 8) + g;
        return (returnable << 8) + b;
    }
}