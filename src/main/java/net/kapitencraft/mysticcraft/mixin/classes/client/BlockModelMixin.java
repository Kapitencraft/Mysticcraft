package net.kapitencraft.mysticcraft.mixin.classes.client;

import net.kapitencraft.mysticcraft.mixin.duck.IParentKeeper;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockModel;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@Mixin(BlockModel.class)
public class BlockModelMixin implements IParentKeeper {
    @Shadow @Nullable public BlockModel parent;
    @Shadow @Final private List<BlockElement> elements;
    public boolean keepParentElements;

    @Override
    public boolean keeps() {
        return keepParentElements;
    }

    @Override
    public void setKeep(boolean keep) {
        keepParentElements = keep;
    }

    /**
     * @author Kapitencraft
     * @reason adding parent keeping
     */
    @Inject(method = "getElements", at = @At("HEAD"), cancellable = true)
    public void getElements(CallbackInfoReturnable<List<BlockElement>> cir) {
        if (keeps()) {
            if (parent == null) {
                throw new IllegalStateException("detected parent keeping without parent!");
            }
            ArrayList<BlockElement> list = new ArrayList<>(this.elements);
            list.addAll(this.parent.getElements());
            cir.setReturnValue(list);
        }
    }

}