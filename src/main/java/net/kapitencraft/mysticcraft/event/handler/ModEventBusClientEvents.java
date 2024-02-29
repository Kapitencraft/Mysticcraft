package net.kapitencraft.mysticcraft.event.handler;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneBlock;
import net.kapitencraft.mysticcraft.client.particle.*;
import net.kapitencraft.mysticcraft.client.particle.animation.ParticleAnimationProvider;
import net.kapitencraft.mysticcraft.client.particle.flame.ModFlameParticle;
import net.kapitencraft.mysticcraft.entity.client.renderer.*;
import net.kapitencraft.mysticcraft.gui.gemstone_grinder.GemstoneGrinderScreen;
import net.kapitencraft.mysticcraft.gui.reforging_anvil.ReforgeAnvilScreen;
import net.kapitencraft.mysticcraft.init.*;
import net.kapitencraft.mysticcraft.item.ColoredItem;
import net.kapitencraft.mysticcraft.item.capability.gemstone.IGemstoneItem;
import net.kapitencraft.mysticcraft.item.material.RainbowElementalShard;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.kapitencraft.mysticcraft.misc.ModItemProperties;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Function;

@Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MysticcraftMod.sendRegisterDisplay("Item Properties");
        ModItemProperties.addCustomItemProperties();
        MysticcraftMod.sendRegisterDisplay("Menu Screens");
        registerMenuScreens();
        MysticcraftMod.LOGGER.info(Markers.REGISTRY, "adding Fluid Renderers...");
        ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MANA_FLUID.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MANA_FLUID.get(), RenderType.translucent());
    }

    private static void registerMenuScreens() {
        MenuScreens.register(ModMenuTypes.GEM_GRINDER.get(), GemstoneGrinderScreen::new);
        MenuScreens.register(ModMenuTypes.REFORGING_ANVIL.get(), ReforgeAnvilScreen::new);
    }


    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.FROZEN_BLAZE.get(), FrozenBlazeRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SKELETON_MASTER.get(), SkeletonMasterRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.VAMPIRE_BAT.get(), VampireBatRenderer::new);

        event.registerEntityRenderer(ModEntityTypes.FIRE_BOLD.get(), FireBoltRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.CRIMSON_DEATH_RAY.get(), CrimsonDeathRayRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.LAVA_FISHING_HOOK.get(), ModFishingHookRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.register(ModParticleTypes.FIRE_NORMAL.get(), FireNormalParticle.FireNormalParticleProvider::new);
        event.register(ModParticleTypes.MAGIC_CIRCLE.get(), MagicCircleParticle.MagicCircleParticleProvider::new);
        event.register(ModParticleTypes.DAMAGE_INDICATOR.get(), DamageIndicatorParticle.Provider::new);
        event.register(ModParticleTypes.CIRCLE.get(), CircleParticle.Provider::new);
        event.register(ModParticleTypes.ANIMATION.get(), ParticleAnimationProvider::new);
        event.register(ModParticleTypes.FLAME.get(), ModFlameParticle.FlameParticleProvider::new);
        event.register(ModParticleTypes.SHADOW_SWEEP.get(), ShadowSweepParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerColors(RegisterColorHandlersEvent.Item event) {
        MysticcraftMod.sendRegisterDisplay("Custom Item Colors");
        registerColor(event, ColoredItem::getColor, ModItems.DYED_LEATHER.get());
        registerColor(event, RainbowElementalShard::getColor, ModItems.RAINBOW_ELEMENTAL_SHARD.get());
        registerColor(event, IGemstoneItem::getColor, ModItems.GEMSTONE.get(), ModBlocks.GEMSTONE_BLOCK.getItem(), ModBlocks.GEMSTONE_CRYSTAL.getItem());
    }

    private static void registerColor(RegisterColorHandlersEvent.Item event, Function<ItemStack, Integer> func, Item... object) {
        event.register((stack, i) -> i > 0 ? -1 : func.apply(stack), object);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        MysticcraftMod.sendRegisterDisplay("Custom Block Colors");
        event.register(GemstoneBlock::getColor, ModBlocks.GEMSTONE_BLOCK.getBlock());
        event.register(GemstoneBlock::getColor, ModBlocks.GEMSTONE_CRYSTAL.getBlock());
    }
}
