package net.kapitencraft.mysticcraft.rpg.classes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.rpg.traits.PlayerAttributes;

import java.util.EnumMap;
import java.util.Map;

public class RPGClass {
    private final EnumMap<PlayerAttributes.Type, Integer> traitEntries;

    public static final Codec<RPGClass> DIRECT_CODEC = RecordCodecBuilder.create(i -> i.group(
            Codec.unboundedMap(PlayerAttributes.Type.CODEC, Codec.INT).fieldOf("traits").forGetter(c -> c.traitEntries)
    ).apply(i, RPGClass::new));

    private RPGClass() {
        this.traitEntries = new EnumMap<>(PlayerAttributes.Type.class);
    }

    private RPGClass(Map<PlayerAttributes.Type, Integer> entries) {
        this();
        this.traitEntries.putAll(entries);
    }

    public static RPGClass.Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Map<PlayerAttributes.Type, Integer> entries = new EnumMap<>(PlayerAttributes.Type.class);

        public Builder addTrait(PlayerAttributes.Type type, int amount) {
            this.entries.put(type, amount);
            return this;
        }

        public RPGClass build() {
            return new RPGClass(entries);
        }
    }
}