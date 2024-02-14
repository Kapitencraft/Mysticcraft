package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.mysticcraft.event.ModEventFactory;
import net.kapitencraft.mysticcraft.helpers.IOHelper;
import net.kapitencraft.mysticcraft.requirements.Requirement;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {

    @Override
    public boolean equals(Object obj) {
        return obj == this || obj instanceof ItemStack stack && ItemStack.isSameItemSameTags(self(), stack);
    }

    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    private void inj0(Level level, Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir) {
        if (Requirement.doesntMeetRequirements(player, self())) cir.setReturnValue(InteractionResultHolder.fail(self()));
    }

    @Inject(method = "tagMatches", at = @At("HEAD"), cancellable = true)
    private static void secureTagsEmpty(ItemStack stack, ItemStack stack1, CallbackInfoReturnable<Boolean> cir) {
        if (IOHelper.isTagEmpty(stack.getTag()) && IOHelper.isTagEmpty(stack1.getTag())) cir.setReturnValue(stack.areCapsCompatible(stack1));
    }


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
