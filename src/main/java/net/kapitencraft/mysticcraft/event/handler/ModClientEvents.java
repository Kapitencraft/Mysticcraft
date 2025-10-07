package net.kapitencraft.mysticcraft.event.handler;

import net.kapitencraft.kap_lib.event.custom.client.RegisterChunkBufferLayersEvent;
import net.kapitencraft.kap_lib.event.custom.client.RegisterConfigurableOverlaysEvent;
import net.kapitencraft.kap_lib.event.custom.client.RegisterInventoryPageRenderersEvent;
import net.kapitencraft.kap_lib.event.custom.client.RegisterItemModifiersDisplayExtensionsEvent;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.render.AltarBlockEntityRenderer;
import net.kapitencraft.mysticcraft.block.entity.render.BasePedestalBlockEntityRenderer;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneBlock;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneHelper;
import net.kapitencraft.mysticcraft.capability.gemstone.IGemstoneItem;
import net.kapitencraft.mysticcraft.capability.reforging.Reforges;
import net.kapitencraft.mysticcraft.client.ItemCategory;
import net.kapitencraft.mysticcraft.client.ModKeyMappings;
import net.kapitencraft.mysticcraft.client.overlay.SpellCastChargeOverlay;
import net.kapitencraft.mysticcraft.client.overlay.SpellSelectionOverlay;
import net.kapitencraft.mysticcraft.client.particle.CircleParticle;
import net.kapitencraft.mysticcraft.client.particle.FireNormalParticle;
import net.kapitencraft.mysticcraft.client.particle.MagicCircleParticle;
import net.kapitencraft.mysticcraft.client.particle.ShadowSweepParticle;
import net.kapitencraft.mysticcraft.client.particle.flame.ModFlameParticle;
import net.kapitencraft.mysticcraft.client.rpg.perks.PerkInventoryPageRenderer;
import net.kapitencraft.mysticcraft.client.shader.ModRenderTypes;
import net.kapitencraft.mysticcraft.entity.client.model.ModModelLayers;
import net.kapitencraft.mysticcraft.entity.client.model.TestEntityModel;
import net.kapitencraft.mysticcraft.entity.client.model.VampireBatModel;
import net.kapitencraft.mysticcraft.entity.client.renderer.*;
import net.kapitencraft.mysticcraft.gui.artificer_table.ArtificerTableScreen;
import net.kapitencraft.mysticcraft.gui.reforging_anvil.ReforgeAnvilScreen;
import net.kapitencraft.mysticcraft.item.ColoredItem;
import net.kapitencraft.mysticcraft.misc.ModItemProperties;
import net.kapitencraft.mysticcraft.registry.*;
import net.kapitencraft.mysticcraft.tech.gui.screen.*;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.function.Function;

@Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onRegisterChunkBufferLayers(RegisterChunkBufferLayersEvent event) {
        event.register(ModRenderTypes.CHROMATIC_CUTOUT);
    }


    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(ModKeyMappings.SELECT_SPELL_CAST);
    }

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ModItemProperties.addCustomItemProperties();
        registerMenuScreens();
        ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MANA_FLUID.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MANA_FLUID.get(), RenderType.translucent());
        ItemCategory.Registry.register();
    }

    private static void registerMenuScreens() {
        MenuScreens.register(ModMenuTypes.GEM_GRINDER.get(), ArtificerTableScreen::new);
        MenuScreens.register(ModMenuTypes.REFORGING_ANVIL.get(), ReforgeAnvilScreen::new);
        MenuScreens.register(ModMenuTypes.PRISMATIC_GENERATOR.get(), PrismaticGeneratorScreen::new);
        MenuScreens.register(ModMenuTypes.VULCANIC_GENERATOR.get(), VulcanicGeneratorScreen::new);
        MenuScreens.register(ModMenuTypes.MAGIC_FURNACE.get(), MagicFurnaceScreen::new);
        MenuScreens.register(ModMenuTypes.MANA_BATTERY.get(), ManaBatteryScreen::new);
        MenuScreens.register(ModMenuTypes.SPELL_CASTER_TURRET.get(), SpellCasterTurretScreen::new);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.FROZEN_BLAZE.get(), FrozenBlazeRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SKELETON_MASTER.get(), SkeletonMasterRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.VAMPIRE_BAT.get(), VampireBatRenderer::new);

        event.registerEntityRenderer(ModEntityTypes.FIRE_BOLD.get(), FireBoltRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.CRIMSON_DEATH_RAY.get(), CrimsonDeathRayRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.LAVA_FISHING_HOOK.get(), FishingHookRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.CURSED_PEARL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.DRAGON.get(), DragonRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntities.ALTAR.get(), AltarBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.PEDESTAL.get(), BasePedestalBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void onEntityRenderersRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.VAMPIRE_BAT, VampireBatModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.DRAGON, TestEntityModel::createBodyLayer);
    }


    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticleTypes.FIRE_NORMAL.get(), FireNormalParticle.FireNormalParticleProvider::new);
        event.registerSpriteSet(ModParticleTypes.MAGIC_CIRCLE.get(), MagicCircleParticle.MagicCircleParticleProvider::new);
        event.registerSpriteSet(ModParticleTypes.CIRCLE.get(), CircleParticle.Provider::new);
        event.registerSpriteSet(ModParticleTypes.FLAME.get(), ModFlameParticle.FlameParticleProvider::new);
        event.registerSpriteSet(ModParticleTypes.SHADOW_SWEEP.get(), ShadowSweepParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerColors(RegisterColorHandlersEvent.Item event) {
        registerColor(event, ColoredItem::getColor, ModItems.DYED_LEATHER.get());
        registerColor(event, IGemstoneItem::getColor, ModItems.GEMSTONE.get(), ModBlocks.GEMSTONE_BLOCK.getItem(), ModBlocks.GEMSTONE_CRYSTAL.getItem(), ModBlocks.GEMSTONE_SEED.getItem());
    }

    private static void registerColor(RegisterColorHandlersEvent.Item event, Function<ItemStack, Integer> func, Item... object) {
        event.register((stack, i) -> i > 0 ? -1 : func.apply(stack), object);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        registerBlockColor(event, GemstoneBlock::getColor, ModBlocks.GEMSTONE_BLOCK, ModBlocks.GEMSTONE_CRYSTAL, ModBlocks.GEMSTONE_SEED);
    }

    private static void registerBlockColor(RegisterColorHandlersEvent.Block event, BlockColor color, BlockRegistryHolder<?, ?>... holders) {
        for (BlockRegistryHolder<?, ?> holder : holders) {
            event.register(color, holder.get());
        }
    }

    @SubscribeEvent
    public static void onRegisterItemModifiersDisplayExtensions(RegisterItemModifiersDisplayExtensionsEvent event) {
        event.registerEquipment(GemstoneHelper::getCapability);
        event.registerEquipment(Reforges::getReforgeDisplayExtension);
    }

    @SubscribeEvent
    public static void onRegisterInventoryPageRenderers(RegisterInventoryPageRenderersEvent event) {
        event.register(ModInventoryPages.PERKS, PerkInventoryPageRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterConfigurableOverlays(RegisterConfigurableOverlaysEvent event) {
        event.addOverlay(ModOverlays.CAST_CHARGE, SpellCastChargeOverlay::new);
        event.addOverlay(ModOverlays.SPELL_SELECTION, SpellSelectionOverlay::new);
    }

    @SubscribeEvent
    public static void onRegisterNamedRenderTypes(RegisterNamedRenderTypesEvent event) {
        event.register("chromatic_cutout", ModRenderTypes.CHROMATIC_CUTOUT, ModRenderTypes.CHROMATIC_CUTOUT_ENTITY);
    }

}
