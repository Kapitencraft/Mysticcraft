package net.kapitencraft.mysticcraft.item.misc;

import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.kap_lib.helpers.TextHelper;
import net.kapitencraft.mysticcraft.misc.DamageCounter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ProtectionEnchantment;
import net.minecraft.world.level.EntityBasedExplosionDamageCalculator;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ModExplosion {
    private static final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new ExplosionDamageCalculator();
    protected final Level level;
    protected final @Nullable Entity source;
    protected final Vec3 pos;
    protected final float radius;
    private Explosion self;
    private boolean destroyBlocks = false;
    private boolean hitEntities = false;
    private boolean hasFire = false;
    private Explosion.BlockInteraction interaction;
    @Nullable DamageSource damage = null;
    protected final List<BlockPos> toBlow = new ArrayList<>();
    protected final Map<Player, Vec3> hitPlayers = new HashMap<>();
    protected final ExplosionDamageCalculator calculator;

    private ExplosionDamageCalculator makeDamageCalculator(@javax.annotation.Nullable Entity p_46063_) {
        return p_46063_ == null ? EXPLOSION_DAMAGE_CALCULATOR : new EntityBasedExplosionDamageCalculator(p_46063_);
    }

    private boolean interactsWithBlocks() {
        return destroyBlocks || (blockInteraction() == Explosion.BlockInteraction.DESTROY_WITH_DECAY) || blockInteraction() == Explosion.BlockInteraction.DESTROY;
    }
    Explosion.BlockInteraction blockInteraction() {
        return interaction;
    }


    public void setInteraction(Explosion.BlockInteraction interaction) {
        this.interaction = interaction;
    }

    public ModExplosion destroyBlocks() {
        this.destroyBlocks = true;
        return this;
    }

    public ModExplosion hitEntities() {
        this.hitEntities = true;
        return this;
    }

    public ModExplosion(Level level, @Nullable Entity source, double x, double y, double z, float radius) {
        this.level = level;
        this.source = source;
        this.pos = new Vec3(x, y, z);
        this.radius = radius;
        this.calculator = makeDamageCalculator(source);
    }

    public boolean hasFire() {
        return hasFire;
    }

    public void setDamageSource(DamageSource damage) {
        this.damage = damage;
    }

    public void explode() {
        if (!destroyBlocks) return;//make sure it actually destroyes blocks
        this.level.gameEvent(this.source, GameEvent.EXPLODE, new Vec3(this.pos.x, this.pos.y, this.pos.z));
        Set<BlockPos> set = Sets.newHashSet();

        for(float j = -radius; j < radius; ++j) {
            for(float k = -radius; k < radius; ++k) {
                for(float l = -radius; l < radius; ++l) {
                    Vec3 target = new Vec3(j, k, l).add(pos);
                    if (target.distanceTo(pos) <= radius) {//making explosion only take target's within sphere
                        BlockPos targetPos = new BlockPos((int) j, (int) k, (int) l);
                        BlockState state = this.level.getBlockState(targetPos);
                        FluidState fluidState = this.level.getFluidState(targetPos);
                        float f = this.radius * (0.7F + this.level.random.nextFloat() * 0.6F);
                        Optional<Float> optional = this.calculator.getBlockExplosionResistance(toExplosion(), this.level, targetPos, state, fluidState);
                        if (optional.isPresent()) {
                            f -= (optional.get() + 0.3F) * 0.3F;
                        }

                        if (f > 0.0F && this.calculator.shouldBlockExplode(toExplosion(), this.level, targetPos, state, f)) {
                            set.add(targetPos);
                        }
                    }
                }
            }
        }
        this.toBlow.addAll(set);
    }

    public void dealDamage() {
        if (!hitEntities) return; //make sure the explosion actually hurts entities
        float f2 = this.radius * 2.0F;
        int k1 = (int) (pos.x - (double)f2 - 1.0D);
        int l1 = (int) (pos.x + (double)f2 + 1.0D);
        int i2 = (int) (pos.y - (double)f2 - 1.0D);
        int i1 = (int) (pos.y + (double)f2 + 1.0D);
        int j2 = (int) (pos.z - (double)f2 - 1.0D);
        int j1 = (int) (pos.z + (double)f2 + 1.0D);
        List<Entity> list = this.level.getEntities(this.source, new AABB(k1, i2, j2, l1, i1, j1));
        DamageCounter.activate();
        net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.level, toExplosion(), list, f2);
        Vec3 vec3 = new Vec3(pos.x, pos.y, pos.z);

        for (Entity entity : list) {
            if (!entity.ignoreExplosion()) {
                double d12 = Math.sqrt(entity.distanceToSqr(vec3)) / (double) f2;
                if (d12 <= 1.0D) {
                    double d5 = entity.getX() - pos.x;
                    double d7 = (entity instanceof PrimedTnt ? entity.getY() : entity.getEyeY()) - pos.y;
                    double d9 = entity.getZ() - pos.z;
                    double d13 = Math.sqrt(d5 * d5 + d7 * d7 + d9 * d9);
                    if (d13 != 0.0D) {
                        d5 /= d13;
                        d7 /= d13;
                        d9 /= d13;
                        double d14 = Explosion.getSeenPercent(vec3, entity);
                        double d10 = (1.0D - d12) * d14;

                        entity.hurt(this.damage != null ? this.damage : this.level.damageSources().explosion(toExplosion()), (float) ((int) ((d10 * d10 + d10) / 2.0D * 7.0D * (double) f2 + 1.0D)));
                        double d11 = d10;
                        if (entity instanceof LivingEntity) {
                            d11 = ProtectionEnchantment.getExplosionKnockbackAfterDampener((LivingEntity) entity, d10);
                        }

                        entity.setDeltaMovement(entity.getDeltaMovement().add(d5 * d11, d7 * d11, d9 * d11));
                        if (entity instanceof Player player) {
                            if (!player.isSpectator() && (!player.isCreative() || !player.getAbilities().flying)) {
                                this.hitPlayers.put(player, new Vec3(d5 * d10, d7 * d10, d9 * d10));
                            }
                        }
                    }
                }
            }
        }
    }

    @javax.annotation.Nullable
    public LivingEntity getIndirectSourceEntity() {
        if (this.source == null) {
            return null;
        } else {
            Entity entity = this.source;
            if (entity instanceof PrimedTnt primedtnt) {
                return primedtnt.getOwner();
            } else {
                if (entity instanceof LivingEntity living) {
                    return living;
                } else {
                    if (entity instanceof Projectile projectile) {
                        entity = projectile.getOwner();
                        if (entity instanceof LivingEntity living) {
                            return living;
                        }
                    }

                    return null;
                }
            }
        }
    }

    public void completeExplode(String name, boolean withParticle) {
        if (this.level.isClientSide()) return;
        explode();
        finalizeExplosion(withParticle);
        dealDamage();
        sendToClient(name);
    }

    protected boolean shouldSendDamageValue() {
        return false;
    }

    private void sendToClient(String name) {
        if (this.level instanceof ServerLevel serverLevel) {
            serverLevel.getPlayers(p -> true)
                    .forEach(serverPlayer ->
                            serverPlayer.connection.send(new ClientboundExplodePacket(pos.x, pos.y, pos.z,
                                    this.radius,
                                    this.toBlow,
                                    this.hitPlayers.get(serverPlayer)
                            ))
                    );
        }
        if (shouldSendDamageValue() && this.source instanceof Player player) {
            DamageCounter.DamageHolder holder = DamageCounter.getDamage(true);
            player.sendSystemMessage(Component.translatable("damage_notification", name, TextHelper.wrapInRed(holder.hit()), MathHelper.defRound(holder.damage())));
        }
    }

    public void finalizeExplosion(boolean withParticles) {
        if (this.level.isClientSide) {
            this.level.playLocalSound(pos.x, pos.y, pos.z, SoundEvents.GENERIC_EXPLODE, SoundSource.BLOCKS, 4.0F, (1.0F + (this.level.random.nextFloat() - this.level.random.nextFloat()) * 0.2F) * 0.7F, false);
        }

        boolean flag = this.interactsWithBlocks();
        if (withParticles) {
            if (!(this.radius < 2.0F) && flag) {
                this.level.addParticle(ParticleTypes.EXPLOSION_EMITTER, pos.x, pos.y, pos.z, 1.0D, 0.0D, 0.0D);
            } else {
                this.level.addParticle(ParticleTypes.EXPLOSION, pos.x, pos.y, pos.z, 1.0D, 0.0D, 0.0D);
            }
        }

        if (flag) {
            boolean flag1 = this.getIndirectSourceEntity() instanceof Player;
            ObjectArrayList<Pair<ItemStack, BlockPos>> dropsForPos = new ObjectArrayList<>();
            for (BlockPos blockpos : this.toBlow) {
                BlockState blockstate = this.level.getBlockState(blockpos);
                if (!blockstate.isAir()) {
                    this.level.getProfiler().push("explosion_blocks");
                    if (blockstate.canDropFromExplosion(this.level, blockpos, toExplosion())) {
                        if (this.level instanceof ServerLevel serverlevel) {
                            BlockEntity blockentity = blockstate.hasBlockEntity() ? this.level.getBlockEntity(blockpos) : null;
                            LootContext.Builder builder = (new LootContext.Builder(serverlevel)).withRandom(this.level.random).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockpos)).withParameter(LootContextParams.TOOL, ItemStack.EMPTY).withOptionalParameter(LootContextParams.BLOCK_ENTITY, blockentity).withOptionalParameter(LootContextParams.THIS_ENTITY, this.source);
                            if (blockInteraction() == Explosion.BlockInteraction.DESTROY_WITH_DECAY) {
                                builder.withParameter(LootContextParams.EXPLOSION_RADIUS, this.radius);
                            }

                            blockstate.spawnAfterBreak(serverlevel, blockpos, ItemStack.EMPTY, flag1);
                            blockstate.getDrops(builder).forEach(stack ->
                                    Explosion.addBlockDrops(dropsForPos, stack, blockpos)
                            );
                        }
                    }

                    blockstate.onBlockExploded(this.level, blockpos, toExplosion());
                    this.level.getProfiler().pop();
                }
            }
            for(Pair<ItemStack, BlockPos> pair : dropsForPos) {
                Block.popResource(this.level, pair.getSecond(), pair.getFirst());
            }

        }

        if (this.hasFire()) {
            for (BlockPos blockPos : this.toBlow) {
                if (this.level.random.nextInt(3) == 0 && this.level.getBlockState(blockPos).isAir() && this.level.getBlockState(blockPos.below()).isSolidRender(this.level, blockPos.below())) {
                    this.level.setBlockAndUpdate(blockPos, BaseFireBlock.getState(this.level, blockPos));
                }
            }
        }
    }

    private Explosion toExplosion() {
        if (self == null) self = new Explosion(level, this.source, this.pos.x, this.pos.y, this.pos.z, this.radius, this.hasFire(), this.blockInteraction());
        return self;
    }
}
