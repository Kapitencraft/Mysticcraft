package net.kapitencraft.mysticcraft;

import org.slf4j.Marker;

import java.util.Iterator;

public final class ModMarker implements Marker {
    private final String name;

    public ModMarker(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void add(Marker reference) {
        MysticcraftMod.sendWarn("tried adding Marker: sike!", this);
    }

    @Override
    public boolean remove(Marker reference) {
        return false;
    }

    @Override
    public boolean hasChildren() {
        return false;
    }

    @Override
    public boolean hasReferences() {
        return false;
    }

    @Override
    public Iterator<Marker> iterator() {
        return null;
    }

    @Override
    public boolean contains(Marker other) {
        return false;
    }

    @Override
    public boolean contains(String name) {
        return this.name.contains(name);
    }
}
