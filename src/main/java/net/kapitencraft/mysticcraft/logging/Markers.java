package net.kapitencraft.mysticcraft.logging;

import org.slf4j.Marker;

public interface Markers {

    Marker MOD_MARKER = new ModMarker("Mysticcraft");
    Marker BESTIARY_MANAGER = new ModMarker("BestiaryManager");
    Marker PARTICLE_ENGINE = new ModMarker("ParticleEngine");
    Marker REGISTRY = new ModMarker("Registry");
    Marker REFORGE_MANAGER = new ModMarker("ReforgeManager");
    Marker GEMSTONE = new ModMarker("GemstoneHandler");
    Marker GEMSTONE_BUILDER = new ModMarker("GemstoneBuilder");
    Marker REQUESTS = new ModMarker("RequestHandler");
}