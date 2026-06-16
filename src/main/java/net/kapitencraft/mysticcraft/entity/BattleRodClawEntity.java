package net.kapitencraft.mysticcraft.entity;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BattleRodClawEntity extends Projectile {
    private static final EntityDataAccessor<Boolean> ATTACHED = SynchedEntityData.defineId(BattleRodClawEntity.class, EntityDataSerializers.BOOLEAN);

    @Nullable
    private Entity clawed;
    private State currentState;

    public BattleRodClawEntity(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(ATTACHED, false);
    }

    public boolean isAttached() {
        return this.entityData.get(ATTACHED);
    }

    public @Nullable Entity getClawed() {
        return clawed;
    }

    @Override
    public void tick() {
        switch (this.currentState) {
            case MOVE_TARGET ->
                    this.clawed.setDeltaMovement(this.getOwner().getDeltaMovement().subtract(this.clawed.getDeltaMovement()));
            case MOVE_OWNER ->
                    this.getOwner().setDeltaMovement(this.clawed.getDeltaMovement().subtract(this.getOwner().getDeltaMovement()));
        }

        super.tick();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        this.currentState = State.ATTACHED_ENTITY;
        super.onHitEntity(result);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        this.currentState = State.ATTACHED_BLOCK;
        super.onHitBlock(result);
    }

    public void attemptHook() {
        if (this.currentState == State.ATTACHED_BLOCK) {
            this.currentState = State.MOVE_OWNER;
        } else {

            if ( this.getOwner() instanceof Player player) {
                if (player.isCrouching()) {
                    this.currentState = State.MOVE_TARGET;
                } else
                    this.currentState = State.MOVE_OWNER;
            }
        }
    }

    public enum State implements StringRepresentable {
        ATTACHED_BLOCK,
        ATTACHED_ENTITY,
        MOVE_TARGET,
        MOVE_OWNER,
        REEL,
        FREE;

        @Override
        public @NotNull String getSerializedName() {
            return this.name().toLowerCase();
        }
    }
}
