package net.kapitencraft.mysticcraft.mixin.classes;

import net.minecraft.SharedConstants;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SharedConstants.class)
public class SharedConstantsMixin {

    //@ModifyConstant(method = "isAllowedChatCharacter")
    private static int modify(int in) {
        if (in == 167) return -1;
        return in;
    }
}
