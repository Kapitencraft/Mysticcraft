package net.kapitencraft.mysticcraft.item.misc;

import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.TextHelper;
import net.kapitencraft.mysticcraft.misc.DamageCounter;
import net.kapitencraft.mysticcraft.networking.ModMessages;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundExplodePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
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
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class ModExplosion {
    private static final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new ExplosionDamageCalculator();
    protected final Level level;
    protected final @Nullable Entity source;
    protected final Vec3 pos;
    protected final float radius;
    protected final List<BlockPos> toBlow = new ArrayList<>();
    protected final Map<Player, Vec3> hitPlayers = new HashMap<>();
    protected final ExplosionDamageCalculator calculator;

    private ExplosionDamageCalculator makeDamageCalculator(@javax.annotation.Nullable Entity p_46063_) {
        return p_46063_ == null ? EXPLOSION_DAMAGE_CALCULATOR : new EntityBasedExplosionDamageCalculator(p_46063_);
    }

    private boolean interactsWithBlocks() {
        return (blockInteraction() == Explosion.BlockInteraction.DESTROY_WITH_DECAY) || blockInteraction() == Explosion.BlockInteraction.DESTROY;
    }
    abstract Explosion.BlockInteraction blockInteraction();


    public ModExplosion(Level level, @Nullable Entity source, double x, double y, double z, float radius) {
        this.level = level;
        this.source = source;
        this.pos = new Vec3(x, y, z);
        this.radius = radius;
        this.calculator = makeDamageCalculator(source);
    }

    abstract boolean hasFire();
    abstract DamageSource getDamageSource();

    public void explode() {
        Set<BlockPos> set = Sets.newHashSet();

        for(int j = 0; j < 16; ++j) {
            for(int k = 0; k < 16; ++k) {
                for(int l = 0; l < 16; ++l) {
                    if (j == 0 || j == 15 || k == 0 || k == 15 || l == 0 || l == 15) {
                        double d0 = (float)j / 15.0F * 2.0F - 1.0F;
                        double d1 = (float)k / 15.0F * 2.0F - 1.0F;
                        double d2 = (float)l / 15.0F * 2.0F - 1.0F;
                        double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                        d0 /= d3;
                        d1 /= d3;
                        d2 /= d3;
                        float f = this.radius * (0.7F + this.level.random.nextFloat() * 0.6F);
                        double d4 = this.pos.x;
                        double d6 = this.pos.y;
                        double d8 = this.pos.z;

                        for(float f1 = 0.3F; f > 0.0F; f -= 0.22500001F) {
                            BlockPos blockpos = new BlockPos(d4, d6, d8);
                            BlockState blockstate = this.level.getBlockState(blockpos);
                            FluidState fluidstate = this.level.getFluidState(blockpos);
                            if (!this.level.isInWorldBounds(blockpos)) {
                                break;
                            }

                            Optional<Float> optional = this.calculator.getBlockExplosionResistance(toExplosion(), this.level, blockpos, blockstate, fluidstate);
                            if (optional.isPresent()) {
                                f -= (optional.get() + 0.3F) * 0.3F;
                            }

                            if (f > 0.0F && this.calculator.shouldBlockExplode(toExplosion(), this.level, blockpos, blockstate, f)) {
                                set.add(blockpos);
                            }

                            d4 += d0 * (double)0.3F;
                            d6 += d1 * (double)0.3F;
                            d8 += d2 * (double)0.3F;
                        }
                    }
                }
            }
        }
        this.toBlow.addAll(set);
    }

    public void dealDamage() {
        float f2 = this.radius * 2.0F;
        int k1 = Mth.floor(pos.x - (double)f2 - 1.0D);
        int l1 = Mth.floor(pos.x + (double)f2 + 1.0D);
        int i2 = Mth.floor(pos.y - (double)f2 - 1.0D);
        int i1 = Mth.floor(pos.y + (double)f2 + 1.0D);
        int j2 = Mth.floor(pos.z - (double)f2 - 1.0D);
        int j1 = Mth.floor(pos.z + (double)f2 + 1.0D);
        List<Entity> list = this.level.getEntities(this.source, new AABB(k1, i2, j2, l1, i1, j1));
        net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.level, toExplosion(), list, f2);
        Vec3 vec3 = new Vec3(pos.x, pos.y, pos.z);

        for(int k2 = 0; k2 < list.size(); ++k2) {
            Entity entity = list.get(k2);
            if (!entity.ignoreExplosion()) {
                double d12 = Math.sqrt(entity.distanceToSqr(vec3)) / (double)f2;
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
                        entity.hurt(this.getDamageSource(), (float)((int)((d10 * d10 + d10) / 2.0D * 7.0D * (double)f2 + 1.0D)));
                        double d11 = d10;
                        if (entity instanceof LivingEntity) {
                            d11 = ProtectionEnchantment.getExplosionKnockbackAfterDampener((LivingEntity)entity, d10);
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

    public void completeExplode(String name) {
        explode();
        finalizeExplosion(false);
        sendToClient(name);
    }

    protected abstract boolean shouldSendDamageValue();

    private void sendToClient(String name) {
        if (this.level instanceof ServerLevel serverLevel) {
            ModMessages.sendToAllConnectedPlayers(value -> new ClientboundExplodePacket(pos.x, pos.y, pos.z, this.radius, this.toBlow, this.hitPlayers.get(value)), serverLevel);
        }
        if (shouldSendDamageValue() && this.source instanceof Player player) {
            DamageCounter.DamageHolder holder = DamageCounter.getDamage(true);
            player.sendSystemMessage(Component.literal("Your " + name + " hit " + TextHelper.wrapInRed(holder.hit()) + " Enemies for §c" + MathHelper.defRound(holder.damage()) + " Damage"));
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
            ObjectArrayList<Pair<ItemStack, BlockPos>> objectarraylist = new ObjectArrayList<>();
            boolean flag1 = this.getIndirectSourceEntity() instanceof Player;

            for(BlockPos blockpos : this.toBlow) {
                BlockState blockstate = this.level.getBlockState(blockpos);
                if (!blockstate.isAir()) {
                    BlockPos blockpos1 = blockpos.immutable();
                    this.level.getProfiler().push("explosion_blocks");
                    if (blockstate.canDropFromExplosion(this.level, blockpos, toExplosion())) {
                        Level $$9 = this.level;
                        if ($$9 instanceof ServerLevel) {
                            ServerLevel serverlevel = (ServerLevel)$$9;
                            BlockEntity blockentity = blockstate.hasBlockEntity() ? this.level.getBlockEntity(blockpos) : null;
                            LootContext.Builder lootcontext$builder = (new LootContext.Builder(serverlevel)).withRandom(this.level.random).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(blockpos)).withParameter(LootContextParams.TOOL, ItemStack.EMPTY).withOptionalParameter(LootContextParams.BLOCK_ENTITY, blockentity).withOptionalParameter(LootContextParams.THIS_ENTITY, this.source);
                            if (blockInteraction() == Explosion.BlockInteraction.DESTROY_WITH_DECAY) {
                                lootcontext$builder.withParameter(LootContextParams.EXPLOSION_RADIUS, this.radius);
                            }

                            blockstate.spawnAfterBreak(serverlevel, blockpos, ItemStack.EMPTY, flag1);
                            blockstate.getDrops(lootcontext$builder).forEach((p_46074_) -> {
                                addBlockDrops(objectarraylist, p_46074_, blockpos1);
                            });
                        }
                    }

                    blockstate.onBlockExploded(this.level, blockpos, toExplosion());
                    this.level.getProfiler().pop();
                }
            }

            for(Pair<ItemStack, BlockPos> pair : objectarraylist) {
                Block.popResource(this.level, pair.getSecond(), pair.getFirst());
            }
        }

        if (this.hasFire()) {
            for(BlockPos blockpos2 : this.toBlow) {
                if (this.level.random.nextInt(3) == 0 && this.level.getBlockState(blockpos2).isAir() && this.level.getBlockState(blockpos2.below()).isSolidRender(this.level, blockpos2.below())) {
                    this.level.setBlockAndUpdate(blockpos2, BaseFireBlock.getState(this.level, blockpos2));
                }
            }
        }
    }

    private static void addBlockDrops(ObjectArrayList<Pair<ItemStack, BlockPos>> p_46068_, ItemStack p_46069_, BlockPos p_46070_) {
        int i = p_46068_.size();

        for(int j = 0; j < i; ++j) {
            Pair<ItemStack, BlockPos> pair = p_46068_.get(j);
            ItemStack itemstack = pair.getFirst();
            if (ItemEntity.areMergable(itemstack, p_46069_)) {
                ItemStack stack = ItemEntity.merge(itemstack, p_46069_, 16);
                p_46068_.set(j, Pair.of(stack, pair.getSecond()));
                if (p_46069_.isEmpty()) {
                    return;
                }
            }
        }

        p_46068_.add(Pair.of(p_46069_, p_46070_));
    }


    private Explosion toExplosion() {
        return new Explosion(level, this.source, this.pos.x, this.pos.y, this.pos.z, this.radius, this.hasFire(), this.blockInteraction());
    }
}
