package net.kapitencraft.mysticcraft.misc.content;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum EssenceType implements StringRepresentable {
    CRIMSON("crimson"),
    UNDEAD("undead");
    private final String id;

    public static final EnumCodec<EssenceType> CODEC = StringRepresentable.fromEnum(EssenceType::values);

    EssenceType(String id) {
        this.id = id;
    }

    public String getId() {
        return "essence." + id;
    }

    public MutableComponent getName() {
        return Component.translatable(getId());
    }

    @Override
    public @NotNull String getSerializedName() {
        return id;
    }
}
