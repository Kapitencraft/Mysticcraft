package net.kapitencraft.mysticcraft.item.item_bonus.fullset;

import net.kapitencraft.mysticcraft.item.item_bonus.FullSetBonus;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class StackingFullSetBonus extends FullSetBonus {
    protected final String cooldownId;
    protected final int cooldownTime;

    protected StackingFullSetBonus(String name, int cooldownTime) {
        super(name);
        this.cooldownTime = cooldownTime;
        cooldownId = this.getName() + "Cooldown";
    }

    protected int getStackCount(Entity user) {
        return user.getPersistentData().getInt(this.getName());
    }

    @Override
    public void onTick(Level level, @NotNull LivingEntity entity) {
        CompoundTag data = entity.getPersistentData();
        int cooldown = data.getInt(cooldownId);
        int stackCount = getStackCount(entity);
        if (stackCount > 0 && cooldown-- <= 0) {
            stackCount--;
            cooldown = cooldownTime;
        }
        data.putInt(cooldownId, cooldown);
        data.putInt(getName(), stackCount);
    }
}
