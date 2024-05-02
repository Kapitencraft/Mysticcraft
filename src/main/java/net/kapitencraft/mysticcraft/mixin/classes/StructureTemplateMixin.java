package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.block.special.StructureExecutioner;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Map;

@Mixin(StructureTemplate.class)
public abstract class StructureTemplateMixin {

    @Unique
    private final Map<BlockPos, BlockState> structureExecutioners = new HashMap<>();

    @Redirect(method = "placeInWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/ServerLevelAccessor;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z"))
    private boolean proxy(ServerLevelAccessor instance, BlockPos pos, BlockState state, int i) {
        if (state.getBlock() instanceof StructureExecutioner executioner) {
            if (executioner.getType() == StructureExecutioner.Type.DIRECT) {
                executioner.execute(state, pos, instance.getLevel());
            } else {
                structureExecutioners.put(pos, state);
            }
        }
        return instance.setBlock(pos, state, i);
    }

    @Inject(method = "placeInWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructurePlaceSettings;isIgnoreEntities()Z"))
    private void executeExecutioners(ServerLevelAccessor accessor, BlockPos blockPos, BlockPos blockPos1, StructurePlaceSettings settings, RandomSource source, int i, CallbackInfoReturnable<Boolean> cir) {
        structureExecutioners.forEach((pos, state) -> {
            StructureExecutioner executioner = (StructureExecutioner) state.getBlock();
            executioner.execute(state, pos, accessor.getLevel());
        });
        structureExecutioners.clear();
    }
}
