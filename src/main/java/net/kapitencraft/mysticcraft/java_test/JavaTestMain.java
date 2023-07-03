package net.kapitencraft.mysticcraft.java_test;

import net.kapitencraft.mysticcraft.misc.string_converter.converter.TextToDoubleConverter;

import java.util.HashMap;
import java.util.function.Supplier;

public class JavaTestMain {
    private static HashMap<String, Supplier<Double>> stringTransfers() {
        HashMap<String, Supplier<Double>> stringTransfers = new HashMap<>();
        stringTransfers.put("five", () -> 5.);
        stringTransfers.put("age", ()-> 123.);
        return stringTransfers;
    }

    public static void main(String... args) {
        System.out.println(new TextToDoubleConverter(stringTransfers()).transfer("age / 12 + five"));
    }

    private static <T> void printArray(T[] array) {
        for (T t : array) {
            System.out.println(t);
        }
    }
}
