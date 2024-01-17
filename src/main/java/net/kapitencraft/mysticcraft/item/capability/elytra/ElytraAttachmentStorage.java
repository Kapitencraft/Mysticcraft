package net.kapitencraft.mysticcraft.item.capability.elytra;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ElytraAttachmentStorage {
    public static final Codec<ElytraAttachmentStorage> CODEC = RecordCodecBuilder.create(elytraAttachmentStorageInstance ->
            elytraAttachmentStorageInstance.group(
                    Codec.unboundedMap(ElytraData.CODEC, Codec.INT).fieldOf("data").forGetter(ElytraAttachmentStorage::getInstances)
            ).apply(elytraAttachmentStorageInstance, ElytraAttachmentStorage::new)
    );
    private final Map<ElytraData, Integer> instances;

    public ElytraAttachmentStorage(Map<ElytraData, Integer> data) {
        this.instances = new HashMap<>(data);
    }

    public void addInstance(ElytraData data, int i) {
        instances.put(data, i);
    }

    public Map<ElytraData, Integer> getInstances() {
        return instances;
    }

    public void copyFrom(ElytraAttachmentStorage storage) {
        instances.clear();
        instances.putAll(storage.getInstances());
    }

    public void forEach(BiConsumer<ElytraData, Integer> consumer) {
        instances.forEach(consumer);
    }
}
