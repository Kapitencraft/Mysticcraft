package net.kapitencraft.mysticcraft.client.shaders;

import net.kapitencraft.mysticcraft.event.ModEventFactory;
import net.kapitencraft.mysticcraft.event.custom.RegisterUniformsEvent;

import java.util.HashMap;
import java.util.function.Supplier;

public class UniformsProvider {

    private static final HashMap<String, Supplier<float[]>> vecSuppliers = new HashMap<>();
    private static final HashMap<String, Supplier<Integer>> intSuppliers = new HashMap<>();

    static {
        RegisterUniformsEvent event = new RegisterUniformsEvent(vecSuppliers, intSuppliers);
        ModEventFactory.fireModEvent(event);
    }
}