package net.kapitencraft.mysticcraft.item.reforging;

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

    public static ReforgeStat build(Double... values) {
        return new Builder(values).build();
    }

    public static class Builder {
        private final List<Double> values;

        public Builder(Double... values) {
            this.values = List.of(values);
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
