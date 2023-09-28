package net.kapitencraft.mysticcraft.mixin.classes;

import net.minecraft.client.GuiMessageTag;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MessageSignature;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(ChatComponent.class)
public class ChatComponentMixin {


    @Inject(method = "addMessage", at = @At("HEAD"))
    public void addMessage(Component component, @Nullable MessageSignature signature, int time, @Nullable GuiMessageTag messageTag, boolean show, CallbackInfo info) {
        if (show && component instanceof MutableComponent mutable) {
            mutable.withStyle(mutable.getStyle().withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, mutable.getString())));
        }
    }
}