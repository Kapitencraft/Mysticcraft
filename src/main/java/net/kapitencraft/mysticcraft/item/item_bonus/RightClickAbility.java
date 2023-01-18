package net.kapitencraft.mysticcraft.item.item_bonus;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public abstract class RightClickAbility {

    public abstract void onExecuted(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity user);
}
