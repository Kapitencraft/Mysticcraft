package net.kapitencraft.mysticcraft;

import com.mojang.logging.LogUtils;
import net.kapitencraft.mysticcraft.gui.gemstone_grinder.GemstoneGrinderScreen;
import net.kapitencraft.mysticcraft.init.*;
import net.kapitencraft.mysticcraft.misc.ModItemProperties;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MysticcraftMod.MOD_ID)
public class MysticcraftMod {
    public static final String MOD_ID = "mysticcraft";
    public static final float TIME_PER_TICK = 0.05f;
    private static int messageID = 0;
    public static final Logger LOGGER = LogUtils.getLogger();
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel PACKET_HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MOD_ID, MOD_ID), () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static final UUID[] ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT = {UUID.fromString("7be43dcd-084a-49d6-91d7-280777b49926"), UUID.fromString("a15eaf40-a426-4084-9807-bdb93977db92"), UUID.fromString("e3e176da-2089-43d2-bdc9-1cde813b5424"), UUID.fromString("b41c0858-f948-4dd5-ac3b-90da936b7e95"), UUID.fromString("aab76209-c6f6-41c4-a5f0-b495feeefe01"), UUID.fromString("d5613269-c9d5-4884-a340-d32e97c7b7a3")};
    public static final UUID[] ITEM_ATTRIBUTE_MODIFIER_MUL_FOR_SLOT = {UUID.fromString("47bcd762-0959-4385-aee8-fa5d6f92c31f"), UUID.fromString("ef879c0f-4e70-4213-94b9-ab7103f521a6"), UUID.fromString("0128c1ad-a1a4-49bc-943f-c080d1adad73"), UUID.fromString("f5849fb9-28a6-4c6c-b591-3b1b2dece46b"), UUID.fromString("d7a8f3b9-8ee1-45fd-8f97-deaa4a615bf0"), UUID.fromString("520d4928-7ea8-4e4d-bda5-d89a898d79c2")};

    public MysticcraftMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.REGISTRY.register(modEventBus);
        ModEnchantments.REGISTRY.register(modEventBus);
        ModAttributes.REGISTRY.register(modEventBus);
        ModMobEffects.REGISTRY.register(modEventBus);
        ModBlocks.REGISTRY.register(modEventBus);
        ModBlockEntities.REGISTRY.register(modEventBus);
        ModMenuTypes.REGISTRY.register(modEventBus);


        MinecraftForge.EVENT_BUS.register(this);
        GeckoLib.initialize();
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ModItemProperties.addCustomItemProperties();
            event.enqueueWork(() -> {
                MysticcraftMod.LOGGER.info("Loading GUI for Gem Grinder");
                MenuScreens.register(ModMenuTypes.GEM_GRINDER_MENU.get(), GemstoneGrinderScreen::new);
            });
        }
    }

    public static <T> void addNetworkMessage(Class<T> messageType, BiConsumer<T, FriendlyByteBuf> encoder, Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer) {
        PACKET_HANDLER.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
        messageID++;
    }
}
