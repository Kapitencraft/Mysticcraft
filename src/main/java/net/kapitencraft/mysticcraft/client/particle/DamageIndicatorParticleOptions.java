package net.kapitencraft.mysticcraft.client.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.init.ModParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

public class DamageIndicatorParticleOptions extends ParticleType<DamageIndicatorParticleOptions> implements ParticleOptions {
    private static final Codec<DamageIndicatorParticleOptions> CODEC = RecordCodecBuilder.create(optionsInstance ->
            optionsInstance.group(
                    Codec.INT.fieldOf("damageType")
                            .forGetter(DamageIndicatorParticleOptions::getDamageType),
                    Codec.FLOAT.fieldOf("damage")
                            .forGetter(DamageIndicatorParticleOptions::getDamage)
            ).apply(optionsInstance, DamageIndicatorParticleOptions::new));
    private final int damageType;
    private final float damage;

    public DamageIndicatorParticleOptions(int damageType, float damage) {
        super(true, new Deserializer());
        this.damageType = damageType;
        this.damage = damage;
    }

    public int getDamageType() {
        return damageType;
    }

    public float getDamage() {
        return damage;
    }

    @Override
    public @NotNull ParticleType<?> getType() {
        return ModParticleTypes.DAMAGE_INDICATOR.get();
    }

    @Override
    public void writeToNetwork(@NotNull FriendlyByteBuf buf) {
        buf.writeInt(damageType);
    }

    @Override
    public @NotNull String writeToString() {
        return String.format("%s %d", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), damageType);
    }

    @Override
    public @NotNull Codec<DamageIndicatorParticleOptions> codec() {
        return CODEC;
    }

    public static class Deserializer implements ParticleOptions.Deserializer<DamageIndicatorParticleOptions> {

        @Override
        public @NotNull DamageIndicatorParticleOptions fromCommand(@NotNull ParticleType<DamageIndicatorParticleOptions> p_123733_, @NotNull StringReader reader) throws CommandSyntaxException {
            int damageType = reader.readInt();
            reader.expect(' ');
            float damage = reader.readFloat();
            return new DamageIndicatorParticleOptions(damageType, damage);
        }

        @Override
        public @NotNull DamageIndicatorParticleOptions fromNetwork(@NotNull ParticleType<DamageIndicatorParticleOptions> p_123735_, @NotNull FriendlyByteBuf buf) {
            int damageType = buf.readInt();
            float damage = buf.readFloat();
            return new DamageIndicatorParticleOptions(damageType, damage);
        }
    }
}
