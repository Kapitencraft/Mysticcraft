package net.kapitencraft.mysticcraft.client.particle.options;

import net.kapitencraft.kap_lib.util.Color;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

public abstract class ColoredParticleOptions<T extends ColoredParticleOptions<T>> extends ModParticleOptions<T> {
    protected final Color color;
    public ColoredParticleOptions(boolean p_123740_, Deserializer p_123741_, Color color) {
        super(p_123740_, p_123741_);
        this.color = color;
    }

    @Override
    public void writeToNetwork(@NotNull FriendlyByteBuf buf) {
        color.write(buf);
    }

    @Override
    public @NotNull String writeToString() {
        return String.format("%s-color:%s", super.writeToString(), this.color);
    }

}
