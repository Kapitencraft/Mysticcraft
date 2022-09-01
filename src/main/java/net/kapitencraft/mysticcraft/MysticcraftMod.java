package net.kapitencraft.mysticcraft;

import com.mojang.logging.LogUtils;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModEnchantments;
import net.kapitencraft.mysticcraft.init.ModEntityTypes;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.misc.ModItemProperties;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.util.UUID;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(MysticcraftMod.MOD_ID)
public class MysticcraftMod
{
    public static final String MOD_ID = "mysticcraft";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final UUID ITEM_ATTRIBUTE_MODDIFIER_ADD = UUID.fromString("7be43dcd-084a-49d6-91d7-280777b49926");
    public static final UUID ITEM_ATTRIBUTE_MODDIFIER_MUL = UUID.fromString("47bcd762-0959-4385-aee8-fa5d6f92c31f");

    public MysticcraftMod()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModAttributes.REGISTRY.register(modEventBus);
        ModItems.REGISTRY.register(modEventBus);
        ModEnchantments.REGISTRY.register(modEventBus);
        ModEntityTypes.REGISTRY.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        GeckoLib.initialize();
    }

    private void commonSetup(final FMLCommonSetupEvent event)   {
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ModItemProperties.addCustomItemProperties();
        }
    }
}
