package net.kapitencraft.mysticcraft.event.handler;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.GemstoneBlock;
import net.kapitencraft.mysticcraft.client.particle.CircleParticle;
import net.kapitencraft.mysticcraft.client.particle.DamageIndicatorParticle;
import net.kapitencraft.mysticcraft.client.particle.FireNormalParticle;
import net.kapitencraft.mysticcraft.client.particle.MagicCircleParticle;
import net.kapitencraft.mysticcraft.client.particle.flame.*;
import net.kapitencraft.mysticcraft.entity.client.renderer.*;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.init.ModEntityTypes;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.init.ModParticleTypes;
import net.kapitencraft.mysticcraft.item.ColoredItem;
import net.kapitencraft.mysticcraft.item.data.gemstone.IGemstoneItem;
import net.kapitencraft.mysticcraft.item.material.RainbowElementalShard;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Function;

@Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntityTypes.FROZEN_BLAZE.get(), FrozenBlazeRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.SKELETON_MASTER.get(), SkeletonMasterRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.VAMPIRE_BAT.get(), VampireBatRenderer::new);

        event.registerEntityRenderer(ModEntityTypes.FIRE_BOLD.get(), FireBoltRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.CRIMSON_DEATH_RAY.get(), CrimsonDeathRayRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.DAMAGE_INDICATOR.get(), DamageIndicatorRenderer::new);
        event.registerEntityRenderer(ModEntityTypes.LAVA_FISHING_HOOK.get(), ModFishingHookRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.register((SimpleParticleType) ModParticleTypes.FIRE_NORMAL.get(), FireNormalParticle.FireNormalParticleProvider::new);
        event.register((SimpleParticleType) ModParticleTypes.RED_FLAME.get(), RedFlameParticle.RisingFlameParticleProvider::new);
        event.register((SimpleParticleType) ModParticleTypes.DARK_BLUE_FLAME.get(), DarkBlueFlame.RisingFlameParticleProvider::new);
        event.register((SimpleParticleType) ModParticleTypes.LIGHT_BLUE_FLAME.get(), LightBlueFlame.RisingFlameParticleProvider::new);
        event.register((SimpleParticleType) ModParticleTypes.LIGHT_GREEN_FLAME.get(), LightGreenFlame.RisingFlameParticleProvider::new);
        event.register((SimpleParticleType) ModParticleTypes.DARK_GREEN_FLAME.get(), DarkGreenFlame.RisingFlameParticleProvider::new);
        event.register((SimpleParticleType) ModParticleTypes.PURPLE_FLAME.get(), PurpleFlame.RisingFlameParticleProvider::new);
        event.register(ModParticleTypes.MAGIC_CIRCLE.get(), MagicCircleParticle.MagicCircleParticleProvider::new);
        event.register(ModParticleTypes.DAMAGE_INDICATOR.get(), DamageIndicatorParticle.Provider::new);
        event.register(ModParticleTypes.CIRCLE.get(), CircleParticle.Provider::new);
    }

    @SubscribeEvent
    public static void registerColors(RegisterColorHandlersEvent.Item event) {
        MysticcraftMod.sendRegisterDisplay("Custom Item Colors");
        registerColor(event, ColoredItem::getColor, ModItems.DYED_LEATHER.get());
        registerColor(event, RainbowElementalShard::getColor, ModItems.RAINBOW_ELEMENTAL_SHARD.get());
        registerColor(event, IGemstoneItem::getColor, ModItems.GEMSTONE.get(), ModBlocks.GEMSTONE_BLOCK.getItem());
    }

    private static void registerColor(RegisterColorHandlersEvent.Item event, Function<ItemStack, Integer> func, Item... object) {
        event.register((stack, i) -> i > 0 ? -1 : func.apply(stack), object);
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        MysticcraftMod.sendRegisterDisplay("Custom Block Colors");
        event.register(GemstoneBlock::getColor, ModBlocks.GEMSTONE_BLOCK.getBlock());
    }
}
