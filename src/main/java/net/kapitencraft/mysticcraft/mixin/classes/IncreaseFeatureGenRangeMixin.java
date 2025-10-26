package net.kapitencraft.mysticcraft.mixin.classes;

import net.minecraft.world.level.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ChunkGenerator.class)
public class IncreaseFeatureGenRangeMixin {

    @ModifyConstant(method = "applyBiomeDecoration", constant = @Constant(intValue = 1))
    private static int modifyChunkRange(int i) {
        return 3;
    }
}
