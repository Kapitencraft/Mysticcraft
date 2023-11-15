package net.kapitencraft.mysticcraft.event;

import net.kapitencraft.mysticcraft.item.data.reforging.Reforge;
import net.minecraftforge.eventbus.api.Event;

import java.util.HashMap;

public class RegisterReforgesEvent extends Event {

    private final HashMap<String, Reforge> reforges;

    public RegisterReforgesEvent(HashMap<String, Reforge> reforges) {
        this.reforges = reforges;
    }

    public void addReforge(String name, Reforge reforge) {
        reforges.put(name, reforge);
    }

    public void addReforge(Reforge reforge) {
        addReforge(reforge.getRegistryName(), reforge);
    }
}