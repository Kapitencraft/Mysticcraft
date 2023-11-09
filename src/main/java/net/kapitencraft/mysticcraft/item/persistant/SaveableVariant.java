package net.kapitencraft.mysticcraft.item.persistant;

import net.kapitencraft.mysticcraft.misc.serialization.NbtSerializer;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SaveableVariant<T extends ItemStackSaveable> {
    private final SaveableContainer<T> container = new SaveableContainer<>();
    public static final List<SaveableVariant<?>> SERIALIZERS = new ArrayList<>();

    private final NbtSerializer<T> serializer;
    private final String name;

    public SaveableVariant(NbtSerializer<T> serializer, String name) {
        this.serializer = serializer;
        this.name = name;
        SERIALIZERS.add(this);
    }

    public SaveableContainer<T> getContainer() {
        return container;
    }

    public Tag save(ItemStack stack) {
        return getSerializer().serialize(getContainer().get(stack));
    }

    public T load(ItemStack stack) {
        return getSerializer().deserialize(stack.getTagElement(this.getName()));
    }

    public void loadAndSave(ItemStack stack) {
        getContainer().add(stack, load(stack));
    }

    public NbtSerializer<T> getSerializer() {
        return serializer;
    }

    public String getName() {
        return name;
    }
}
