package net.kapitencraft.mysticcraft.api;

import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class APITools {

    public static @NotNull LivingEntity[] entityListToArray(@NotNull List<LivingEntity> list) {
        LivingEntity[] ret = new LivingEntity[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ret[i] = list.get(i);
        }
        return ret;
    }

    private static HashMap<Character, Integer> getConversationMap() {
        HashMap<Character, Integer> convertationMap = new HashMap<Character, Integer>(16);
        convertationMap.put('0', 0);
        convertationMap.put('1', 1);
        convertationMap.put('2', 2);
        convertationMap.put('3', 3);
        convertationMap.put('4', 4);
        convertationMap.put('5', 5);
        convertationMap.put('6', 6);
        convertationMap.put('7', 7);
        convertationMap.put('8', 8);
        convertationMap.put('9', 9);
        convertationMap.put('A', 10);
        convertationMap.put('B', 11);
        convertationMap.put('C', 12);
        convertationMap.put('D', 13);
        convertationMap.put('E', 14);
        convertationMap.put('F', 15);
        return convertationMap;
    }

    private static char[] invertCharArray(char[] ts) {
        char[] temp = Arrays.copyOf(ts, ts.length);
        for (int i = (ts.length - 1); i >= 0; i--) {
            temp[i] = ts[(ts.length - 1) - i];
        }
        return temp;
    }

    public static int hexadecimalDecompiler(String input) {
        input = input.toUpperCase();
        char[] chars = invertCharArray(input.toCharArray());
        System.out.println(Arrays.toString(chars));
        int ret = 0;
        char ch;
        for (int i = 0; i < chars.length; i++) {
            ch = chars[i];
            if (getConversationMap().containsKey(ch)) {
                int buffer = (int) Math.pow(16, i) * getConversationMap().get(ch);
                System.out.println("Converting " + ch + ": " + buffer);
                ret += buffer;
            }
        }
        return ret;
    }
}