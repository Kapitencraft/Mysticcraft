package net.kapitencraft.mysticcraft.mixin.classes.client;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.kapitencraft.mysticcraft.mixin.duck.IParentKeeper;
import net.minecraft.client.renderer.block.model.BlockElement;
import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Map;

@Mixin(BlockModel.Deserializer.class)
public class BlockModelDeserializerMixin {
    private boolean keepParentElements;

    @Inject(method = "getElements", at = @At("HEAD"))
    private void checkKeepParent(JsonDeserializationContext pContext, JsonObject pJson, CallbackInfoReturnable<List<BlockElement>> cir) {
        this.keepParentElements = pJson.has("keep_parent_elements") && GsonHelper.getAsBoolean(pJson, "keep_parent_elements");
    }

    @Redirect(method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/client/renderer/block/model/BlockModel;", at = @At(value = "NEW", target = "(Lnet/minecraft/resources/ResourceLocation;Ljava/util/List;Ljava/util/Map;ZLnet/minecraft/client/renderer/block/model/BlockModel$GuiLight;Lnet/minecraft/client/renderer/block/model/ItemTransforms;Ljava/util/List;)Lnet/minecraft/client/renderer/block/model/BlockModel;"))
    private BlockModel pushKeep(ResourceLocation pParentLocation, List pElements, Map pTextureMap, boolean pHasAmbientOcclusion, BlockModel.GuiLight pGuiLight, ItemTransforms pTransforms, List pOverrides) {
        BlockModel model = new BlockModel(pParentLocation, pElements, pTextureMap, pHasAmbientOcclusion, pGuiLight, pTransforms, pOverrides);
        ((IParentKeeper) model).setKeep(keepParentElements);
        return model;
    }
}