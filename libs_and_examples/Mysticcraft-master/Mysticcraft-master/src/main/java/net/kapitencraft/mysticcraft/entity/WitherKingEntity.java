
package net.kapitencraft.mysticcraft.entity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.kapitencraft.mysticcraft.procedures.WitherKingEntityIsHurtProcedure;
import net.kapitencraft.mysticcraft.procedures.WitherKingEntityDiesProcedure;
import net.kapitencraft.mysticcraft.init.MysticcraftModItems;
import net.kapitencraft.mysticcraft.init.MysticcraftModEntities;

public class WitherKingEntity extends Monster {
	private final ServerBossEvent bossInfo = new ServerBossEvent(this.getDisplayName(), ServerBossEvent.BossBarColor.RED,
			ServerBossEvent.BossBarOverlay.NOTCHED_20);

	public WitherKingEntity(PlayMessages.SpawnEntity packet, Level world) {
		this(MysticcraftModEntities.WITHER_KING.get(), world);
	}

	public WitherKingEntity(EntityType<WitherKingEntity> type, Level world) {
		super(type, world);
		xpReward = 100000;
		setNoAi(false);
		setCustomName(new TextComponent("The Wither King"));
		setCustomNameVisible(true);
		setPersistenceRequired();
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(MysticcraftModItems.THE_STUFF_OF_DESTRUCTION.get()));
		this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.ENCHANTED_GOLDEN_APPLE));
		this.setItemSlot(EquipmentSlot.HEAD, new ItemStack(MysticcraftModItems.REFINED_DIAMOND_ARMOR_HELMET.get()));
		this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(MysticcraftModItems.REFINED_DIAMOND_ARMOR_CHESTPLATE.get()));
		this.setItemSlot(EquipmentSlot.LEGS, new ItemStack(MysticcraftModItems.REFINED_DIAMOND_ARMOR_LEGGINGS.get()));
		this.setItemSlot(EquipmentSlot.FEET, new ItemStack(MysticcraftModItems.REFINED_DIAMOND_ARMOR_BOOTS.get()));
	}

	@Override
	public Packet<?> getAddEntityPacket() {
		return NetworkHooks.getEntitySpawningPacket(this);
	}

	@Override
	protected void registerGoals() {
		super.registerGoals();
		this.targetSelector.addGoal(1, new NearestAttackableTargetGoal(this, Player.class, false, false));
		this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, true) {
			@Override
			protected double getAttackReachSqr(LivingEntity entity) {
				return (double) (4.0 + entity.getBbWidth() * entity.getBbWidth());
			}
		});
		this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
		this.targetSelector.addGoal(4, new HurtByTargetGoal(this));
		this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
		this.goalSelector.addGoal(6, new FloatGoal(this));
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	@Override
	public boolean removeWhenFarAway(double distanceToClosestPlayer) {
		return false;
	}

	@Override
	public SoundEvent getAmbientSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.ambient"));
	}

	@Override
	public void playStepSound(BlockPos pos, BlockState blockIn) {
		this.playSound(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.break_block")), 0.15f, 1);
	}

	@Override
	public SoundEvent getHurtSound(DamageSource ds) {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.hurt"));
	}

	@Override
	public SoundEvent getDeathSound() {
		return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.wither.death"));
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		WitherKingEntityIsHurtProcedure.execute(this.level, this.getX(), this.getY(), this.getZ(), this);
		if (source == DamageSource.CACTUS)
			return false;
		if (source == DamageSource.DROWN)
			return false;
		if (source == DamageSource.LIGHTNING_BOLT)
			return false;
		if (source == DamageSource.WITHER)
			return false;
		if (source.getMsgId().equals("witherSkull"))
			return false;
		return super.hurt(source, amount);
	}

	@Override
	public void die(DamageSource source) {
		super.die(source);
		WitherKingEntityDiesProcedure.execute(this.level, this.getX(), this.getY(), this.getZ(), this);
	}

	@Override
	public boolean canChangeDimensions() {
		return false;
	}

	@Override
	public void startSeenByPlayer(ServerPlayer player) {
		super.startSeenByPlayer(player);
		this.bossInfo.addPlayer(player);
	}

	@Override
	public void stopSeenByPlayer(ServerPlayer player) {
		super.stopSeenByPlayer(player);
		this.bossInfo.removePlayer(player);
	}

	@Override
	public void customServerAiStep() {
		super.customServerAiStep();
		this.bossInfo.setProgress(this.getHealth() / this.getMaxHealth());
	}

	public void aiStep() {
		super.aiStep();
		double x = this.getX();
		double y = this.getY();
		double z = this.getZ();
		Entity entity = this;
		Level world = this.level;
		for (int l = 0; l < 5; ++l) {
			double x0 = x + 0.5 + (random.nextFloat() - 0.5) * 0.75D * 20;
			double y0 = y + 1.2 + (random.nextFloat() - 0.5) * 0.75D;
			double z0 = z + 0.5 + (random.nextFloat() - 0.5) * 0.75D * 20;
			world.addParticle(ParticleTypes.ENCHANTED_HIT, x0, y0, z0, 0, 0, 0);
		}
	}

	public static void init() {
	}

	public static AttributeSupplier.Builder createAttributes() {
		AttributeSupplier.Builder builder = Mob.createMobAttributes();
		builder = builder.add(Attributes.MOVEMENT_SPEED, 0.9);
		builder = builder.add(Attributes.MAX_HEALTH, 1024);
		builder = builder.add(Attributes.ARMOR, 20);
		builder = builder.add(Attributes.ATTACK_DAMAGE, 20);
		builder = builder.add(Attributes.FOLLOW_RANGE, 16);
		builder = builder.add(Attributes.ATTACK_KNOCKBACK, 0.5);
		return builder;
	}
}
