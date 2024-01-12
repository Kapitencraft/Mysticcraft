package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.event.ModEventFactory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {

    private ItemStack self() {
        return (ItemStack) (Object) this;
    }

    @Inject(method = "<init>*", at = @At(value = "RETURN"))
    private void load(CallbackInfo ci) {
        ModEventFactory.onLoadingItemStack(self());
    }

    @Inject(method = "save", at = @At("HEAD"))
    private void save(CompoundTag tag, CallbackInfoReturnable<CompoundTag> cir) {
        ModEventFactory.onSavingItemStack(self(), tag);
    }
}
