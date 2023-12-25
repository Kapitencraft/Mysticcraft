package net.kapitencraft.mysticcraft.entity;

import com.mojang.logging.LogUtils;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.entity.item.NoFireItemEntity;
import net.kapitencraft.mysticcraft.fluid.ModFluidTags;
import net.kapitencraft.mysticcraft.helpers.FishingHookHelper;
import net.kapitencraft.mysticcraft.item.loot_table.ModLootTables;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.List;

public class ModFishingHook extends Projectile {
    private static final Logger LOGGER = LogUtils.getLogger();
    private final RandomSource syncronizedRandom = RandomSource.create();
    private boolean biting;
    private int outOfWaterTime;
    private static final EntityDataAccessor<Integer> DATA_HOOKED_ENTITY = SynchedEntityData.defineId(ModFishingHook.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_BITING = SynchedEntityData.defineId(ModFishingHook.class, EntityDataSerializers.BOOLEAN);
    private int life;
    private int nibble;
    private int timeUntilLured;
    private static final int MAX_UNTIL_LURED = 200;
    private int timeUntilHooked;
    private float fishAngle;
    private boolean openWater = true;
    @Nullable
    private Entity hookedIn;
    private FishHookState currentState = FishHookState.FLYING;
    private TagKey<Fluid> fluidType;
    private final int luck;
    private final int lureSpeed;

    private ModFishingHook(EntityType<? extends ModFishingHook> p_150141_, Level p_150142_, int luck, int lureSpeed, TagKey<Fluid> fluidType) {
        super(p_150141_, p_150142_);
        this.noCulling = true;
        this.luck = Math.max(0, luck);
        this.lureSpeed = Math.max(0, lureSpeed);
        this.fluidType = fluidType;
    }

    protected ModFishingHook(EntityType<? extends ModFishingHook> type, Level level, TagKey<Fluid> fluidType) {
        this(type, level, 0, 0, fluidType);
        MysticcraftMod.sendWarn(level instanceof ServerLevel ? "server":"client");
    }


    protected ModFishingHook(EntityType<? extends ModFishingHook> type, Player p_37106_, Level level, int luck, int lureSpeed, TagKey<Fluid> fluidType) {
        this(type, level, luck, lureSpeed, fluidType);
        this.setOwner(p_37106_);
        float f = p_37106_.getXRot();
        float f1 = p_37106_.getYRot();
        float f2 = Mth.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-f * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-f * ((float)Math.PI / 180F));
        double d0 = p_37106_.getX() - (double)f3 * 0.3D;
        double d1 = p_37106_.getEyeY();
        double d2 = p_37106_.getZ() - (double)f2 * 0.3D;
        this.moveTo(d0, d1, d2, f1, f);
        Vec3 vec3 = new Vec3(-f3, Mth.clamp(-(f5 / f4), -5.0F, 5.0F), -f2);
        double d3 = vec3.length();
        vec3 = vec3.multiply(0.6D / d3 + this.random.triangle(0.5D, 0.0103365D), 0.6D / d3 + this.random.triangle(0.5D, 0.0103365D), 0.6D / d3 + this.random.triangle(0.5D, 0.0103365D));
        this.setDeltaMovement(vec3);
        this.setYRot((float)(Mth.atan2(vec3.x, vec3.z) * (double)(180F / (float)Math.PI)));
        this.setXRot((float)(Mth.atan2(vec3.y, vec3.horizontalDistance()) * (double)(180F / (float)Math.PI)));
        this.yRotO = this.getYRot();
        this.xRotO = this.getXRot();
    }


    protected void defineSynchedData() {
        this.getEntityData().define(DATA_HOOKED_ENTITY, 0);
        this.getEntityData().define(DATA_BITING, false);
    }

    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> p_37153_) {
        if (DATA_HOOKED_ENTITY.equals(p_37153_)) {
            int i = this.getEntityData().get(DATA_HOOKED_ENTITY);
            this.hookedIn = i > 0 ? this.level.getEntity(i - 1) : null;
        }

        if (DATA_BITING.equals(p_37153_)) {
            this.biting = this.getEntityData().get(DATA_BITING);
            if (this.biting) {
                this.setDeltaMovement(this.getDeltaMovement().x, -0.4F * Mth.nextFloat(this.syncronizedRandom, 0.6F, 1.0F), this.getDeltaMovement().z);
            }
        }

        super.onSyncedDataUpdated(p_37153_);
    }

    public boolean shouldRenderAtSqrDistance(double p_37125_) {
        return p_37125_ < 4096.0D;
    }

    public void lerpTo(double p_37127_, double p_37128_, double p_37129_, float p_37130_, float p_37131_, int p_37132_, boolean p_37133_) {
    }

    public void tick() {
        this.syncronizedRandom.setSeed(this.getUUID().getLeastSignificantBits() ^ this.level.getGameTime());
        super.tick();
        Player player = this.getPlayerOwner();
        if (player == null) {
            this.discard();
        } else if (this.level.isClientSide || !this.shouldStopFishing(player)) {
            if (this.onGround) {
                ++this.life;
                if (this.life >= 1200) {
                    this.discard();
                    return;
                }
            } else {
                this.life = 0;
            }

            float f = 0.0F;
            BlockPos blockpos = this.blockPosition();
            FluidState fluidstate = this.level.getFluidState(blockpos);
            if (fluidstate.is(this.fluidType)) {
                f = fluidstate.getHeight(this.level, blockpos);
            }

            boolean flag = f > 0.0F;
            if (this.currentState == FishHookState.FLYING) {
                if (this.hookedIn != null) {
                    this.setDeltaMovement(Vec3.ZERO);
                    this.currentState = FishHookState.HOOKED_IN_ENTITY;
                    return;
                }

                if (flag) {
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.3D, 0.2D, 0.3D));
                    this.currentState = FishHookState.BOBBING;
                    return;
                }

                this.checkCollision();
            } else {
                if (this.currentState == FishHookState.HOOKED_IN_ENTITY) {
                    if (this.hookedIn != null) {
                        if (!this.hookedIn.isRemoved() && this.hookedIn.level.dimension() == this.level.dimension()) {
                            this.setPos(this.hookedIn.getX(), this.hookedIn.getY(0.8D), this.hookedIn.getZ());
                        } else {
                            this.setHookedEntity(null);
                            this.currentState = FishHookState.FLYING;
                        }
                    }

                    return;
                }

                if (this.currentState == FishHookState.BOBBING) {
                    Vec3 vec3 = this.getDeltaMovement();
                    double d0 = this.getY() + vec3.y - (double)blockpos.getY() - (double)f;
                    if (Math.abs(d0) < 0.01D) {
                        d0 += Math.signum(d0) * 0.1D;
                    }

                    this.setDeltaMovement(vec3.x * 0.9D, vec3.y - d0 * (double)this.random.nextFloat() * 0.2D, vec3.z * 0.9D);
                    if (this.nibble <= 0 && this.timeUntilHooked <= 0) {
                        this.openWater = true;
                    } else {
                        this.openWater = this.openWater && this.outOfWaterTime < 10 && this.calculateOpenWater(blockpos);
                    }

                    if (flag) {
                        this.outOfWaterTime = Math.max(0, this.outOfWaterTime - 1);
                        if (this.biting) {
                            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.1D * (double)this.syncronizedRandom.nextFloat() * (double)this.syncronizedRandom.nextFloat(), 0.0D));
                        }

                        if (!this.level.isClientSide) {
                            this.catchingFish(blockpos);
                        }
                    } else {
                        this.outOfWaterTime = Math.min(10, this.outOfWaterTime + 1);
                    }
                }
            }

            if (!fluidstate.is(this.fluidType)) {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.03D, 0.0D));
            }

            this.move(MoverType.SELF, this.getDeltaMovement());
            this.updateRotation();
            if (this.currentState == FishHookState.FLYING && (this.onGround || this.horizontalCollision)) {
                this.setDeltaMovement(Vec3.ZERO);
            }

            double d1 = 0.92D;
            this.setDeltaMovement(this.getDeltaMovement().scale(d1));
            this.reapplyPosition();
        }
    }

    private boolean shouldStopFishing(Player p_37137_) {
        ItemStack itemstack = p_37137_.getMainHandItem();
        ItemStack itemstack1 = p_37137_.getOffhandItem();
        boolean flag = itemstack.canPerformAction(net.minecraftforge.common.ToolActions.FISHING_ROD_CAST);
        boolean flag1 = itemstack1.canPerformAction(net.minecraftforge.common.ToolActions.FISHING_ROD_CAST);
        if (!p_37137_.isRemoved() && p_37137_.isAlive() && (flag || flag1) && !(this.distanceToSqr(p_37137_) > 1024.0D)) {
            return false;
        } else {
            this.discard();
            return true;
        }
    }

    private void checkCollision() {
        HitResult hitresult = ProjectileUtil.getHitResult(this, this::canHitEntity);
        if (hitresult.getType() == HitResult.Type.MISS || !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) this.onHit(hitresult);
    }

    protected boolean canHitEntity(@NotNull Entity p_37135_) {
        return super.canHitEntity(p_37135_) || p_37135_.isAlive() && p_37135_ instanceof ItemEntity;
    }

    protected void onHitEntity(@NotNull EntityHitResult p_37144_) {
        super.onHitEntity(p_37144_);
        if (!this.level.isClientSide) {
            this.setHookedEntity(p_37144_.getEntity());
        }

    }

    protected void onHitBlock(@NotNull BlockHitResult p_37142_) {
        super.onHitBlock(p_37142_);
        this.setDeltaMovement(this.getDeltaMovement().normalize().scale(p_37142_.distanceTo(this)));
    }

    private void setHookedEntity(@Nullable Entity p_150158_) {
        this.hookedIn = p_150158_;
        this.getEntityData().set(DATA_HOOKED_ENTITY, p_150158_ == null ? 0 : p_150158_.getId() + 1);
    }

    private void catchingFish(BlockPos p_37146_) {
        ServerLevel serverlevel = (ServerLevel)this.level;
        int i = 1;
        BlockPos blockpos = p_37146_.above();
        if (this.random.nextFloat() < 0.25F && this.level.isRainingAt(blockpos)) {
            ++i;
        }

        if (this.random.nextFloat() < 0.5F && !this.level.canSeeSky(blockpos)) {
            --i;
        }
        if (this.nibble > 0) {
            --this.nibble;
            if (this.nibble <= 0) {
                this.timeUntilLured = 0;
                this.timeUntilHooked = 0;
                this.getEntityData().set(DATA_BITING, false);
            }
        } else if (this.timeUntilHooked > 0) {
            this.timeUntilHooked -= i;
            if (this.timeUntilHooked > 0) {
                this.fishAngle += (float)this.random.triangle(0.0D, 9.188D);
                float f = this.fishAngle * ((float)Math.PI / 180F);
                float f1 = Mth.sin(f);
                float f2 = Mth.cos(f);
                double d0 = this.getX() + (double)(f1 * (float)this.timeUntilHooked * 0.1F);
                double d1 = (float)Mth.floor(this.getY()) + 1.0F;
                double d2 = this.getZ() + (double)(f2 * (float)this.timeUntilHooked * 0.1F);
                if (serverlevel.getBlockState(new BlockPos((int)d0, (int)d1 - 1, (int)d2)).getMaterial() == Material.LAVA) {
                    if (this.random.nextFloat() < 0.15F) {
                        serverlevel.sendParticles(ParticleTypes.BUBBLE, d0, d1 - (double)0.1F, d2, 1, f1, 0.1D, f2, 0.0D);
                    }

                    float f3 = f1 * 0.04F;
                    float f4 = f2 * 0.04F;
                    serverlevel.sendParticles(ParticleTypes.FISHING, d0, d1, d2, 0, f4, 0.01D, -f3, 1.0D);
                    serverlevel.sendParticles(ParticleTypes.FISHING, d0, d1, d2, 0, -f4, 0.01D, f3, 1.0D);
                }
            } else {
                this.playSound(SoundEvents.FISHING_BOBBER_SPLASH, 0.25F, 1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.4F);
                double d3 = this.getY() + 0.5D;
                serverlevel.sendParticles(ParticleTypes.BUBBLE, this.getX(), d3, this.getZ(), (int)(1.0F + this.getBbWidth() * 20.0F), this.getBbWidth(), 0.0D, this.getBbWidth(), 0.2F);
                serverlevel.sendParticles(ParticleTypes.FISHING, this.getX(), d3, this.getZ(), (int)(1.0F + this.getBbWidth() * 20.0F), this.getBbWidth(), 0.0D, this.getBbWidth(), 0.2F);
                this.nibble = Mth.nextInt(this.random, 20, 40);
                this.getEntityData().set(DATA_BITING, true);
            }
        } else if (this.timeUntilLured > 0) {
            this.timeUntilLured -= i;
            float f5 = 0.15F;
            if (this.timeUntilLured < 20) {
                f5 += (float)(20 - this.timeUntilLured) * 0.05F;
            } else if (this.timeUntilLured < 40) {
                f5 += (float)(40 - this.timeUntilLured) * 0.02F;
            } else if (this.timeUntilLured < 60) {
                f5 += (float)(60 - this.timeUntilLured) * 0.01F;
            }

            if (this.random.nextFloat() < f5) {
                float f6 = Mth.nextFloat(this.random, 0.0F, 360.0F) * ((float)Math.PI / 180F);
                float f7 = Mth.nextFloat(this.random, 25.0F, 60.0F);
                double d4 = this.getX() + (double)(Mth.sin(f6) * f7) * 0.1D;
                double d5 = (float)Mth.floor(this.getY()) + 1.0F;
                double d6 = this.getZ() + (double)(Mth.cos(f6) * f7) * 0.1D;
                if (serverlevel.getBlockState(new BlockPos(d4, d5 - 1.0D, d6)).getMaterial() == net.minecraft.world.level.material.Material.WATER) {
                    serverlevel.sendParticles(ParticleTypes.SPLASH, d4, d5, d6, 2 + this.random.nextInt(2), 0.1F, 0.0D, 0.1F, 0.0D);
                }
            }

            if (this.timeUntilLured <= 0) {
                this.fishAngle = Mth.nextFloat(this.random, 0.0F, 360.0F);
                this.timeUntilHooked = Mth.nextInt(this.random, 20, 80);
            }
        } else {
            this.timeUntilLured = Mth.nextInt(this.random, 100, MAX_UNTIL_LURED);
            this.timeUntilLured -= this.lureSpeed * 100;
            if (timeUntilLured < 1) timeUntilLured = 1;
        }
    }

    private boolean calculateOpenWater(BlockPos pos) {
        OpenWaterType waterType = OpenWaterType.INVALID;
        for(int i = -1; i <= 2; ++i) {
            OpenWaterType waterType1 = this.getOpenWaterTypeForArea(pos.offset(-2, i, -2), pos.offset(2, i, 2));
            switch (waterType1) {
                case INVALID:
                    return false;
                case ABOVE_WATER:
                    if (waterType == OpenWaterType.INVALID) {
                        return false;
                    }
                    break;
                case INSIDE_WATER:
                    if (waterType == OpenWaterType.ABOVE_WATER) {
                        return false;
                    }
            }
            waterType = waterType1;
        }
        return true;
    }

    private OpenWaterType getOpenWaterTypeForArea(BlockPos p_37148_, BlockPos p_37149_) {
        return BlockPos.betweenClosedStream(p_37148_, p_37149_).map(this::getOpenWaterTypeForBlock).reduce((p_37139_, p_37140_) -> p_37139_ == p_37140_ ? p_37139_ : OpenWaterType.INVALID).orElse(OpenWaterType.INVALID);
    }

    private OpenWaterType getOpenWaterTypeForBlock(BlockPos p_37164_) {
        BlockState blockstate = this.level.getBlockState(p_37164_);
        if (!blockstate.isAir() && !blockstate.is(Blocks.LILY_PAD)) {
            FluidState fluidstate = blockstate.getFluidState();
            return fluidstate.is(this.fluidType) && fluidstate.isSource() && blockstate.getCollisionShape(this.level, p_37164_).isEmpty() ? OpenWaterType.INSIDE_WATER : OpenWaterType.INVALID;
        } else {
            return OpenWaterType.ABOVE_WATER;
        }
    }

    public void addAdditionalSaveData(@NotNull CompoundTag tag) {
        tag.putString("fluidType", this.fluidType.toString());
    }

    public void readAdditionalSaveData(CompoundTag tag) {
        this.fluidType = ModFluidTags.getByName(tag.getString("fluidType"));
    }

    public int retrieve(ItemStack p_37157_) {
        Player player = this.getPlayerOwner();
        if (this.level instanceof ServerLevel serverLevel && player != null && !this.shouldStopFishing(player)) {
            int i = 0;
            if (this.hookedIn != null) {
                this.pullEntity(this.hookedIn);
                this.level.broadcastEntityEvent(this, (byte)31);
                i = this.hookedIn instanceof ItemEntity ? 3 : 5;
            } else if (this.nibble > 0) {
                LootContext.Builder builder = (new LootContext.Builder((ServerLevel)this.level));
                builder.withParameter(LootContextParams.ORIGIN, this.position());
                builder.withParameter(LootContextParams.TOOL, p_37157_);
                builder.withParameter(LootContextParams.THIS_ENTITY, this);
                builder.withRandom(this.random).withLuck((float)this.luck + player.getLuck());
                if (this.getOwner() != null) {
                    builder.withParameter(LootContextParams.KILLER_ENTITY, this.getOwner());
                }
                LootTable loottable = serverLevel.getServer().getLootTables().get(ModLootTables.LAVA_FISHING);
                List<ItemStack> list = loottable.getRandomItems(builder.create(LootContextParamSets.FISHING));
                for(ItemStack itemstack : list) {
                    ItemEntity itementity = new NoFireItemEntity(this.level, this.getX(), this.getY(), this.getZ(), itemstack);
                    double d0 = player.getX() - this.getX();
                    double d1 = player.getY() - this.getY();
                    double d2 = player.getZ() - this.getZ();
                    double d3 = 0.1D;
                    itementity.setDeltaMovement(d0 * d3, d1 * d3 + Math.sqrt(Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2)) * 0.08D, d2 * d3);
                    this.level.addFreshEntity(itementity);
                    player.level.addFreshEntity(new ExperienceOrb(player.level, player.getX(), player.getY() + 0.5D, player.getZ() + 0.5D, this.random.nextInt(6) + 1));
                    if (itemstack.is(ItemTags.FISHES)) {
                        player.awardStat(Stats.FISH_CAUGHT, 1);
                    }
                }

                i = 1;
            }

            if (this.onGround) {
                i = 2;
            }

            this.discard();
            FishingHookHelper.removeHook(player);
            return i;
        } else {
            return 0;
        }
    }

    public void handleEntityEvent(byte p_37123_) {
        if (p_37123_ == 31 && this.level.isClientSide && this.hookedIn instanceof Player && ((Player)this.hookedIn).isLocalPlayer()) {
            this.pullEntity(this.hookedIn);
        }

        super.handleEntityEvent(p_37123_);
    }

    protected void pullEntity(Entity p_150156_) {
        Entity entity = this.getOwner();
        if (entity != null) {
            Vec3 vec3 = (new Vec3(entity.getX() - this.getX(), entity.getY() - this.getY(), entity.getZ() - this.getZ())).scale(0.1D);
            p_150156_.setDeltaMovement(p_150156_.getDeltaMovement().add(vec3));
        }
    }

    protected Entity.@NotNull MovementEmission getMovementEmission() {
        return Entity.MovementEmission.NONE;
    }

    public void remove(@NotNull RemovalReason p_150146_) {
        this.updateOwnerInfo(null);
        super.remove(p_150146_);
    }

    public void onClientRemoval() {
        this.updateOwnerInfo(null);
    }

    public void setOwner(@Nullable Entity p_150154_) {
        super.setOwner(p_150154_);
        this.updateOwnerInfo(this);
    }

    private void updateOwnerInfo(@Nullable ModFishingHook modFishingHook) {
        Player player = this.getPlayerOwner();
        if (player != null) {
            if (modFishingHook == null) {
                FishingHookHelper.removeHook(player);
            } else {
                FishingHookHelper.setActiveHook(player, modFishingHook);
            }
        }
    }

    @Nullable
    public Player getPlayerOwner() {
        Entity entity = this.getOwner();
        return entity instanceof Player player ? player : null;
    }

    public boolean canChangeDimensions() {
        return false;
    }

    public @NotNull Packet<ClientGamePacketListener> getAddEntityPacket() {
        Entity entity = this.getOwner();
        return new ClientboundAddEntityPacket(this, entity == null ? this.getId() : entity.getId());
    }

    public void recreateFromPacket(@NotNull ClientboundAddEntityPacket p_150150_) {
        super.recreateFromPacket(p_150150_);
        if (this.getPlayerOwner() == null) {
            int i = p_150150_.getData();
            LOGGER.error("Failed to recreate fishing hook on client. {} (id: {}) is not a valid owner.", this.level.getEntity(i), i);
            this.kill();
        }

    }

    enum FishHookState {
        FLYING,
        HOOKED_IN_ENTITY,
        BOBBING
    }

    enum OpenWaterType {
        ABOVE_WATER,
        INSIDE_WATER,
        INVALID
    }
}
