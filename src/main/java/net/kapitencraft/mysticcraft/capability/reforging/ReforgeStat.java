package net.kapitencraft.mysticcraft.capability.reforging;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.item.Rarity;

import java.util.HashMap;
import java.util.List;

public class ReforgeStat {
    private final HashMap<Rarity, Double> value = new HashMap<>();

    private ReforgeStat(HashMap<Rarity, Double> value) {
        this.value.putAll(value);
    }

    public double apply(Rarity rarity) {
        if (this.value.containsKey(rarity)) return this.value.get(rarity);
        throw new RuntimeException("Unable to find Rarity " + rarity.name());
    }

    public static ReforgeStat build(double... values) {
        return new Builder(values).build();
    }

    public static class Builder {
        private final List<Double> values;

        public Builder(double... values) {
            ImmutableList.Builder<Double> builder = new ImmutableList.Builder<>();
            for (double value : values) {
                builder.add(value);
            }
            this.values = builder.build();
        }

        public Builder(List<Double> list) {
            values = list;
        }

        public ReforgeStat build() {
            HashMap<Rarity, Double> map = new HashMap<>();
            List<Rarity> rarities = Reforges.getRegisteredRarities();
            if (rarities.size() != values.size()) {
                throw new IllegalStateException("There must be the same amount of Rarities and Values");
            }
            for (int i = 0; i < rarities.size(); i++) {
                map.put(rarities.get(i), values.get(i));
            }
            return new ReforgeStat(map);
        }

    }
}
