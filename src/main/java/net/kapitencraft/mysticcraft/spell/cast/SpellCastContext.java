package net.kapitencraft.mysticcraft.spell.cast;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SpellCastContext {
    private final Map<SpellCastContextParam<?>, Object> params;

    private SpellCastContext(Map<SpellCastContextParam<?>, Object> params) {
        this.params = ImmutableMap.copyOf(params);
    }

    public <T> T getParamOrThrow(SpellCastContextParam<T> param) {
        return (T) Objects.requireNonNull(params.get(param), "param not found!");
    }

    public LivingEntity getCaster() {
        return getParamOrThrow(SpellCastContextParams.CASTER);
    }

    public <T> T getParamOrNull(SpellCastContextParam<T> param) {
        return (T) params.get(param);
    }

    public static class Builder {
        private final Map<SpellCastContextParam<?>, Object> params = new HashMap<>();

        public <T> void addParam(SpellCastContextParam<T> param, T value) {
            params.put(param, value);
        }

        public SpellCastContext build() {
            return new SpellCastContext(params);
        }
    }
}
