
/*
 *    MCreator note: This file will be REGENERATED on each build.
 */
package net.kapitencraft.mysticcraft.init;

import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;

import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;

import net.kapitencraft.mysticcraft.entity.WitherSoldierEntity;
import net.kapitencraft.mysticcraft.entity.WitherLordEntity;
import net.kapitencraft.mysticcraft.entity.WitherKingEntity;
import net.kapitencraft.mysticcraft.entity.TheStuffOfDestructionEntity;
import net.kapitencraft.mysticcraft.entity.TeleportBowEntity;
import net.kapitencraft.mysticcraft.entity.ShadowWarpBowEntity;
import net.kapitencraft.mysticcraft.entity.LongBowEntity;
import net.kapitencraft.mysticcraft.entity.LightningStaffEntity;
import net.kapitencraft.mysticcraft.entity.HomingbowEntity;
import net.kapitencraft.mysticcraft.entity.GoblinEntity;
import net.kapitencraft.mysticcraft.entity.ExplosivStaffEntity;
import net.kapitencraft.mysticcraft.MysticcraftMod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class MysticcraftModEntities {
	public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.ENTITIES, MysticcraftMod.MODID);
	public static final RegistryObject<EntityType<HomingbowEntity>> HOMINGBOW = register("projectile_homingbow",
			EntityType.Builder.<HomingbowEntity>of(HomingbowEntity::new, MobCategory.MISC).setCustomClientFactory(HomingbowEntity::new)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<GoblinEntity>> GOBLIN = register("goblin",
			EntityType.Builder.<GoblinEntity>of(GoblinEntity::new, MobCategory.CREATURE).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.setUpdateInterval(3).setCustomClientFactory(GoblinEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<LightningStaffEntity>> LIGHTNING_STAFF = register("projectile_lightning_staff",
			EntityType.Builder.<LightningStaffEntity>of(LightningStaffEntity::new, MobCategory.MISC).setCustomClientFactory(LightningStaffEntity::new)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<ExplosivStaffEntity>> EXPLOSIV_STAFF = register("projectile_explosiv_staff",
			EntityType.Builder.<ExplosivStaffEntity>of(ExplosivStaffEntity::new, MobCategory.MISC).setCustomClientFactory(ExplosivStaffEntity::new)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<ShadowWarpBowEntity>> SHADOW_WARP_BOW = register("projectile_shadow_warp_bow",
			EntityType.Builder.<ShadowWarpBowEntity>of(ShadowWarpBowEntity::new, MobCategory.MISC).setCustomClientFactory(ShadowWarpBowEntity::new)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<WitherKingEntity>> WITHER_KING = register("wither_king",
			EntityType.Builder.<WitherKingEntity>of(WitherKingEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(100).setUpdateInterval(3).setCustomClientFactory(WitherKingEntity::new)

					.sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<WitherLordEntity>> WITHER_LORD = register("wither_lord",
			EntityType.Builder.<WitherLordEntity>of(WitherLordEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(WitherLordEntity::new).fireImmune().sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<WitherSoldierEntity>> WITHER_SOLDIER = register("wither_soldier",
			EntityType.Builder.<WitherSoldierEntity>of(WitherSoldierEntity::new, MobCategory.MONSTER).setShouldReceiveVelocityUpdates(true)
					.setTrackingRange(64).setUpdateInterval(3).setCustomClientFactory(WitherSoldierEntity::new).fireImmune().sized(0.6f, 1.8f));
	public static final RegistryObject<EntityType<TheStuffOfDestructionEntity>> THE_STUFF_OF_DESTRUCTION = register(
			"projectile_the_stuff_of_destruction",
			EntityType.Builder.<TheStuffOfDestructionEntity>of(TheStuffOfDestructionEntity::new, MobCategory.MISC)
					.setCustomClientFactory(TheStuffOfDestructionEntity::new).setShouldReceiveVelocityUpdates(true).setTrackingRange(64)
					.setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<TeleportBowEntity>> TELEPORT_BOW = register("projectile_teleport_bow",
			EntityType.Builder.<TeleportBowEntity>of(TeleportBowEntity::new, MobCategory.MISC).setCustomClientFactory(TeleportBowEntity::new)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));
	public static final RegistryObject<EntityType<LongBowEntity>> LONG_BOW = register("projectile_long_bow",
			EntityType.Builder.<LongBowEntity>of(LongBowEntity::new, MobCategory.MISC).setCustomClientFactory(LongBowEntity::new)
					.setShouldReceiveVelocityUpdates(true).setTrackingRange(64).setUpdateInterval(1).sized(0.5f, 0.5f));

	private static <T extends Entity> RegistryObject<EntityType<T>> register(String registryname, EntityType.Builder<T> entityTypeBuilder) {
		return REGISTRY.register(registryname, () -> (EntityType<T>) entityTypeBuilder.build(registryname));
	}

	@SubscribeEvent
	public static void init(FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			GoblinEntity.init();
			WitherKingEntity.init();
			WitherLordEntity.init();
			WitherSoldierEntity.init();
		});
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(GOBLIN.get(), GoblinEntity.createAttributes().build());
		event.put(WITHER_KING.get(), WitherKingEntity.createAttributes().build());
		event.put(WITHER_LORD.get(), WitherLordEntity.createAttributes().build());
		event.put(WITHER_SOLDIER.get(), WitherSoldierEntity.createAttributes().build());
	}
}
