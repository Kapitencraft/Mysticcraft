package net.kapitencraft.mysticcraft.rpg.attributes;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;

import java.util.HashMap;
import java.util.Map;

public class PlayerAttributes {
    Map<Type, Integer> entries = new HashMap<>();

    public enum Type implements StringRepresentable {
        CONSTITUTION,
        INTELLIGENCE,
        STRENGHT,
        ;

        public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);

        @Override
        public String getSerializedName() {
            return this.name().toLowerCase();
        }
    }
}
