package net.kapitencraft.mysticcraft.mixin.classes.client;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Screen.class)
public class ScreenMixin {

    private Screen self() {
        return (Screen) (Object) this;
    }

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void keyPressed(int key, int p_96553_, int p_96554_, CallbackInfoReturnable<Boolean> cir) {
        if (key == ' ' && self() instanceof RecipeUpdateListener listener) {
            cir.setReturnValue(listener.getRecipeBookComponent().keyPressed(key, p_96553_, p_96554_));
        }
    }
}
