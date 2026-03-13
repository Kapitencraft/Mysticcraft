package net.kapitencraft.mysticcraft.item.bonus;

import net.kapitencraft.kap_lib.bonus.Bonus;
import net.kapitencraft.kap_lib.core.helpers.IOHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import org.jetbrains.annotations.NotNull;

import java.util.function.Predicate;

public abstract class StackingBonus<T extends StackingBonus<T>> implements Bonus<T> {
    private final int duration;
    private final String typeName;
    private final Predicate<DamageSource> filter;

    public StackingBonus(int duration, String typeName, Predicate<DamageSource> filter) {
        this.duration = duration;
        this.typeName = typeName;
        this.filter = filter;
    }

    @Override
    public void onEntityHurt(LivingEntity attacked, LivingEntity attacker, DamageContainer container) {
        if (filter.test(container.getSource())) {
            CompoundTag tag = IOHelper.getOrCreateCompound(attacked.getPersistentData(), typeName);
            IOHelper.increaseIntegerTagValue(tag, "stack", 1);
            tag.putInt("duration", duration);
        }
    }

    @Override
    public void onTick(int tickCount, @NotNull LivingEntity entity) {
        CompoundTag tag = entity.getPersistentData().getCompound(typeName);
        if (!tag.isEmpty()) {
            int i = IOHelper.increaseIntOnlyAbove0(tag, "duration", -1);
            if (i == 0) {
                IOHelper.increaseIntOnlyAbove0(tag, "stack", -1);
                tag.putInt("duration", duration);
            }
        }
    }

    protected int getStack(LivingEntity living) {
        CompoundTag tag = living.getPersistentData().getCompound(typeName);
        if (!tag.isEmpty()) return tag.getInt("stack");
        return 0;
    }
}