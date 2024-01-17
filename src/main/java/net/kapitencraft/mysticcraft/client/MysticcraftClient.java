package net.kapitencraft.mysticcraft.client;

import net.kapitencraft.mysticcraft.client.particle.animation.ParticleAnimationAcceptor;

public class MysticcraftClient {
    private static final MysticcraftClient instance = new MysticcraftClient();

    public static MysticcraftClient getInstance() {
        return instance;
    }

    public final ParticleAnimationAcceptor acceptor = new ParticleAnimationAcceptor();
}
