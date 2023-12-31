package net.kapitencraft.mysticcraft.client;

import net.kapitencraft.mysticcraft.client.particle.animation.ParticleAnimationAcceptor;

public class MysticcraftClientInstance {
    private static final MysticcraftClientInstance instance = new MysticcraftClientInstance();

    public static MysticcraftClientInstance getInstance() {
        return instance;
    }

    public final ParticleAnimationAcceptor acceptor = new ParticleAnimationAcceptor();
}
