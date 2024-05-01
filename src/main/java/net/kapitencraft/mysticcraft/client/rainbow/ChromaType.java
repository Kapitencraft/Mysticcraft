package net.kapitencraft.mysticcraft.client.rainbow;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum ChromaType implements StringRepresentable, IShaderConfig {
    LINEAR("linear", 1),
    CIRCLE("circle", 0),
    RECTANGLE("rectangle", 2);

    private final String name;
    private final int byteId;

    ChromaType(String name, int byteId) {
        this.name = name;
        this.byteId = byteId;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }

    @Override
    public int getConfigId() {
        return byteId;
    }
}
