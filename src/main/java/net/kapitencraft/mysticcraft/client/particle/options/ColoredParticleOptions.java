package net.kapitencraft.mysticcraft.client.particle.options;

import net.kapitencraft.mysticcraft.client.render.ColorAnimator;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

public abstract class ColoredParticleOptions<T extends ColoredParticleOptions<T>> extends ModParticleOptions<T> {
    protected final ColorAnimator animator;
    public ColoredParticleOptions(boolean p_123740_, Deserializer p_123741_, ColorAnimator animator) {
        super(p_123740_, p_123741_);
        this.animator = animator;
    }

    @Override
    public void writeToNetwork(@NotNull FriendlyByteBuf buf) {
        animator.writeToBytes(buf);
    }

    @Override
    public @NotNull String writeToString() {
        return String.format("%s", super.writeToString());
    }

}
