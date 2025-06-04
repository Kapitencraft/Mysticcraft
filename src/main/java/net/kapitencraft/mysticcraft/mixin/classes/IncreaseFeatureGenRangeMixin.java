package net.kapitencraft.mysticcraft.mixin.classes;

import net.minecraft.world.level.chunk.ChunkStatus;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(ChunkStatus.class)
public class IncreaseFeatureGenRangeMixin {

    @ModifyConstant(method = "lambda$static$11", remap = false)
    private static int modifyChunkRange(int i) {
        if (i == 1) {
            return 3;
        }
        return i;
    }
}
