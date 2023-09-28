package net.kapitencraft.mysticcraft.spell;

public enum Elements implements Element {
    FIRE("fire"),
    EARTH("earth"),
    WATER("water"),
    AIR("air"),
    SHADOW("shadow"),
    VOID("void");

    private final String name;

    Elements(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}