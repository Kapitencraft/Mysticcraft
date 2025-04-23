package net.kapitencraft.mysticcraft.client.particle.options;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.kap_lib.util.Color;
import net.kapitencraft.mysticcraft.registry.ModParticleTypes;
import net.minecraft.core.particles.DustParticleOptionsBase;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class CircleParticleOptions extends SimpleColoredParticleOptions<CircleParticleOptions> {
    private static final Codec<CircleParticleOptions> CODEC = RecordCodecBuilder.create(optionsInstance ->
            optionsInstance.group(
                Color.CODEC.fieldOf("color")
                    .forGetter(CircleParticleOptions::getColor),
                Codec.DOUBLE.fieldOf("size")
                    .forGetter(CircleParticleOptions::getSize),
                Codec.DOUBLE.fieldOf("expandSpeed")
                    .forGetter(CircleParticleOptions::getExpandSpeed)
            ).apply(optionsInstance, CircleParticleOptions::new));
    private final double size;
    private final double expandSpeed;

    public CircleParticleOptions(Color color, double size, double expandSpeed) {
        super(true, new Deserializer(), color);
        this.size = size;
        this.expandSpeed = expandSpeed;
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
        color.write(buf);
    }

    @Override
    public @NotNull String writeToString() {
        return String.format("%s %.2f %.2f %.2f %.2f %.2f", BuiltInRegistries.PARTICLE_TYPE.getKey(this.getType()), this.size, this.expandSpeed, this.color.r, this.color.g, this.color.b);
    }

    @Override
    public @NotNull Codec<CircleParticleOptions> codec() {
        return CODEC;
    }

    public static class Deserializer implements ParticleOptions.Deserializer<CircleParticleOptions> {

        @Override
        public @NotNull CircleParticleOptions fromCommand(@NotNull ParticleType<CircleParticleOptions> type, @NotNull StringReader reader) throws CommandSyntaxException {
            Vector3f vector3f = DustParticleOptionsBase.readVector3f(reader);
            Color color = new Color(vector3f.x, vector3f.y, vector3f.z, 1);
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
            Color color = Color.read(buf);
            return new CircleParticleOptions(color, size, expandSize);
        }
    }
}
