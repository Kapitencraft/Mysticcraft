package net.kapitencraft.mysticcraft.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.function.BiFunction;

public abstract class Levelable {
    protected static <T extends Levelable> Codec<T> createCodec(BiFunction<Integer, Float, T> constructor) {
        return RecordCodecBuilder.create(i -> i.group(
                Codec.INT.fieldOf("level").forGetter(p -> p.level),
                Codec.FLOAT.fieldOf("xp").forGetter(p -> p.xp)
        ).apply(i, constructor));
    }

    public int level;
    public float xp;
    public float requiredXp;

    public Levelable(int level, float xp) {
        this.level = level;
        this.xp = xp;
        this.requiredXp = calculateNextRequiredXp();
    }

    protected abstract float calculateNextRequiredXp();

    public final void reward(float xp) {
        this.xp += xp;
        while (this.xp >= this.requiredXp) {
            this.level++;
            this.requiredXp = calculateNextRequiredXp();
        }
    }
}
