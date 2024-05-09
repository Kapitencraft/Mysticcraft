package net.kapitencraft.mysticcraft.client.render.overlay;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.helpers.ClientHelper;
import net.minecraft.network.chat.Component;
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


    public Alignment getXAlignment() {
        return this.x.getAlignment();
    }

    public Alignment getYAlignment() {
        return this.y.getAlignment();
    }

    public void add(Vec2 loc) {
        this.x.add(loc.x);
        this.y.add(loc.y);
    }

    public void scale(float x, float y) {
        this.x.scale *= x;
        this.y.scale *= y;
    }

    public float getXScale() {
        return x.getScale();
    }

    public float getYScale() {
        return y.getScale();
    }

    public Vec2 getLoc(float width, float height) {
        return new Vec2(this.x.getVisualLoc(width), this.y.getVisualLoc(height));
    }

    public PositionHolder createCopy() {
        return new PositionHolder(this.x.value, this.y.value, this.x.scale, this.y.scale, this.x.alignment, this.y.alignment);
    }

    public enum Alignment implements StringRepresentable {
        TOP_LEFT("top"),
        BOTTOM_RIGHT("bottom"),
        MIDDLE("middle");

        private static final EnumCodec<Alignment> CODEC = StringRepresentable.fromEnum(Alignment::values);

        private final String name;

        Alignment(String name) {
            this.name = name;
        }

        public Component getName(boolean x) {
            return Component.translatable("alignment." + (x ? "x" : "y") + "." + this.name);
        }

        public Component getWidthName() {
            return getName(true);
        }

        public Component getHeightName() {
            return getName(false);
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }

        public float convert(Alignment other, float value, float axisMax) {
            return switch (this) {
                case TOP_LEFT ->
                        switch (other) {
                            case BOTTOM_RIGHT -> axisMax - value;
                            case MIDDLE -> (axisMax / 2) + value;
                            case TOP_LEFT -> value;
                        };
                case MIDDLE ->
                        switch (other) {
                            case BOTTOM_RIGHT -> (axisMax / 2) - value;
                            case MIDDLE -> value;
                            case TOP_LEFT -> value - (axisMax / 2);
                        };
                case BOTTOM_RIGHT ->
                        switch (other) {
                            case BOTTOM_RIGHT -> value;
                            case MIDDLE -> (axisMax / 2) - value;
                            case TOP_LEFT -> axisMax - value;
                        };
            };
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
         * @param axisMax width or height of the screen, depending on what axis this is
         * @return the screen - location of this axis
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

        public void add(float var) {
            if (this.alignment == Alignment.BOTTOM_RIGHT) {
                this.value -= var;
            } else {
                this.value += var;
            }
        }

        public void changeAlignment(Alignment change, float axisLength) {
            this.value = change.convert(this.alignment, this.value, axisLength);
            this.alignment = change;
        }
    }

    public void setXAlignment(Alignment alignment) {
        this.x.changeAlignment(alignment, ClientHelper.getScreenWidth());
    }

    public void setYAlignment(Alignment alignment) {
        this.y.changeAlignment(alignment, ClientHelper.getScreenHeight());
    }
}
