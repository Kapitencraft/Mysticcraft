package net.kapitencraft.mysticcraft.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.FishingHook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import javax.annotation.Nullable;

@Mixin(FishingHook.class)
public interface FishingHookMixin {

    @Invoker
    void callCheckCollision();

    @Invoker
    void callCatchingFish(BlockPos pos);

    @Accessor
    void setLife(int live);

    @Accessor
    int getNibble();

    @Accessor
    int getLuck();

    @Accessor
    boolean getBiting();

    @Accessor
    @Nullable
    Entity getHookedIn();

    @Accessor
    void setOpenWater(boolean openWater);

    @Accessor
    int getTimeUntilHooked();

    @Invoker
    void callSetHookedEntity(@Nullable Entity entity);

    @Accessor
    int getLife();

    @Accessor
    RandomSource getSyncronizedRandom();
}
