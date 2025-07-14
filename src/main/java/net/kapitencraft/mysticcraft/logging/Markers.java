package net.kapitencraft.mysticcraft.logging;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public interface Markers {

    Marker MOD_MARKER = getMarker("Mysticcraft");
    Marker BESTIARY_MANAGER = getMarker("BestiaryManager");
    Marker PARTICLE_ENGINE = getMarker("ParticleEngine");
    Marker REGISTRY = getMarker("Registry");
    Marker REFORGE_MANAGER = getMarker("ReforgeManager");
    Marker GEMSTONE = getMarker("GemstoneHandler");
    Marker GEMSTONE_BUILDER = getMarker("GemstoneBuilder");
    Marker REQUESTS = getMarker("RequestHandler");

    static Marker getMarker(String name) {
        return MarkerFactory.getMarker(name);
    }
}