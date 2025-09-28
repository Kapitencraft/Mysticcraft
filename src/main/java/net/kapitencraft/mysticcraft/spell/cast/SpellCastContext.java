package net.kapitencraft.mysticcraft.spell.cast;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SpellCastContext {
    private final Map<SpellCastContextParam<?>, Object> params;
    private final Level world;
    private final int level;

    private SpellCastContext(Map<SpellCastContextParam<?>, Object> params, Level world, int level) {
        this.params = ImmutableMap.copyOf(params);
        this.world = world;
        this.level = level;
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

    public Level getWorld() {
        return world;
    }

    public int getLevel() {
        return level;
    }

    public static class Builder {
        private final Map<SpellCastContextParam<?>, Object> params = new HashMap<>();

        public <T> void addParam(SpellCastContextParam<T> param, T value) {
            params.put(param, value);
        }

        public SpellCastContext build(Level world, int level) {
            return new SpellCastContext(params, world, level);
        }
    }
}
