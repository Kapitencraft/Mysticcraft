package net.kapitencraft.mysticcraft.java_test;

public class JavaTestMain {

    public static void main(String... args) {
        String[] strings = new String[] {"https://hypixel.net/threads/1-0-design-thread-9-foraging-island-and-minecraft-version.5185676/"};
        printArray(Compiler.mirrorStringList(strings));
    }

    private static <T> void printArray(T[] array) {
        for (T t : array) {
            System.out.println(t);
        }
    }
}
