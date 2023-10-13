package net.kapitencraft.mysticcraft.java_test;


import java.io.IOException;

public class JavaTestMain {

    public static void main(String... args) throws IOException {
        System.out.println(RGBtoInt(255, 0, 0));
    }

    public static int RGBtoInt(int r, int g, int b) {
        int returnable = (r << 8) + g;
        return (returnable << 8) + b;
    }
}