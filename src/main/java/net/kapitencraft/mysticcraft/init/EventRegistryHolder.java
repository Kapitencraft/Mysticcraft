package net.kapitencraft.mysticcraft.init;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegisterEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class EventRegistryHolder<T> {
    private final ResourceKey<? extends Registry<T>> registryKey;
    private final ResourceLocation name;
    private final Supplier<T> valueSupplier;
    private static final List<EventRegistryHolder<?>> allHolders = new ArrayList<>();

    public EventRegistryHolder(ResourceKey<? extends Registry<T>> registryKey, ResourceLocation name, Supplier<T> valueSupplier) {
        this.registryKey = registryKey;
        this.name = name;
        this.valueSupplier = valueSupplier;
        allHolders.add(this);
    }

    public static void registerAll(RegisterEvent event) {
        allHolders.forEach(holder -> holder.register(event));
    }

    public void register(RegisterEvent event) {
        event.register(registryKey, name, valueSupplier);
    }
}