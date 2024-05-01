package net.kapitencraft.mysticcraft.java_test;


import net.kapitencraft.mysticcraft.api.APITools;

public class JavaTestMain {

    public static void main(String... args) {
        String sixByte = APITools.transfer("2748Hello");
        System.out.println(sixByte);
        System.out.println(APITools.transfer(sixByte));
    }
}