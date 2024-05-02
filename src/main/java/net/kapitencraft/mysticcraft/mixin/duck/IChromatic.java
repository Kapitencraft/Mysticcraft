package net.kapitencraft.mysticcraft.mixin.duck;

import net.minecraft.client.renderer.RenderType;

public interface IChromatic {

    RenderType getChromaType();

    void setChromaType(RenderType chromaType);
}
