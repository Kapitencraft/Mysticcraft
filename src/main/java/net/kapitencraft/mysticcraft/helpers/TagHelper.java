package net.kapitencraft.mysticcraft.helpers;

import com.google.common.collect.Multimap;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.kapitencraft.mysticcraft.ModMarker;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTagVisitor;
import net.minecraft.nbt.TagParser;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.storage.loot.Serializer;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;

public class TagHelper {
    private static final ModMarker TRANSFER_MARKER = new ModMarker("DataFormattingHelper");
    private static final String LENGTH_ID = "Length";
    public static <T, K> Codec<Map<T, K>> makeMapCodec(Codec<T> tCodec, Codec<K> kCodec) {
        return Codec.unboundedMap(tCodec, kCodec);
    }

    public static <T> CodecSerializer<T> createSerializer(Codec<T> codec, T defaulted) {
        return new CodecSerializer<>(codec, defaulted);
    }

    public static <T> CodecSerializer<T> createNullDefaultedSerializer(Codec<T> codec) {
        return createSerializer(codec, null);
    }

    public record CodecSerializer<T>(Codec<T> codec, T defaulted) implements Serializer<T> {
        @Override
            public void serialize(@NotNull JsonObject object, @NotNull T value, @NotNull JsonSerializationContext p_79327_) {
                JsonObject element = (JsonObject) TagHelper.getOrLog(codec.encodeStart(JsonOps.INSTANCE, value), new JsonObject());
                element.asMap().forEach(object::add);
            }

            @Override
            public @NotNull T deserialize(@NotNull JsonObject object, @NotNull JsonDeserializationContext context) {
                return TagHelper.getOrLog(codec.parse(JsonOps.INSTANCE, object), defaulted);
            }
        }


    public static boolean checkForIntAbove0(CompoundTag tag, String name) {
        return tag.contains(name) && tag.getInt(name) > 0;
    }

    public static float increaseFloatTagValue(CompoundTag tag, String name, float f) {
        float value = tag.getFloat(name)+f;
        tag.putFloat(name, value);
        return value;
    }

    public static <T> T getOrLog(DataResult<T> result, T defaulted) {
        return result.resultOrPartial(MysticcraftMod::sendError).orElse(defaulted);
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
        List<Integer> IntArray = CollectionHelper.colToList(hashMap.values());
        mapTag.put("Uuids", putUuidList(CollectionHelper.colToList(hashMap.keySet())));
        mapTag.putIntArray("Ints", IntArray);
        mapTag.putInt(LENGTH_ID, hashMap.size());
        return mapTag;
    }

    @SuppressWarnings("ALL")
    public static void injectCompoundTag(Entity toInject, CompoundTag tag) {
        CompoundTag data = toInject.getPersistentData();
        Set<String> allKeys = tag.getAllKeys();
        allKeys.forEach(s -> {
            if (tag.get(s) != null) {
                if (tag.get(s) instanceof CompoundTag cTag)
                    injectCompoundTag(toInject, cTag);
                else data.put(s, tag.get(s));
            }
        });
    }

    public static String fromCompoundTag(CompoundTag tag) {
        return new StringTagVisitor().visit(tag);
    }

    public static CompoundTag fromString(String s) {
        try {
            return new TagParser(new StringReader(s)).readStruct();
        } catch (CommandSyntaxException e) {
            MysticcraftMod.sendWarn("unable to read Tag '" + s + "': " + e.getMessage());
            return new CompoundTag();
        }
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
