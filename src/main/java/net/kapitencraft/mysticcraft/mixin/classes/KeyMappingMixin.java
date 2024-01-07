package net.kapitencraft.mysticcraft.mixin.classes;


import net.kapitencraft.mysticcraft.init.ModMobEffects;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.client.extensions.IForgeKeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyMapping.class)
public abstract class KeyMappingMixin implements IForgeKeyMapping {

    @Shadow private int clickCount;

    private static boolean isStunned() {
        LocalPlayer local = Minecraft.getInstance().player;
        return local != null && local.hasEffect(ModMobEffects.STUN.get());
    }

    /**
     * @reason stun effect
     * @author Kapitencraft
     */
    @Inject(method = "consumeClick", at = @At("HEAD"), cancellable = true)
    public void consumeClick(CallbackInfoReturnable<Boolean> cir) {
        if (isStunned()) {
            this.clickCount = 0;
            cir.setReturnValue(false);
        }
    }


    /**
     * @reason stun effect
     * @author Kapitencraft
     */
    @Inject(method = "isDown", at = @At("HEAD"), cancellable = true)
    public void isDown(CallbackInfoReturnable<Boolean> cir) {
        if (isStunned()) cir.setReturnValue(false);
    }
}