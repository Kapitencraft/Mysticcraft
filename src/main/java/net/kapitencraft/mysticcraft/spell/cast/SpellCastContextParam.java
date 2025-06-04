package net.kapitencraft.mysticcraft.spell.cast;

import net.minecraft.resources.ResourceLocation;

public class SpellCastContextParam<T> {
    private final ResourceLocation name;

    public SpellCastContextParam(ResourceLocation name) {
        this.name = name;
    }
}
