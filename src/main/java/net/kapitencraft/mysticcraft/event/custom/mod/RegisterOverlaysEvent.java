package net.kapitencraft.mysticcraft.event.custom.mod;

import net.kapitencraft.mysticcraft.client.render.overlay.OverlayLocation;
import net.kapitencraft.mysticcraft.client.render.overlay.PositionHolder;
import net.kapitencraft.mysticcraft.client.render.overlay.holder.RenderHolder;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class RegisterOverlaysEvent extends Event implements IModBusEvent {
    private final BiConsumer<OverlayLocation, Function<PositionHolder, RenderHolder>> constructorFactory;

    public RegisterOverlaysEvent(BiConsumer<OverlayLocation, Function<PositionHolder, RenderHolder>> constructorFactory) {
        this.constructorFactory = constructorFactory;
    }

    public void addOverlay(OverlayLocation location, Function<PositionHolder, RenderHolder> constructor) {
        constructorFactory.accept(location, constructor);
    }
}
