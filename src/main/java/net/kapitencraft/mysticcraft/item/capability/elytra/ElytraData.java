package net.kapitencraft.mysticcraft.item.capability.elytra;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum ElytraData implements StringRepresentable {
    SPEED_BOOST(5, "speed_boost"),
    UNBREAKING(1, "unbreaking"),
    MANA_BOOST(5, "mana_boost"),
    GRAVITY_BOOST(3, "gravity");

    public static final EnumCodec<ElytraData> CODEC = StringRepresentable.fromEnum(ElytraData::values);

    private final int maxLevel;
    private final String name;

    ElytraData(int maxLevel, String name) {
        this.maxLevel = maxLevel;
        this.name = name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return name;
    }

    public int getMaxLevel() {
        return maxLevel;
    }
}
