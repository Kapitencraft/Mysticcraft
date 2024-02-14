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
import java.util.List;

public class ParticleAnimationProvider implements ParticleProvider<ParticleAnimationOptions> {
    public ParticleAnimationProvider(SpriteSet ignored) {

    }

    @Nullable
    @Override
    public Particle createParticle(ParticleAnimationOptions options, @NotNull ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
        ParticleAnimationInfo info = options.info;
        ParticleOptions options1 = options.options;
        List<Particle> particles = new ArrayList<>();
        for (int i = 0; i < options.amount; i++) {
            particles.add(Minecraft.getInstance().particleEngine.createParticle(options1, x, y, z, dx, dy, dz));
        }
        ParticleAnimationController controller = new ParticleAnimationController(particles, info, options.params);
        MysticcraftClient.getInstance().acceptor.addAnimation(controller);
        return particles.get(0);
    }
}
