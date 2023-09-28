package net.kapitencraft.mysticcraft.item.misc;

import com.google.common.collect.Sets;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.EntityBasedExplosionDamageCalculator;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public abstract class ModExplosion {
    private static final ExplosionDamageCalculator EXPLOSION_DAMAGE_CALCULATOR = new ExplosionDamageCalculator();
    private final Level level;
    private final @Nullable Entity source;
    private final Vec3 pos;
    private final float radius;
    private final List<BlockPos> toBlow = new ArrayList<>();
    private final ExplosionDamageCalculator calculator;

    private ExplosionDamageCalculator makeDamageCalculator(@javax.annotation.Nullable Entity p_46063_) {
        return p_46063_ == null ? EXPLOSION_DAMAGE_CALCULATOR : new EntityBasedExplosionDamageCalculator(p_46063_);
    }


    public ModExplosion(Level level, @Nullable Entity source, double x, double y, double z, float radius) {
        this.level = level;
        this.source = source;
        this.pos = new Vec3(x, y, z);
        this.radius = radius;
        this.calculator = makeDamageCalculator(source);
    }

    abstract boolean hasFire();

    public void u() {
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

    private Explosion toExplosion() {
        return new Explosion(level, this.source, this.pos.x, this.pos.y, this.pos.z, this.radius, this.hasFire(), Explosion.BlockInteraction.KEEP);
    }
}
