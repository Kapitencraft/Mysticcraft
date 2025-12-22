package net.kapitencraft.mysticcraft.rpg.perks;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public class Perk {
    private final ProgressionType progression;

    public static final Codec<Perk> DIRECT_CODEC = RecordCodecBuilder.create(i -> i.group(
            ProgressionType.CODEC.fieldOf("progression").forGetter(p -> p.progression)
    ).apply(i, Perk::new)); //TODO

    public Perk(ProgressionType progression) {
        this.progression = progression;
    }

    public boolean isPassiveProgression() {
        return progression == ProgressionType.PASSIVE;
    }

    public enum ProgressionType implements StringRepresentable {
        PASSIVE,
        ACTIVE;

        @Override
        public @NotNull String getSerializedName() {
            return this.name().toLowerCase();
        }

        private static final Codec<ProgressionType> CODEC = StringRepresentable.fromEnum(ProgressionType::values);
    }
}
