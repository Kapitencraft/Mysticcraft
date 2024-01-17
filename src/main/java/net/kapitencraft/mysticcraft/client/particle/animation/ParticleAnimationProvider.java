package net.kapitencraft.mysticcraft.client.particle.animation;

import net.kapitencraft.mysticcraft.client.MysticcraftClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Stream;

public class ParticleAnimationProvider implements ParticleProvider<ParticleAnimationOptions> {
    public ParticleAnimationProvider(SpriteSet ignored) {

    }

    @Nullable
    @Override
    public Particle createParticle(ParticleAnimationOptions options, @NotNull ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
        ParticleAnimationInfo info = options.info;
        ParticleOptions options1 = options.options;
        Particle particle = Minecraft.getInstance().particleEngine.createParticle(options1, x, y, z, dx, dy, dz);
        ParticleAnimationController controller = new ParticleAnimationController(new ArrayList<>(Stream.of(particle).filter(Objects::nonNull).toList()), info, options.params);
        MysticcraftClient.getInstance().acceptor.addAnimation(controller);
        return particle;
    }
}
