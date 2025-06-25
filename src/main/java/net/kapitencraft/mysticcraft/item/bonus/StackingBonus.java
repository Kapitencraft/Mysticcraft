package net.kapitencraft.mysticcraft.item.bonus;

import net.kapitencraft.kap_lib.helpers.IOHelper;
import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.item.bonus.Bonus;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public abstract class StackingBonus<T extends StackingBonus<T>> implements Bonus<T> {
    private final MiscHelper.DamageType type;
    private final int duration;
    private final String typeName;

    public StackingBonus(MiscHelper.DamageType type, int duration, String typeName) {
        this.type = type;
        this.duration = duration;
        this.typeName = typeName;
    }

    @Override
    public float onEntityHurt(LivingEntity attacked, LivingEntity attacker, MiscHelper.DamageType type, float damage) {
        if (type == this.type) {
            CompoundTag tag = IOHelper.getOrCreateTag(attacked.getPersistentData(), typeName);
            IOHelper.increaseIntegerTagValue(tag, "stack", 1);
            tag.putInt("duration", duration);
        }
        return damage;
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