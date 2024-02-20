package net.kapitencraft.mysticcraft.client;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.client.particle.animation.ParticleAnimationAcceptor;
import net.kapitencraft.mysticcraft.client.render.RenderController;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.io.File;

@OnlyIn(Dist.CLIENT)
public class MysticcraftClient {
    private static MysticcraftClient instance;

    public final RenderController renderController = new RenderController(); // RenderController.load();
    public static final File CLIENT_FILES = new File(Minecraft.getInstance().gameDirectory, MysticcraftMod.MOD_ID);

    public static MysticcraftClient getInstance() {
        if (instance == null) instance = new MysticcraftClient();
        return instance;
    }

    public void save() {
        renderController.save();
    }

    public final ParticleAnimationAcceptor acceptor = new ParticleAnimationAcceptor();
}
