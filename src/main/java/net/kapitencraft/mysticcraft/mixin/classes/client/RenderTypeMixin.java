package net.kapitencraft.mysticcraft.mixin.classes.client;

import com.google.common.collect.ImmutableList;
import net.kapitencraft.mysticcraft.client.shader.ModRenderTypes;
import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderType.class)
public class RenderTypeMixin {
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableList;of(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Lcom/google/common/collect/ImmutableList;"), remap = false)
    private static ImmutableList<Object> addChunkLayers(Object e1, Object e2, Object e3, Object e4, Object e5) {
        return ImmutableList.of(
                e1,
                e2,
                e3,
                e4,
                e5,
                ModRenderTypes.CHROMATIC_CUTOUT
        );
    }
}