package net.kapitencraft.mysticcraft.client;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.client.particle.animation.ParticleAnimationAcceptor;
import net.kapitencraft.mysticcraft.client.render.overlay.OverlayController;
import net.kapitencraft.mysticcraft.networking.ModMessages;
import net.kapitencraft.mysticcraft.networking.RequestHandler;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@OnlyIn(Dist.CLIENT)
public class MysticcraftClient {
    private static final MysticcraftClient instance = new MysticcraftClient();
    public final RequestHandler handler;

    public final OverlayController overlayController = OverlayController.load();
    private static File CLIENT_FILES;

    public static @NotNull File getBaseClientDirectory() {
        if (CLIENT_FILES == null) {
            CLIENT_FILES = new File(Minecraft.getInstance().gameDirectory, MysticcraftMod.MOD_ID);
        }
        return CLIENT_FILES;
    }

    public MysticcraftClient() {
        this.handler = new RequestHandler(ModMessages::sendToServer);
    }

    public static MysticcraftClient getInstance() {
        return instance;
    }

    public final ParticleAnimationAcceptor acceptor = new ParticleAnimationAcceptor();
}
