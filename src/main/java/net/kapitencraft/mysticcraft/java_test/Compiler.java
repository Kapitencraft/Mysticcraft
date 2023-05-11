package net.kapitencraft.mysticcraft.java_test;

public class Compiler {

    public static String mirror(String s) {
        char[] chars = s.toCharArray();
        char[] charsToReturn = new char[chars.length];
        for (int i = 0; i < chars.length; i++) {
            charsToReturn[i] = chars[chars.length - i - 1];
        }
        return new String(charsToReturn);
    }

    public static String[] mirrorStringList(String[] strings) {
        for (int i = 0; i < strings.length; i++) {
            strings[i] = mirror(strings[i]);
        }
        return strings;
    }
}