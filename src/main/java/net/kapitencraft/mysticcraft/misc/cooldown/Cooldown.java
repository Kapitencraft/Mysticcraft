package net.kapitencraft.mysticcraft.misc.cooldown;

import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class Cooldown {

    //TODO fix not working for spells
    private final CompoundPath path;
    private final int defaultTime;
    private final Consumer<Entity> toDo;

    public Cooldown(CompoundPath path, int defaultTime, Consumer<Entity> toDo) {
        this.path = path;
        this.defaultTime = defaultTime;
        this.toDo = toDo;
        Cooldowns.addCooldown(this);
    }

    public void applyCooldown(Entity entity, boolean reduceWithTime) {
        CompoundTag tag = getTag(entity);
        if (tag != null) {
            int mul = 0;
            if (reduceWithTime && entity instanceof LivingEntity living) {
                mul = (int) (living.getAttributeValue(ModAttributes.COOLDOWN_REDUCTION.get()));
            }
            tag.putInt(path.getPath(), defaultTime * (1 - mul / 100));
        }
    }

    public int getCooldownTime(Entity living) {
        CompoundTag tag = getTag(living);
        return tag == null ? 0 : tag.getInt(path.getPath());
    }

    public void onDone(Entity living) {
        toDo.accept(living);
    }

    public CompoundPath getPath() {
        return path;
    }

    public @Nullable CompoundTag getTag(Entity entity) {
        return path.getTag(entity);
    }
}
