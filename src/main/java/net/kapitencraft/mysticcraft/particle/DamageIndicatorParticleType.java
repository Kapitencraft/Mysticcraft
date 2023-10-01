package net.kapitencraft.mysticcraft.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

public class DamageIndicatorParticleType extends ParticleType<DamageIndicatorParticleType> implements ParticleOptions {
    private final float amount;
    private final String type;
    private final Codec<DamageIndicatorParticleType> codec = Codec.unit(this::getType);
    public DamageIndicatorParticleType(double amount, String type) {
        super(true, DESERIALIZER);
        this.amount = (float) amount;
        this.type = type;
    }

    @Override
    public @NotNull DamageIndicatorParticleType getType() {
        return this;
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeUtf(this.type);
        buf.writeFloat(this.amount);
    }

    public float getAmount() {
        return this.amount;
    }

    public String getDamageType() {
        return this.type;
    }

    @Override
    public @NotNull String writeToString() {
        String name = BuiltInRegistries.PARTICLE_TYPE.getKey(this).toString();
        return name + ", amount: " + this.amount + ", type: " + this.type;
    }

    @Override
    public Codec<DamageIndicatorParticleType> codec() {
        return this.codec;
    }

    private static final ParticleOptions.Deserializer<DamageIndicatorParticleType> DESERIALIZER = new ParticleOptions.Deserializer<>() {
        public @NotNull DamageIndicatorParticleType fromCommand(@NotNull ParticleType<DamageIndicatorParticleType> type, StringReader reader) {
            return (DamageIndicatorParticleType) type;
        }

        public @NotNull DamageIndicatorParticleType fromNetwork(@NotNull ParticleType<DamageIndicatorParticleType> type, FriendlyByteBuf buf) {
            return new DamageIndicatorParticleType(buf.readFloat(), buf.readUtf());
        }
    };
}
