package net.kapitencraft.mysticcraft.client.particle.animation;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class ParticleAnimParam<T> implements BiFunction<FriendlyByteBuf, ClientLevel, T>, BiConsumer<FriendlyByteBuf, Object> {
    private static final HashMap<String, ParticleAnimParam<?>> KEYS = new HashMap<>();
    private final String id;
    private final BiConsumer<FriendlyByteBuf, T> serializer;
    private final BiFunction<FriendlyByteBuf, ClientLevel, T> deserializer;

    public ParticleAnimParam(String id, BiConsumer<FriendlyByteBuf, T> serializer, BiFunction<FriendlyByteBuf, ClientLevel, T> deserializer) {
        this.id = id;
        this.serializer = serializer;
        this.deserializer = deserializer;
        KEYS.put(id, this);
    }

    @Override
    public T apply(FriendlyByteBuf buf, ClientLevel level) {
        return deserializer.apply(buf, level);
    }

    @Override
    public void accept(FriendlyByteBuf buf, Object obj) {
        T val = (T) obj;
        serializer.accept(buf, val);
    }

    public static ParticleAnimParam<?> getParam(String name) {
        return KEYS.get(name);
    }
}