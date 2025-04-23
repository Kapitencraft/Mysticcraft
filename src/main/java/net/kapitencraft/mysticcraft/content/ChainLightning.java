package net.kapitencraft.mysticcraft.content;

import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.mysticcraft.data_gen.ModDamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ChainLightning {
    private LivingEntity target;
    private LivingEntity beamSource;
    private final LivingEntity owner;
    private final Level level;
    private int tickCooldown;
    private final float damage;

    private boolean canceled = false;

    public ChainLightning(LivingEntity target, LivingEntity owner, float damage) {
        this.target = target;
        this.owner = owner;
        this.damage = damage;
        this.beamSource = owner;
        this.level = owner.level();
    }

    public boolean tick() {
        if (this.tickCooldown-- <= 0) {
            target.hurt(target.damageSources().source(ModDamageTypes.CHAIN_LIGHTNING, owner), damage);
            updateTarget();
        }
        if (this.level.isClientSide()) {
            //TODO move to render thread
            //ClientHelper.renderBeam(beamSourceLocation(), this.target, (int) (255 * tickPercentage()), (int) (255 * tickPercentage()), 255, new PoseStack(), Minecraft.getInstance().renderBuffers().bufferSource());
        }
        return this.canceled;
    }

    private float tickPercentage() {
        return (10 - this.tickCooldown) / 10f;
    }

    private Vec3 beamSourceLocation() {
        return this.beamSource.position().add(this.beamSource.getBbWidth() / 2, this.beamSource.getBbHeight() / 2, this.beamSource.getBbWidth() / 2);
    }

    private void updateCooldown() {
        tickCooldown = 10;
    }

    private void updateTarget() {
        LivingEntity newTarget = MathHelper.getClosestLiving(target, 5);
        if (newTarget != null && newTarget != target) {
            beamSource = target;
            target = newTarget;
        } else {
            canceled = true;
        }
        updateCooldown();
    }
}
