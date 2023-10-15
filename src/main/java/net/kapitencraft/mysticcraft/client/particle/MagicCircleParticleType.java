package net.kapitencraft.mysticcraft.client.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MagicCircleParticleType extends ParticleType<MagicCircleParticleType> implements ParticleOptions {
    private final @Nullable LivingEntity living;
    public MagicCircleParticleType(@Nullable LivingEntity living) {
        super(false, DESERIALIZER);
        this.living = living;
    }

    public @Nullable LivingEntity getLiving() {
        return living;
    }

    public static final Deserializer<MagicCircleParticleType> DESERIALIZER = new ParticleOptions.Deserializer<>() {

        @Override
        public @NotNull MagicCircleParticleType fromCommand(@NotNull ParticleType<MagicCircleParticleType> type, @NotNull StringReader reader) throws CommandSyntaxException {
            return new MagicCircleParticleType(null);
        }

        @Override
        public @NotNull MagicCircleParticleType fromNetwork(@NotNull ParticleType<MagicCircleParticleType> p_123735_, @NotNull FriendlyByteBuf byteBuf) {
            return new MagicCircleParticleType(null);
        }
    };

    @Override
    public @NotNull MagicCircleParticleType getType() {
        return this;
    }

    @Override
    public void writeToNetwork(@NotNull FriendlyByteBuf byteBuf) {

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
