package net.kapitencraft.mysticcraft.cooldown;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class MappedCooldown<T> {
    private final Map<T, Cooldown> mapped = new HashMap<>();
    private final String path;
    private final Function<T, String> mapper;
    private final @Nullable Consumer<Entity> exe;

    public MappedCooldown(String path, Function<T, String> mapper, @Nullable Consumer<Entity> exe) {
        this.path = path;
        this.mapper = mapper;
        this.exe = exe;
    }

    public @Nullable Cooldown get(T t) {
        return mapped.get(t);
    }

    public Cooldown getOrCreate(T t, int time) {
        if (get(t) == null) {
            add(t, time);
        }
        return get(t);
    }

    public void add(T t, int time) {
        CompoundPath path = new CompoundPath(this.path, CompoundPath.COOLDOWN, entity -> entity instanceof LivingEntity);
        mapped.put(t, new Cooldown(new CompoundPath(mapper.apply(t), path, entity -> entity instanceof LivingEntity), time, exe == null ? entity -> {} : exe));
    }
}
