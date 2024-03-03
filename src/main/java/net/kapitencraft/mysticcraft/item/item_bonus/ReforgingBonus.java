package net.kapitencraft.mysticcraft.item.item_bonus;

import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class ReforgingBonus implements Bonus {
    private final String name;
    protected ReforgingBonus(String name) {
        this.name = name;
    }

    @Override
    public void onApply(LivingEntity living) {
    }

    @Override
    public void onEntityKilled(LivingEntity killed, LivingEntity user, MiscHelper.DamageType type) {
    }

    @Override
    public void onTick(Level level, @NotNull LivingEntity entity) {
    }

    @Override
    public void onRemove(LivingEntity living) {
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSuperName() {
        return "Reforge";
    }
}