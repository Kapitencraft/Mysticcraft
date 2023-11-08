package net.kapitencraft.mysticcraft.item.persistant;

import net.kapitencraft.mysticcraft.misc.serialization.NbtSerializer;

public abstract class SaveableVariant<T extends ItemStackSaveable> {

    private final NbtSerializer<T> serializer;

    protected SaveableVariant(NbtSerializer<T> serializer) {
        this.serializer = serializer;
    }

    public NbtSerializer<T> getSerializer() {
        return serializer;
    }
}
