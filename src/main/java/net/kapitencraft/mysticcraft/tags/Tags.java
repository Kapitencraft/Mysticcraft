package net.kapitencraft.mysticcraft.tags;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public class Tags {

    public static <T> TagKey<T> makeKey(ResourceKey<Registry<T>> key, String id) {
        return TagKey.create(key, new ResourceLocation(id));
    }

    public static <T> TagKey<T> makeModKey(ResourceKey<Registry<T>> key, String id) {
        return makeKey(key, "mysticcraft:" + id);
    }

    public static <T> TagKey<T> makeForgeKey(ResourceKey<Registry<T>> key, String id) {
        return makeKey(key, "forge:" + id);
    }
}
