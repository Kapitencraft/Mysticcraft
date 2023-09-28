package net.kapitencraft.mysticcraft.misc;


import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.entity.renderer.*;
import net.kapitencraft.mysticcraft.guild.GuildHandler;
import net.kapitencraft.mysticcraft.init.ModEntityTypes;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.init.ModParticleTypes;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.particle.DamageIndicatorParticle;
import net.kapitencraft.mysticcraft.particle.FireNormalParticle;
import net.kapitencraft.mysticcraft.particle.MagicCircleParticle;
import net.kapitencraft.mysticcraft.particle.flame.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;

public class MiscEvents {
    @Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientForgeEvents {
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
        }

        @SubscribeEvent
        public static void registerColors(RegisterColorHandlersEvent.Item event) {
            MysticcraftMod.sendRegisterDisplay("Custom Colors");
            event.register((p_92708_, p_92709_) -> p_92709_ > 0 ? -1 : ((DyeableLeatherItem) p_92708_.getItem()).getColor(p_92708_), ModItems.DYED_LEATHER.get());
            for (HashMap<GemstoneType.Rarity, RegistryObject<GemstoneItem>> item : ModItems.GEMSTONES.values()) {
                for (GemstoneItem item1 : item.values().stream().map(RegistryObject::get).toList()) {
                    event.register((p_92708_, p_92709_) -> p_92709_ > 0 ? -1 : ((DyeableLeatherItem) p_92708_.getItem()).getColor(p_92708_), item1);
                }
            }
        }

    }

    @Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onKeyRegister(RegisterKeyMappingsEvent event) {
            event.register(ModKeyMappings.NEXT);
            event.register(ModKeyMappings.PREVIOUS);
            event.register(ModKeyMappings.STASH);
        }

        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntityTypes.FROZEN_BLAZE.get(), FrozenBlazeRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SCHNAUZEN_PLUESCH.get(), SchnauzenPlueschRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.SKELETON_MASTER.get(), SkeletonMasterRenderer::new);

            event.registerEntityRenderer(ModEntityTypes.FIRE_BOLD.get(), FireBoltRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.CRIMSON_DEATH_RAY.get(), CrimsonDeathRayRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.DAMAGE_INDICATOR.get(), DamageIndicatorRenderer::new);
            event.registerEntityRenderer(ModEntityTypes.LAVA_FISHING_HOOK.get(), ModFishingHookRenderer::new);
        }

    }

    @Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ModEvents {
        @SubscribeEvent
        public static void loadingLevel(LevelEvent.Load event) {
            if (event.getLevel() instanceof ServerLevel serverLevel && serverLevel.dimension() == Level.OVERWORLD) {
                GuildHandler.setInstance(serverLevel.getDataStorage().computeIfAbsent((tag -> GuildHandler.load(tag, serverLevel.getServer())), GuildHandler::createDefault, "guilds"));
            }
        }
    }
}
