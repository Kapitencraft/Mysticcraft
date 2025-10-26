package net.kapitencraft.mysticcraft.inst;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.worldgen.gemstone.GemstoneDecorator;
import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

import java.io.File;

@EventBusSubscriber
public class MysticcraftServer {
    private final MinecraftServer server;
    private static MysticcraftServer INSTANCE;

    @SubscribeEvent
    public static void startInstance(ServerAboutToStartEvent event) {
        MysticcraftMod.LOGGER.info("starting Mysticcraft-Server");
        INSTANCE = new MysticcraftServer(event.getServer());
    }

    public MysticcraftServer(MinecraftServer server) {
        this.server = server;
        this.serverFiles = new File(this.server.getServerDirectory().toFile(), MysticcraftMod.MOD_ID);
    }

    public static MysticcraftServer getInstance() {
        return INSTANCE;
    }
    public final File serverFiles;
    public final GemstoneDecorator decorator = new GemstoneDecorator();
}
