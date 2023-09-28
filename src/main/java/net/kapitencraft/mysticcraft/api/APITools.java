package net.kapitencraft.mysticcraft.api;

import net.kapitencraft.mysticcraft.utils.MiscUtils;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class APITools {
    public static @NotNull LivingEntity[] entityListToArray(@NotNull List<LivingEntity> list) {
        LivingEntity[] ret = new LivingEntity[list.size()];
        MiscUtils.repeatXTimes(list.size(), integer -> ret[integer] = list.get(integer));
        return ret;
    }

    private static HashMap<Character, Integer> getConversationMap() {
        HashMap<Character, Integer> conversationMap = new HashMap<Character, Integer>(16);
        conversationMap.put('0', 0);
        conversationMap.put('1', 1);
        conversationMap.put('2', 2);
        conversationMap.put('3', 3);
        conversationMap.put('4', 4);
        conversationMap.put('5', 5);
        conversationMap.put('6', 6);
        conversationMap.put('7', 7);
        conversationMap.put('8', 8);
        conversationMap.put('9', 9);
        conversationMap.put('A', 10);
        conversationMap.put('B', 11);
        conversationMap.put('C', 12);
        conversationMap.put('D', 13);
        conversationMap.put('E', 14);
        conversationMap.put('F', 15);
        return conversationMap;
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