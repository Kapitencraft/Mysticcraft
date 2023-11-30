package net.kapitencraft.mysticcraft.mixin.classes;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.kapitencraft.mysticcraft.block.GemstoneBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockState.class)
public abstract class BlockStateMixin extends BlockBehaviour.BlockStateBase {
    protected BlockStateMixin(Block p_60608_, ImmutableMap<Property<?>, Comparable<?>> p_60609_, MapCodec<BlockState> p_60610_) {
        super(p_60608_, p_60609_, p_60610_);
    }

    @SuppressWarnings("ALL")
    private BlockState self() {
        return (BlockState) (Object) this;
    }

    @Inject(method = "getDestroySpeed", at = @At("HEAD"))
    public void getDestroySpeed(@NotNull BlockGetter getter, @NotNull BlockPos pos, CallbackInfoReturnable<Float> returnable) {
        if (getBlock() instanceof GemstoneBlock) {
            returnable.setReturnValue(GemstoneBlock.getType(self()).getBlockStrength());
        }
    }
}
