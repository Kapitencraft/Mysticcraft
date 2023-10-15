package net.kapitencraft.mysticcraft.client.particle;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.helpers.NetworkingHelper;
import net.kapitencraft.mysticcraft.init.ModParticleTypes;
import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class CircleParticleOptions extends ParticleType<CircleParticleOptions> implements ParticleOptions {
    private static final Codec<CircleParticleOptions> CODEC = RecordCodecBuilder.create(optionsInstance ->
            optionsInstance.group(
                ExtraCodecs.VECTOR3F.fieldOf("color")
                    .forGetter(CircleParticleOptions::getColor),
                Codec.DOUBLE.fieldOf("size")
                    .forGetter(CircleParticleOptions::getSize),
                Codec.DOUBLE.fieldOf("expandSpeed")
                    .forGetter(CircleParticleOptions::getExpandSpeed)
            ).apply(optionsInstance, CircleParticleOptions::new));
    private final Vector3f color;
    private final double size;
    private final double expandSpeed;

    public CircleParticleOptions(Vector3f color, double size, double expandSpeed) {
        super(true, new Deserializer());
        this.color = color;
        this.size = size;
        this.expandSpeed = expandSpeed;
    }

    public Vector3f getColor() {
        return color;
    }

    public double getExpandSpeed() {
        return expandSpeed;
    }

    public double getSize() {
        return size;
    }

    @Override
    public @NotNull ParticleType<?> getType() {
        return ModParticleTypes.CIRCLE.get();
    }

    @Override
    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeDouble(size);
        buf.writeDouble(expandSpeed);
        NetworkingHelper.writeVector3f(buf, color);
    }

    @Override
    public @NotNull String writeToString() {
        return String.format("%s %.2f %.2f %.2f %.2f %.2f", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), this.size, this.expandSpeed, this.color.x, this.color.y, this.color.z);
    }

    @Override
    public @NotNull Codec<CircleParticleOptions> codec() {
        return CODEC;
    }

    public static class Deserializer implements ParticleOptions.Deserializer<CircleParticleOptions> {

        @Override
        public @NotNull CircleParticleOptions fromCommand(@NotNull ParticleType<CircleParticleOptions> type, @NotNull StringReader reader) throws CommandSyntaxException {
            Vector3f color = DustParticleOptionsBase.readVector3f(reader);
            reader.expect(' ');
            double size = reader.readDouble();
            reader.expect(' ');
            double expandSpeed = reader.readDouble();
            return new CircleParticleOptions(color, size, expandSpeed);
        }

        @Override
        public @NotNull CircleParticleOptions fromNetwork(@NotNull ParticleType<CircleParticleOptions> type, FriendlyByteBuf buf) {
            double size = buf.readDouble();
            double expandSize = buf.readDouble();
            Vector3f color = DustParticleOptionsBase.readVector3f(buf);
            return new CircleParticleOptions(color, size, expandSize);
        }
    }
}
