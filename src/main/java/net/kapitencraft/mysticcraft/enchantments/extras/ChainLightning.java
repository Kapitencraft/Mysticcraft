package net.kapitencraft.mysticcraft.enchantments.extras;

import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;

public class ChainLightning {
    private LivingEntity target;
    private final LivingEntity owner;
    private int tickCooldown;
    private final float damage;

    private boolean canceled = false;

    public ChainLightning(LivingEntity target, LivingEntity owner, float damage) {
        this.target = target;
        this.owner = owner;
        this.damage = damage;
    }

    public void tick() {
        if (this.tickCooldown-- <= 0 && !canceled) {
            target.hurt(new EntityDamageSource("chain_lightning", owner), damage);
            updateTarget();
        }
    }

    private void updateCooldown() {
        tickCooldown = 10;
    }

    private void updateTarget() {
        LivingEntity newTarget = MathHelper.getClosestLiving(target, 5);
        if (newTarget != null) {
            target = newTarget;
        } else {
            canceled = true;
        }
        updateCooldown();
    }
}
