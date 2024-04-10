package net.kapitencraft.mysticcraft.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.PrimitiveCodec;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.api.MapStream;
import net.kapitencraft.mysticcraft.api.TriConsumer;
import net.minecraft.nbt.*;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.io.*;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class IOHelper {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String LENGTH_ID = "Length";


    public static boolean checkForIntAbove0(CompoundTag tag, String name) {
        return tag.contains(name) && tag.getInt(name) > 0;
    }


    public static boolean isTagEmpty(@Nullable CompoundTag tag) {
        return tag == null || tag.isEmpty();
    }

    public static float increaseFloatTagValue(CompoundTag tag, String name, float f) {
        float value = tag.getFloat(name)+f;
        tag.putFloat(name, value);
        return value;
    }

    public static <T> T get(DataResult<T> result, @NotNull Supplier<T> defaulted) {
        Optional<T> optional = result.result();
        return optional.orElseGet(defaulted);
    }

    @SuppressWarnings("all")
    public static <T> T loadFile(File file, Codec<T> codec, Supplier<T> defaulted) {
        try {
            createFile(file);
            return get(codec.parse(JsonOps.INSTANCE, Streams.parse(createReader(file))), defaulted);
        } catch (IOException e) {
        }
        return defaulted.get();
    }

    @SuppressWarnings("all")
    private static void createFile(File file) {
        try {
            file.getParentFile().mkdirs();
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            MysticcraftMod.LOGGER.warn("unable to create file '{}': {}", file.getName(),  e.getMessage());
        }
    }

    private static JsonReader createReader(File file) throws FileNotFoundException {
        return new JsonReader(new FileReader(file));
    }

    private static JsonWriter createWriter(File file) throws IOException {
        return new JsonWriter(new FileWriter(file));
    }

    @SuppressWarnings("all")
    public static <T> void saveFile(File file, Codec<T> codec, T in) {
        try {
            createFile(file);
            FileWriter writer = new FileWriter(file);
            writer.write(GSON.toJson(get(codec.encodeStart(JsonOps.INSTANCE, in), JsonObject::new)));
            writer.close();
        } catch (Exception e) {
            MysticcraftMod.LOGGER.warn("unable to save file: {}", e.getMessage());
        }
    }

    public static int increaseIntegerTagValue(CompoundTag tag, String name, int i) {
        int value = tag.getInt(name)+i;
        tag.putInt(name, value);
        return value;
    }

    public static int increaseIntOnlyAbove0(CompoundTag tag, String name, int i) {
        if (checkForIntAbove0(tag, name)) {
            return increaseIntegerTagValue(tag, name, i);
        }
        return tag.getInt(name);
    }

    public static int reduceBy1(CompoundTag tag, String name) {
        return increaseIntOnlyAbove0(tag, name, -1);
    }

    public static @NotNull CompoundTag putHashMapTag(@NotNull HashMap<UUID, Integer> hashMap) {
        CompoundTag mapTag = new CompoundTag();
        List<Integer> IntArray = CollectionHelper.fromAny(hashMap.values());
        mapTag.put("Uuids", putUuidList(CollectionHelper.fromAny(hashMap.keySet())));
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

    public static Stream<CompoundTag> readCompoundList(CompoundTag tag, String name) {
        return readList(tag, name, CompoundTag.class, 10);
    }

    public static <T> Stream<T> readList(CompoundTag tag, String name, Class<T> targetId, int elementId) {
        ListTag listTag = tag.getList(name, elementId);
        return listTag.stream().filter(targetId::isInstance)
                .map(targetId::cast);
    }

    public static <T, K> MapStream<T, K> readMap(CompoundTag tag, String name, BiFunction<CompoundTag, String, T> keyMapper, BiFunction<CompoundTag, String, K> valueMapper) {
        HashMap<T, K> map = new HashMap<>();
        readCompoundList(tag, name)
                .forEach(tag1 -> map.put(keyMapper.apply(tag1, "Key"), valueMapper.apply(tag1, "Value")));
        return MapStream.of(map);
    }

    public static CompoundTag fromString(String s) {
        try {
            return new TagParser(new StringReader(s)).readStruct();
        } catch (CommandSyntaxException e) {
            MysticcraftMod.LOGGER.warn("unable to read Tag '{}': {}", s, e.getMessage());
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

    public static UUID[] getUuidArray(CompoundTag arrayTag) {
        if (!arrayTag.contains(LENGTH_ID)) {
            MysticcraftMod.LOGGER.warn("tried to load UUID Array from Tag but Tag isn`t Array Tag");
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

    public static final PrimitiveCodec<UUID> UUID_CODEC = new PrimitiveCodec<>() {
        @Override
        public <T> DataResult<UUID> read(DynamicOps<T> ops, T input) {
            return ops.getStringValue(input).map(UUID::fromString);
        }

        @Override
        public <T> T write(DynamicOps<T> ops, UUID value) {
            return ops.createString(value.toString());
        }
    };

    public static CompoundTag putUuidList(List<UUID> list) {
        CompoundTag arrayTag = new CompoundTag();
        for (int i = 0; i < list.size(); i++) {
            arrayTag.putUUID(String.valueOf(i), list.get(i));
        }
        arrayTag.putInt(LENGTH_ID, list.size());
        return arrayTag;
    }

    public static <T, K> ListTag writeMap(Map<T, K> map, TriConsumer<CompoundTag, String, T> keyMapper, TriConsumer<CompoundTag, String, K> valueMapper) {
        ListTag listTag = new ListTag();
        map.forEach((t, k) -> {
            CompoundTag tag = new CompoundTag();
            keyMapper.accept(tag, "Key", t);
            valueMapper.accept(tag, "Value", k);
            listTag.add(tag);
        });
        return listTag;
    }

    public static <T> ListTag writeList(List<T> list, Function<T, Tag> mapper) {
        ListTag tag = new ListTag();
        list.stream().map(mapper).forEach(tag::add);
        return tag;
    }

    public static class TagBuilder {
        private final CompoundTag tag = new CompoundTag();

        public static TagBuilder create() {
            return new TagBuilder();
        }

        public <T> TagBuilder withArg(String name, T value, TriConsumer<CompoundTag, String, T> consumer) {
            consumer.accept(tag, name, value);
            return this;
        }

        public TagBuilder withString(String name, String val) {
            return withArg(name, val, CompoundTag::putString);
        }

        public TagBuilder withUUID(String name, UUID val) {
            return withArg(name, val, CompoundTag::putUUID);
        }

        public CompoundTag build() {
            return tag;
        }
    }
}
