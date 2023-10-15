package net.kapitencraft.mysticcraft.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TextureSheetParticle;

public abstract class ColoredParticle extends TextureSheetParticle {

    protected ColoredParticle(ClientLevel p_108323_, double p_108324_, double p_108325_, double p_108326_) {
        super(p_108323_, p_108324_, p_108325_, p_108326_);
    }

    protected ColoredParticle(ClientLevel p_108328_, double p_108329_, double p_108330_, double p_108331_, double p_108332_, double p_108333_, double p_108334_) {
        super(p_108328_, p_108329_, p_108330_, p_108331_, p_108332_, p_108333_, p_108334_);
    }

    public enum ColorType {
        ORANGE(1),
        DARK_BLUE(2),
        DARK_GREEN(3),
        LIGHT_BLUE(4),
        LIGHT_GREEN(5),
        RED(6);


        private final int texturePos;

        ColorType(int texturePos) {
            this.texturePos = texturePos;
        }

        public int getTexturePos() {
            return texturePos;
        }
    }
}
