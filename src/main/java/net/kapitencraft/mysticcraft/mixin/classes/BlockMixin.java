package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.block.ModBlockProperties;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.StateHolder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Function;

@Mixin(Block.class)
public class BlockMixin {

    @Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/StateDefinition$Builder;create(Ljava/util/function/Function;Lnet/minecraft/world/level/block/state/StateDefinition$Factory;)Lnet/minecraft/world/level/block/state/StateDefinition;"))
    private <O extends Block, S extends StateHolder<O, S>> StateDefinition<O, S> addPlayerPlacedState(StateDefinition.Builder<O, S> instance, Function<O, S> func, StateDefinition.Factory<O, S> factory) {
        //instance.add(BlockStates.PLAYER_PLACED);
        return instance.create(func, factory);
    }

    /**
     * required since otherwise game's going to crash. yippie!
     * <br>it also makes sure that the default state is <i>always</i> not player placed
     * <br>that's also not all you need :skull:
     */
    //@ModifyVariable(method = "registerDefaultState", at = @At("HEAD"), argsOnly = true)
    private BlockState modifyState(BlockState state) {
        return state.setValue(ModBlockProperties.PLAYER_PLACED, false);
    }
}
