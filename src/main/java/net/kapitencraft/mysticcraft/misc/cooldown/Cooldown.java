package net.kapitencraft.mysticcraft.misc.cooldown;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class Cooldown {

    private final CompoundPath path;
    private final int defaultTime;
    private final Consumer<LivingEntity> toDo;

    public Cooldown(CompoundPath path, int defaultTime, Consumer<LivingEntity> toDo) {
        this.path = path;
        this.defaultTime = defaultTime;
        this.toDo = toDo;
        Cooldowns.addCooldown(this);
    }

    public void applyCooldown(LivingEntity entity, boolean reduceWithTime) {
        CompoundTag tag = getTag(entity);
        if (tag != null) {
            int mul = reduceWithTime ? (int) (entity.getAttributeValue(ModAttributes.COOLDOWN_REDUCTION.get())) : 0;
            tag.putInt(path.getPath(), defaultTime * (1 - mul / 100));
        }
    }

    public void onDone(LivingEntity living) {
        toDo.accept(living);
    }

    public CompoundPath getPath() {
        return path;
    }

    public @Nullable CompoundTag getTag(LivingEntity entity) {
        return path.getTag(entity);
    }
}
