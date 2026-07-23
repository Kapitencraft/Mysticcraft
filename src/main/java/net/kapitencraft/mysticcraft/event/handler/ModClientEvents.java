package net.kapitencraft.mysticcraft.event.handler;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.kapitencraft.kap_lib.inventory_page.event.custom.client.RegisterInventoryPageRenderersEvent;
import net.kapitencraft.kap_lib.item.event.custom.client.RegisterItemModifiersDisplayExtensionsEvent;
import net.kapitencraft.kap_lib.overlay.OverlayProperties;
import net.kapitencraft.kap_lib.overlay.event.custom.client.RegisterConfigurableOverlaysEvent;
import net.kapitencraft.kap_lib.shader.event.custom.client.RegisterChunkBufferLayersEvent;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.render.AltarBlockEntityRenderer;
import net.kapitencraft.mysticcraft.block.entity.render.BasePedestalBlockEntityRenderer;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneBlock;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneHandler;
import net.kapitencraft.mysticcraft.capability.gemstone.IGemstoneItem;
import net.kapitencraft.mysticcraft.capability.reforging.Reforges;
import net.kapitencraft.mysticcraft.client.ItemCategory;
import net.kapitencraft.mysticcraft.client.ModKeyMappings;
import net.kapitencraft.mysticcraft.client.model.ModPoses;
import net.kapitencraft.mysticcraft.client.overlay.ManaSupplyOverlay;
import net.kapitencraft.mysticcraft.client.overlay.SpellCastChargeOverlay;
import net.kapitencraft.mysticcraft.client.overlay.SpellSelectionOverlay;
import net.kapitencraft.mysticcraft.client.particle.CircleParticle;
import net.kapitencraft.mysticcraft.client.particle.FireNormalParticle;
import net.kapitencraft.mysticcraft.client.particle.MagicCircleParticle;
import net.kapitencraft.mysticcraft.client.particle.ShadowSweepParticle;
import net.kapitencraft.mysticcraft.client.particle.flame.ModFlameParticle;
import net.kapitencraft.mysticcraft.client.shader.ModRenderTypes;
import net.kapitencraft.mysticcraft.entity.client.model.DragonModel;
import net.kapitencraft.mysticcraft.entity.client.model.ModModelLayers;
import net.kapitencraft.mysticcraft.entity.client.model.VampireBatModel;
import net.kapitencraft.mysticcraft.entity.client.renderer.*;
import net.kapitencraft.mysticcraft.gui.artificer_table.ArtificerTableScreen;
import net.kapitencraft.mysticcraft.gui.reforging_anvil.ReforgeAnvilScreen;
import net.kapitencraft.mysticcraft.item.ColoredItem;
import net.kapitencraft.mysticcraft.registry.*;
import net.kapitencraft.mysticcraft.rpg.skill.client.CharacterInventoryPageRenderer;
import net.kapitencraft.mysticcraft.rpg.traits.client.TraitsInventoryPageRenderer;
import net.kapitencraft.mysticcraft.tech.gui.screen.*;
import net.kapitencraft.mysticcraft.util.ModItemProperties;
import net.minecraft.client.Camera;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.FishingHookRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.function.Function;

@EventBusSubscriber(modid = MysticcraftMod.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void onRegisterChunkBufferLayers(RegisterChunkBufferLayersEvent event) {
        event.register(ModRenderTypes.CHROMATIC_CUTOUT);
        event.register(ModRenderTypes.CHROMATIC_CUTOUT_NOISE);
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
        ItemBlockRenderTypes.setRenderLayer(ModFluids.SOURCE_MANA_FLUID.get(), RenderType.translucent());
        ItemBlockRenderTypes.setRenderLayer(ModFluids.FLOWING_MANA_FLUID.get(), RenderType.translucent());
        ItemCategory.Registry.register();
    }

    @SubscribeEvent
    public static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.GEM_GRINDER.get(), ArtificerTableScreen::new);
        event.register(ModMenuTypes.REFORGING_ANVIL.get(), ReforgeAnvilScreen::new);
        event.register(ModMenuTypes.PRISMATIC_GENERATOR.get(), PrismaticGeneratorScreen::new);
        event.register(ModMenuTypes.VULCANIC_GENERATOR.get(), VulcanicGeneratorScreen::new);
        event.register(ModMenuTypes.MAGIC_FURNACE.get(), MagicFurnaceScreen::new);
        event.register(ModMenuTypes.MANA_BATTERY.get(), ManaBatteryScreen::new);
        event.register(ModMenuTypes.SPELL_CASTER_TURRET.get(), SpellCasterTurretScreen::new);
        event.register(ModMenuTypes.OBELISK_TURRET.get(), ObeliskTurretScreen::new);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.FROZEN_BLAZE.get(), FrozenBlazeRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.VAMPIRE_BAT.get(), VampireBatRenderer::new);

        event.registerEntityRenderer(ModEntityTypes.FIRE_BOLD.get(), FireBoltRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.CRIMSON_DEATH_RAY.get(), CrimsonDeathRayRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.LAVA_FISHING_HOOK.get(), FishingHookRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.CURSED_PEARL.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SPLASH_POTION_OF_MILK.get(), ThrownItemRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.DRAGON.get(), DragonRenderer::new);

        event.registerBlockEntityRenderer(ModBlockEntities.ALTAR.get(), AltarBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(ModBlockEntities.PEDESTAL.get(), BasePedestalBlockEntityRenderer::new);
    }

    @SubscribeEvent
    public static void onEntityRenderersRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ModModelLayers.VAMPIRE_BAT, VampireBatModel::createBodyLayer);
        event.registerLayerDefinition(ModModelLayers.DRAGON, DragonModel::createBodyLayer);
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
        event.registerEquipment(GemstoneHandler::get);
        event.registerEquipment(Reforges::getReforgeDisplayExtension);
    }

    @SubscribeEvent
    public static void onRegisterConfigurableOverlays(RegisterConfigurableOverlaysEvent event) {
        event.addOverlay(
                SpellCastChargeOverlay.LOCATION,
                new OverlayProperties(-50, 50, 1, 1, OverlayProperties.Alignment.MIDDLE, OverlayProperties.Alignment.BOTTOM_RIGHT),
                SpellCastChargeOverlay::new
        );
        event.addOverlay(
                SpellSelectionOverlay.LOCATION,
                new OverlayProperties(142, 1, 1, 1, OverlayProperties.Alignment.BOTTOM_RIGHT, OverlayProperties.Alignment.TOP_LEFT),
                SpellSelectionOverlay::new
        );
    }

    @SubscribeEvent
    public static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAboveAll(MysticcraftMod.res("mana_overlay"), new ManaSupplyOverlay()::maybeRenderManaSupplyBar);
    }

    @SubscribeEvent
    public static void onRegisterNamedRenderTypes(RegisterNamedRenderTypesEvent event) {
        event.register(MysticcraftMod.res("chromatic_cutout"), ModRenderTypes.CHROMATIC_CUTOUT, ModRenderTypes.CHROMATIC_CUTOUT_ENTITY);
        event.register(MysticcraftMod.res("chromatic_cutout_noise"), ModRenderTypes.CHROMATIC_CUTOUT_NOISE, ModRenderTypes.CHROMATIC_CUTOUT_ENTITY_NOISE);
    }

    private static final ResourceLocation WATER_STILL = ResourceLocation.withDefaultNamespace("block/water_still");
    private static final ResourceLocation WATER_FLOW = ResourceLocation.withDefaultNamespace("block/water_flow");
    private static final ResourceLocation MANA_FLUID_OVERLAY = MysticcraftMod.res("block/mana_fluid");


    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerItem(
                new IClientItemExtensions() {
                    @Override
                    public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                        return entityLiving.isUsingItem() && entityLiving.getUsedItemHand() == hand ? HumanoidModel.ArmPose.THROW_SPEAR : null;
                    }
                },
                ModItems.AOTE, ModItems.AOTV, ModItems.BURNING_SCYTHE, ModItems.INFERNAL_SCYTHE, ModItems.HEATED_SCYTHE, ModItems.FIERY_SCYTHE);
        event.registerItem(
                new IClientItemExtensions() {
                    @Override
                    public HumanoidModel.ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                        return entityLiving.isUsingItem() ? ModPoses.CAST_SPELL : HumanoidModel.ArmPose.ITEM;
                    }
                },
                ModItems.FIRE_LANCE
        );
        event.registerFluidType(new IClientFluidTypeExtensions() {
            @Override
            public int getTintColor() {
                return 0xA100e2ff;
            }

            @Override
            public @NotNull ResourceLocation getStillTexture() {
                return WATER_STILL;
            }

            @Override
            public @NotNull ResourceLocation getFlowingTexture() {
                return WATER_FLOW;
            }

            @Override
            public @NotNull ResourceLocation getOverlayTexture() {
                return MANA_FLUID_OVERLAY;
            }

            @Override
            public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                return new Vector3f();
            }

            @Override
            public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                RenderSystem.setShaderFogStart(1f);
                RenderSystem.setShaderFogEnd(6f);
            }
        }, ModFluidTypes.MANA_FLUID_TYPE.get());
    }

    @SubscribeEvent
    public static void onRegisterInventoryPageRenderers(RegisterInventoryPageRenderersEvent event) {
        event.register(ModInventoryPageTypes.SKILLS, CharacterInventoryPageRenderer::new);
        event.register(ModInventoryPageTypes.PERKS, TraitsInventoryPageRenderer::new);
    }
}
