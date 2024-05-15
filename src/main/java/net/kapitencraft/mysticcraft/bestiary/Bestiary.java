package net.kapitencraft.mysticcraft.bestiary;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public record Bestiary(Type type, double combatXp) {
    public static final Codec<Bestiary> CODEC = RecordCodecBuilder.create(bestiaryInstance ->
            bestiaryInstance.group(
                    Type.CODEC.fieldOf("type").forGetter(Bestiary::type),
                    Codec.DOUBLE.fieldOf("combat_xp").forGetter(Bestiary::combatXp)
            ).apply(bestiaryInstance, Bestiary::new)
    );

    public enum Type implements StringRepresentable {
        NORMAL("normal"),
        MINI_BOSS("mini_boss"),
        BOSS("boss");

        private static final EnumCodec<Type> CODEC = StringRepresentable.fromEnum(Type::values);

        private final String name;

        Type(String name) {
            this.name = name;
        }


        public static Type getByName(String name) {
            return CODEC.byName(name, NORMAL);
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }
}
