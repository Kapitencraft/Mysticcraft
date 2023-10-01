package net.kapitencraft.mysticcraft.java_test;


import java.io.File;
import java.io.IOException;

public class JavaTestMain {

    public static void main(String... args) throws IOException {
        String dir = new File(".").getCanonicalPath();
        System.out.println(dir);
    }
}