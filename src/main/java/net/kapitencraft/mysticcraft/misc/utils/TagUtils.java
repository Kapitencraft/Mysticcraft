package net.kapitencraft.mysticcraft.misc.utils;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

public class TagUtils {

    public static float increaseFloatTagValue(CompoundTag tag, String name, float f) {
        float value = tag.getFloat(name)+f;
        tag.putFloat(name, value);
        return value;
    }

    public static int increaseIntegerTagValue(CompoundTag tag, String name, int i) {
        int value = tag.getInt(name)+i;
        tag.putInt(name, value);
        return value;
    }

    public static @NotNull CompoundTag putHashMapTag(@NotNull HashMap<UUID, Integer> hashMap) {
        CompoundTag mapTag = new CompoundTag();
        List<Integer> IntArray = colToList(hashMap.values());
        mapTag.put("Uuids", putUuidList(colToList(hashMap.keySet())));
        mapTag.putIntArray("Ints", IntArray);
        return mapTag;
    }

    public static @NotNull HashMap<UUID, Integer> getHashMapTag(@Nullable CompoundTag tag) {
        HashMap<UUID, Integer> hashMap = new HashMap<>();
        if (tag == null) {
            return hashMap;
        }
        int[] intArray = tag.getIntArray("Ints");
        UUID[] UuidArray = getUuidArray((CompoundTag) Objects.requireNonNull(tag.get("Uuids")));
        for (int i = 0; i < (intArray.length == Objects.requireNonNull(UuidArray).length ? intArray.length : 0); i++) {
            hashMap.put(UuidArray[i], intArray[i]);
        }
        return hashMap;
    }

    public static <T> ArrayList<T> colToList(Collection<T> collection) {
        return new ArrayList<>(collection);
    }

    @SafeVarargs
    public static <T> ArrayList<T> toList(T... ts) {
        return (ArrayList<T>) List.of(ts);
    }

    public static UUID[] getUuidArray(CompoundTag arrayTag) {
        if (!arrayTag.contains("Length")) {
            MysticcraftMod.sendWarn("tried to load UUID Array from Tag but Tag isn`t Array Tag");
        } else {
            int length = arrayTag.getInt("Length");
            UUID[] array = new UUID[length];
            for (int i = 0; i < length; i++) {
                array[i] = arrayTag.getUUID(String.valueOf(i));
            }
            return array;
        }
        return null;
    }

    public static CompoundTag putUuidList(List<UUID> list) {
        CompoundTag arrayTag = new CompoundTag();
        for (int i = 0; i < list.size(); i++) {
            arrayTag.putUUID(String.valueOf(i), list.get(i));
        }
        arrayTag.putInt("Length", list.size());
        return arrayTag;
    }
}
