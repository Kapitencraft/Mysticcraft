package net.kapitencraft.mysticcraft.cooldown;

import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.TextHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
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
            double mul = reduceWithTime && entity instanceof LivingEntity living ? living.getAttributeValue(ModAttributes.COOLDOWN_REDUCTION.get()) : 0;
            tag.putInt(path.getPath(), (int) (defaultTime * (1 - mul / 100)));
        }
    }

    public int getCooldownTime(Entity living) {
        CompoundTag tag = getTag(living);
        return tag == null ? 0 : tag.getInt(path.getPath());
    }

    public boolean isActive(Entity entity) {
        return getCooldownTime(entity) > 0;
    }

    public void onDone(Entity living) {
        toDo.accept(living);
    }

    public CompoundPath getPath() {
        return path.getParent();
    }

    public @Nullable CompoundTag getTag(Entity entity) {
        return path.getTag(entity);
    }

    public Component createDisplay(Entity entity) {
        int cooldownTicks = getCooldownTime(entity);
        int defaultTime = entity instanceof LivingEntity living ? MathHelper.cooldown(living, this.defaultTime) : this.defaultTime;
        return Component.literal("Cooldown: " + (cooldownTicks > 0 ? TextHelper.wrapInRed("ACTIVE") + "(" + MathHelper.round(cooldownTicks / 20., 1) + "s)" : "§aINACTIVE§r, " + MathHelper.round(defaultTime / 20., 1) + "s")).withStyle(ChatFormatting.DARK_GRAY);
    }
}
