package net.kapitencraft.mysticcraft;

import com.mojang.logging.LogUtils;
import net.kapitencraft.mysticcraft.config.ClientModConfig;
import net.kapitencraft.mysticcraft.config.CommonModConfig;
import net.kapitencraft.mysticcraft.gui.gemstone_grinder.GemstoneGrinderScreen;
import net.kapitencraft.mysticcraft.gui.reforging_anvil.ReforgeAnvilScreen;
import net.kapitencraft.mysticcraft.init.ModEntityTypes;
import net.kapitencraft.mysticcraft.init.ModFluids;
import net.kapitencraft.mysticcraft.init.ModMenuTypes;
import net.kapitencraft.mysticcraft.init.ModRegistryInit;
import net.kapitencraft.mysticcraft.item.AnvilUses;
import net.kapitencraft.mysticcraft.item.data.reforging.Reforges;
import net.kapitencraft.mysticcraft.misc.ModItemProperties;
import net.kapitencraft.mysticcraft.networking.ModMessages;
import net.kapitencraft.mysticcraft.potion.ModPotionRecipe;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.Marker;

import java.text.DecimalFormat;
import java.util.UUID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MysticcraftMod.MOD_ID)
public class MysticcraftMod {
    public static final String MOD_ID = "mysticcraft";

    public static ResourceLocation res(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    public static <T> DeferredRegister<T> makeRegistry(IForgeRegistry<T> registry) {
        return DeferredRegister.create(registry, MOD_ID);
    }

    public static <T> DeferredRegister<T> makeRegistry(String location) {
        return DeferredRegister.create(res(location), MOD_ID);
    }

    public static <T> DeferredRegister<T> makeRegistry(ResourceKey<Registry<T>> key) {
        return DeferredRegister.create(key, MOD_ID);
    }
    public static final double DAMAGE_CALCULATION_VALUE = 50;

    public static final RandomSource RANDOM_SOURCE = RandomSource.create();

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final UUID[] ITEM_ATTRIBUTE_MODIFIER_ADD_FOR_SLOT = {UUID.fromString("7be43dcd-084a-49d6-91d7-280777b49926"), UUID.fromString("a15eaf40-a426-4084-9807-bdb93977db92"), UUID.fromString("e3e176da-2089-43d2-bdc9-1cde813b5424"), UUID.fromString("b41c0858-f948-4dd5-ac3b-90da936b7e95"), UUID.fromString("aab76209-c6f6-41c4-a5f0-b495feeefe01"), UUID.fromString("d5613269-c9d5-4884-a340-d32e97c7b7a3")};
    public static final UUID[] ITEM_ATTRIBUTE_MODIFIER_MUL_FOR_SLOT = {UUID.fromString("47bcd762-0959-4385-aee8-fa5d6f92c31f"), UUID.fromString("ef879c0f-4e70-4213-94b9-ab7103f521a6"), UUID.fromString("0128c1ad-a1a4-49bc-943f-c080d1adad73"), UUID.fromString("f5849fb9-28a6-4c6c-b591-3b1b2dece46b"), UUID.fromString("d7a8f3b9-8ee1-45fd-8f97-deaa4a615bf0"), UUID.fromString("520d4928-7ea8-4e4d-bda5-d89a898d79c2")};

    public MysticcraftMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModRegistryInit.register(modEventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientModConfig.SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonModConfig.SPEC);

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            sendRegisterDisplay("Item Properties");
            ModItemProperties.addCustomItemProperties();
            sendRegisterDisplay("Menu Screens");
            registerMenuScreens();
            sendInfo("rendering Mana Fluid");
            ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MANA_FLUID.get(), RenderType.translucent());
            ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MANA_FLUID.get(), RenderType.translucent());
        }

        private static void registerMenuScreens() {
            MenuScreens.register(ModMenuTypes.GEM_GRINDER.get(), GemstoneGrinderScreen::new);
            MenuScreens.register(ModMenuTypes.REFORGING_ANVIL.get(), ReforgeAnvilScreen::new);
        }

        @SubscribeEvent
        public static void onCommonSetup(FMLCommonSetupEvent event) {
            sendRegisterDisplay("custom Potion Recipes");
            BrewingRecipeRegistry.addRecipe(new ModPotionRecipe());
            sendRegisterDisplay("Entity World Generation");
            registerSpawnPlacements();
            sendRegisterDisplay("Rarities");
            Reforges.registerRarities();
            sendRegisterDisplay("Reforges");
            Reforges.register();
            sendRegisterDisplay("Anvil Uses");
            AnvilUses.registerUses();
            sendRegisterDisplay("Packet Handling");
            ModMessages.register();
        }

        private static void registerSpawnPlacements() {
            SpawnPlacements.register(ModEntityTypes.FROZEN_BLAZE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Monster::checkAnyLightMonsterSpawnRules);
        }
    }


    public static String doubleFormat(double d) {
        return new DecimalFormat("#.##").format(d);
    }

    public static void sendRegisterDisplay(String nameOfRegistered) {
        sendInfo("Registering " + nameOfRegistered + "...");
    }

    private static String lastMSG = "";

    public static void sendError(String error) {
        LOGGER.error(MYSTICCRAFT_MARKER, error);
    }

    public static void sendFatal(String fatal, Throwable throwable) throws Throwable {
        sendError(fatal);
        throw throwable;
    }

    public static void sendInfo(String info) {
        sendInfo(info, false, null);
    }

    public static void sendInfo(String info, boolean shouldRepeat, @Nullable Marker marker, Object... toAdd) {
        if (lastMSG == null || !lastMSG.equals(info) || shouldRepeat) LOGGER.info(marker == null ? MYSTICCRAFT_MARKER : marker, info, toAdd);
        lastMSG = info;
    }

    public static void sendWarn(String warn) {
        sendWarn(warn, false, null);
    }

    public static void sendWarn(String info, boolean shouldRepeat, Marker marker, Object... toAdd) {
        if (lastMSG == null || !lastMSG.equals(info) || shouldRepeat) LOGGER.info(marker == null ? MYSTICCRAFT_MARKER : marker, info, toAdd);
        lastMSG = info;
    }

    public static final Marker MYSTICCRAFT_MARKER = new ModMarker("MYSTICCRAFT");

}
