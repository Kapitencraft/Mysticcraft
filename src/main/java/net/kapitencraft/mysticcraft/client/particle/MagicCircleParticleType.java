package net.kapitencraft.mysticcraft.client.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

public class MagicCircleParticleType extends ParticleType<MagicCircleParticleType> implements ParticleOptions {
    private final int living;
    public MagicCircleParticleType(int living) {
        super(false, DESERIALIZER);
        this.living = living;
    }

    public int getLiving() {
        return living;
    }

    public static final Deserializer<MagicCircleParticleType> DESERIALIZER = new ParticleOptions.Deserializer<>() {

        @Override
        public @NotNull MagicCircleParticleType fromCommand(@NotNull ParticleType<MagicCircleParticleType> type, @NotNull StringReader reader) throws CommandSyntaxException {
            return new MagicCircleParticleType(0);
        }

        @Override
        public @NotNull MagicCircleParticleType fromNetwork(@NotNull ParticleType<MagicCircleParticleType> p_123735_, @NotNull FriendlyByteBuf byteBuf) {
            return new MagicCircleParticleType(byteBuf.readInt());
        }
    };

    @Override
    public @NotNull MagicCircleParticleType getType() {
        return this;
    }

    @Override
    public void writeToNetwork(@NotNull FriendlyByteBuf byteBuf) {
        byteBuf.writeInt(living);
    }

    @Override
    public @NotNull String writeToString() {
        return "";
    }

    @Override
    public @NotNull Codec<MagicCircleParticleType> codec() {
        return Codec.unit(this::getType);
    }
}
