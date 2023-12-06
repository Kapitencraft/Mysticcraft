package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.block.GemstoneBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateMixin {

    @SuppressWarnings("ALL")
    private BlockBehaviour.BlockStateBase self() {
        return (BlockState) (Object) this;
    }


    @Inject(method = "getDestroySpeed", at = @At("HEAD"), cancellable = true)
    public void getDestroySpeed(@NotNull BlockGetter getter, @NotNull BlockPos pos, CallbackInfoReturnable<Float> returnable) {
        if (self().getBlock() instanceof GemstoneBlock) {
            returnable.setReturnValue(GemstoneBlock.getType(asState()).getBlockStrength());
        }
    }

    @Shadow
    protected abstract BlockState asState();
}
