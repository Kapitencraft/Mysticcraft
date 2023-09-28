package net.kapitencraft.mysticcraft.misc.cooldown;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class CompoundPath {
    public static final CompoundPath ROOT = new CompoundPath("", null, entity -> true);

    private final String path;
    private final CompoundPath parent;
    private final Predicate<Entity> useAbles;

    public CompoundPath(String path, CompoundPath parent, Predicate<Entity> useAbles) {
        this.path = path;
        this.parent = parent;
        this.useAbles = useAbles;
    }

    public CompoundPath getParent() {
        return parent;
    }

    public String getPath() {
        return path;
    }

    public @Nullable CompoundTag getTag(Entity entity) {
        if (this == ROOT) return entity.getPersistentData();
        if (applicable(entity)) {
            CompoundTag parentPath = parent.getTag(entity);
            if (parentPath != null) {
                return parentPath.getCompound(this.path);
            } else {
                return null;
            }
        } else {
            MysticcraftMod.sendWarn("unable to read compound '" + path + "': wrong entity");
        }
        return null;
    }

    public boolean applicable(Entity entity) {
        return useAbles.test(entity);
    }

    public Builder builder(String path) {
        return new Builder(path);
    }

    public static class Builder {
        private final String path;
        private Predicate<Entity> predicate;
        private CompoundPath parent;

        public Builder(String path) {
            this.path = path;
        }

        public Builder withUseAbles(Predicate<Entity> predicate) {
            this.predicate = predicate;
            return this;
        }

        public Builder withParent(CompoundPath parent) {
            this.parent = parent;
            return this;
        }

        public Builder withParent(String id, Consumer<Builder> consumer) {
            Builder builder = new Builder(id);
            consumer.accept(builder);
            this.parent = builder.build();
            return this;
        }

        public CompoundPath build() {
            return new CompoundPath(path, parent == null ? ROOT : parent, predicate == null ? entity -> true : predicate);
        }
    }
}
