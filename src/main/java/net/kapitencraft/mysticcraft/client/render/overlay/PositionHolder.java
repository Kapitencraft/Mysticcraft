package net.kapitencraft.mysticcraft.client.render.overlay;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;

public class PositionHolder {

    public static final Codec<PositionHolder> CODEC = RecordCodecBuilder.create(positionHolderInstance ->
            positionHolderInstance.group(
                    Codec.FLOAT.fieldOf("x").forGetter(PositionHolder::getX),
                    Codec.FLOAT.fieldOf("y").forGetter(PositionHolder::getY),
                    Codec.FLOAT.optionalFieldOf("sX", 1f).forGetter(PositionHolder::getSX),
                    Codec.FLOAT.optionalFieldOf("sY", 1f).forGetter(PositionHolder::getSY),
                    Alignment.CODEC.fieldOf("alignment").forGetter(PositionHolder::getAlignment)
            ).apply(positionHolderInstance, PositionHolder::new)
    );
    public float x, y, sX, sY;
    public Alignment alignment;

    public PositionHolder(float x, float y) {
        this(x, y, 1, 1, Alignment.MIDDLE);
    }

    public PositionHolder(float x, float y, Alignment alignment) {
        this(x, y, 1, 1, alignment);
    }

    public PositionHolder(float x, float y, float sX, float sY, Alignment alignment) {
        this.x = x;
        this.y = y;
        this.sX = sX;
        this.sY = sY;
        this.alignment = alignment;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSX() {
        return sX;
    }

    public float getSY() {
        return sY;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public PositionHolder copy(PositionHolder other) {
        this.x = other.x;
        this.y = other.y;
        this.sX = other.sX;
        this.sY = other.sY;
        this.alignment = other.alignment;
        return this;
    }

    public Vec2 getLoc(float width, float height) {
        return switch (alignment) {
            case TOP -> new Vec2(width * x, y);
            case LEFT -> new Vec2(x, height * y);
            case RIGHT -> new Vec2(width - x, height * y);
            case BOTTOM -> new Vec2(width * x, height - y);
            case MIDDLE -> new Vec2(width / 2 + x, height / 2 + y);
        };
    }

    public enum Alignment implements StringRepresentable {
        TOP("top"),
        RIGHT("right"),
        BOTTOM("bottom"),
        LEFT("left"),
        MIDDLE("middle");

        private static final EnumCodec<Alignment> CODEC = StringRepresentable.fromEnum(Alignment::values);

        private final String name;

        Alignment(String name) {
            this.name = name;
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }
}
