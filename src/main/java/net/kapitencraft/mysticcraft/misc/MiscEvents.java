package net.kapitencraft.mysticcraft.misc;


import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.GemstoneBlock;
import net.kapitencraft.mysticcraft.client.particle.CircleParticle;
import net.kapitencraft.mysticcraft.client.particle.DamageIndicatorParticle;
import net.kapitencraft.mysticcraft.client.particle.FireNormalParticle;
import net.kapitencraft.mysticcraft.client.particle.MagicCircleParticle;
import net.kapitencraft.mysticcraft.client.particle.flame.*;
import net.kapitencraft.mysticcraft.entity.client.renderer.*;
import net.kapitencraft.mysticcraft.guild.GuildHandler;
import net.kapitencraft.mysticcraft.init.*;
import net.kapitencraft.mysticcraft.item.combat.duel.DuelHandler;
import net.kapitencraft.mysticcraft.item.data.gemstone.GemstoneItem;
import net.kapitencraft.mysticcraft.item.data.gemstone.GemstoneType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

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
            event.register(ModParticleTypes.CIRCLE.get(), CircleParticle.Provider::new);
        }

        @SubscribeEvent
        public static void registerColors(RegisterColorHandlersEvent.Item event) {
            MysticcraftMod.sendRegisterDisplay("Custom Item Colors");
            event.register((stack, i) -> i > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack), ModItems.DYED_LEATHER.get());
            event.register((stack, i) -> i > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack), ModItems.RAINBOW_ELEMENTAL_SHARD.get());
            for (HashMap<GemstoneType.Rarity, RegistryObject<GemstoneItem>> item : ModItems.GEMSTONES.values()) {
                 for (GemstoneItem item1 : item.values().stream().map(RegistryObject::get).toList()) {
                    event.register((p_92708_, p_92709_) -> p_92709_ > 0 ? -1 : ((GemstoneItem) p_92708_.getItem()).getColor(p_92708_), item1);
                }
            }
            for (Map.Entry<GemstoneType, BlockRegistryHolder<GemstoneBlock>> entry : ModBlocks.GEMSTONE_BLOCKS.entrySet()) {
                event.register((stack, i) -> entry.getKey().getColour(), entry.getValue().getBlock());
            }
        }

        @SubscribeEvent
        public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
            MysticcraftMod.sendRegisterDisplay("Custom Block Colors");
            ModBlocks.GEMSTONE_BLOCKS.forEach((type, blockRegistryHolder) -> event.register((state, tintGetter, pos, i) -> type.getColour(), blockRegistryHolder.getBlock()));
        }
    }


    @Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientModEvents {

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

    }

    @Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ModEvents {
        @SubscribeEvent
        public static void loadingLevel(LevelEvent.Load event) {
            if (event.getLevel() instanceof ServerLevel serverLevel && serverLevel.dimension() == Level.OVERWORLD) {
                GuildHandler.setInstance(serverLevel.getDataStorage().computeIfAbsent((tag -> GuildHandler.load(tag, serverLevel.getServer())), GuildHandler::createDefault, "guilds"));
                DuelHandler.setInstance(serverLevel.getDataStorage().computeIfAbsent(tag -> DuelHandler.load(tag, serverLevel.getServer()), DuelHandler::new, "duels"));
            }
        }
    }
}
