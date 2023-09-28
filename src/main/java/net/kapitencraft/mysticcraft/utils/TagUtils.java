package net.kapitencraft.mysticcraft.utils;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

public class TagUtils {
    private static final String LENGTH_ID = "Length";


    public static boolean checkForIntAbove0(CompoundTag tag, String name) {
        return tag.contains(name) && tag.getInt(name) > 0;
    }

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

    public static int increaseIntOnlyAbove0(CompoundTag tag, String name, int i) {
        if (tag.getInt(name) > 0) {
            return increaseIntegerTagValue(tag, name, i);
        }
        return tag.getInt(name);
    }

    public static int reduceBy1(CompoundTag tag, String name) {
        return increaseIntOnlyAbove0(tag, name, -1);
    }

    public static @NotNull CompoundTag putHashMapTag(@NotNull HashMap<UUID, Integer> hashMap) {
        CompoundTag mapTag = new CompoundTag();
        List<Integer> IntArray = colToList(hashMap.values());
        mapTag.put("Uuids", putUuidList(colToList(hashMap.keySet())));
        mapTag.putIntArray("Ints", IntArray);
        mapTag.putInt(LENGTH_ID, hashMap.size());
        return mapTag;
    }

    public static @NotNull HashMap<UUID, Integer> getHashMapTag(@Nullable CompoundTag tag) {
        HashMap<UUID, Integer> hashMap = new HashMap<>();
        if (tag == null) {
            return hashMap;
        }
        int[] intArray = tag.getIntArray("Ints");
        UUID[] UuidArray = getUuidArray(tag.getCompound("Uuids"));
        if (UuidArray != null) {
            for (int i = 0; i < (intArray.length == UuidArray.length ? intArray.length : 0); i++) {
                hashMap.put(UuidArray[i], intArray[i]);
            }
        }
        return hashMap;
    }

    public static <T> ArrayList<T> colToList(Collection<T> collection) {
        return new ArrayList<>(collection);
    }

    public static <T> ArrayList<T> toList(T ts) {
        ArrayList<T> target = new ArrayList<>();
        Collections.addAll(target, ts);
        return target;
    }

    public static int @NotNull [] getIntArray(CompoundTag tag) {
        if (!tag.contains(LENGTH_ID)) {
            MysticcraftMod.sendWarn("tried to load UUID Array from Tag but Tag isn`t Array Tag");
        } else {
            int length = tag.getInt(LENGTH_ID);
            int[] array = new int[length];
            for (int i = 0; i < length; i++) {
                array[i] = tag.getInt(String.valueOf(i));
            }
            return array;
        }
        return new int[0];
    }

    public static UUID[] getUuidArray(CompoundTag arrayTag) {
        if (!arrayTag.contains(LENGTH_ID)) {
            MysticcraftMod.sendWarn("tried to load UUID Array from Tag but Tag isn`t Array Tag");
        } else {
            int length = arrayTag.getInt(LENGTH_ID);
            UUID[] array = new UUID[length];
            for (int i = 0; i < length; i++) {
                array[i] = arrayTag.getUUID(String.valueOf(i));
            }
            return array;
        }
        return null;
    }

    public static CompoundTag putIntList(List<Integer> list) {
        CompoundTag arrayTag = new CompoundTag();
        for (int i = 0; i < list.size(); i++) {
            arrayTag.putInt(String.valueOf(i), list.get(i));
        }
        arrayTag.putInt(LENGTH_ID, list.size());
        return arrayTag;
    }

    public static CompoundTag putUUIDIntMultiMap(Multimap<UUID, Integer> multimap) {
        CompoundTag tag = new CompoundTag();
        for (UUID uuid : multimap.keySet()) {
            CompoundTag uuidTag = new CompoundTag();
            Iterator<Integer> iterator = multimap.get(uuid).iterator();
            for (int i = 0; i < multimap.get(uuid).size(); i++) {
                if (iterator.hasNext()) {
                    uuidTag.putInt("Value " + i, iterator.next());
                } else {
                    throw new IllegalStateException("that shouldn't happen...");
                }
            }
            tag.put(uuid.toString(), uuidTag);
        }
        return tag;
    }


    public static CompoundTag putUuidList(List<UUID> list) {
        CompoundTag arrayTag = new CompoundTag();
        for (int i = 0; i < list.size(); i++) {
            arrayTag.putUUID(String.valueOf(i), list.get(i));
        }
        arrayTag.putInt(LENGTH_ID, list.size());
        return arrayTag;
    }
}
