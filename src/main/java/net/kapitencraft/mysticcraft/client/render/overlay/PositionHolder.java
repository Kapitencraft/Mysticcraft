package net.kapitencraft.mysticcraft.client.render.overlay;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.phys.Vec2;
import org.jetbrains.annotations.NotNull;

public class PositionHolder {

    public static final Codec<PositionHolder> CODEC = RecordCodecBuilder.create(positionHolderInstance ->
            positionHolderInstance.group(
                    Axis.CODEC.fieldOf("x").forGetter(h -> h.x),
                    Axis.CODEC.fieldOf("y").forGetter(h -> h.y)
            ).apply(positionHolderInstance, PositionHolder::new)
    );

    private final Axis x;
    private final Axis y;

    private PositionHolder(Axis x, Axis y) {
        this.x = x;
        this.y = y;
    }

    public PositionHolder(float x, float y, float sX, float sY, Alignment aX, Alignment aY) {
        this(new Axis(x, sX, aX), new Axis(y, sY, aY));
    }


    public PositionHolder copy(PositionHolder other) {
        this.x.copy(other.x);
        this.y.copy(other.y);
        return this;
    }

    public void add(Vec2 loc) {
        this.x.value += loc.x;
        this.y.value += loc.y;
    }

    public float getXScale() {
        return x.getScale();
    }

    public float getYScale() {
        return y.getScale();
    }

    public Vec2 getLoc(float width, float height) {
        return new Vec2(this.x.getValue(width), this.y.getValue(height));
    }
    public Vec2 getVisualLoc(float width, float height) {
        return new Vec2(this.x.getVisualLoc(width), this.y.getVisualLoc(height));
    }

    public enum Alignment implements StringRepresentable {
        TOP_LEFT("top"),
        BOTTOM_RIGHT("right"),
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

    private static class Axis {
        private static final Codec<Axis> CODEC = RecordCodecBuilder.create(axisInstance -> axisInstance.group(
                Codec.FLOAT.fieldOf("value").forGetter(Axis::value),
                Codec.FLOAT.fieldOf("scale").forGetter(Axis::getScale),
                Alignment.CODEC.fieldOf("alignment").forGetter(Axis::getAlignment)
        ).apply(axisInstance, Axis::new));
        public float value, scale;
        public Alignment alignment;

        private Axis(float value, float scale, Alignment alignment) {
            this.value = value;
            this.scale = scale;
            this.alignment = alignment;
        }

        private float value() {
            return value;
        }

        public float getScale() {
            return scale;
        }

        public Alignment getAlignment() {
            return alignment;
        }

        /**
         * @param axisMax the screen height/width
         * @return the actual screen location for this axis
         */
        public float getValue(float axisMax) {
            return getVisualLoc(axisMax) / scale;
        }

        /**
         * @return the visual location of this axis
         */
        public float getVisualLoc(float axisMax) {
            return switch (alignment) {
                case TOP_LEFT -> value;
                case MIDDLE -> axisMax / 2 + value;
                case BOTTOM_RIGHT -> axisMax - value;
            };
        }

        private void copy(Axis axis) {
            this.value = axis.value;
            this.scale = axis.scale;
            this.alignment = axis.alignment;
        }
    }
}
