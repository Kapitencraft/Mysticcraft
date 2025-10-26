package net.kapitencraft.mysticcraft.mixin.classes;


import net.kapitencraft.mysticcraft.item.combat.weapon.melee.sword.ModSwordItem;
import net.kapitencraft.mysticcraft.mixin.duck.IAttacker;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements IAttacker {

    @Shadow public abstract boolean hurt(DamageSource pSource, float pAmount);

    private PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Override
    public boolean shouldShowName() {
        return !this.isInvisible();
    }

    public Player own() {
        return (Player) (Object) this;
    }

    @Redirect(method = "sweepAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerLevel;sendParticles(Lnet/minecraft/core/particles/ParticleOptions;DDDIDDDD)I"))
    private int sendParticles(ServerLevel level, ParticleOptions options, double d, double d1, double d2, int i, double d3, double d4, double d5, double d6) {
        ItemStack mainHand = own().getMainHandItem();
        if (mainHand.getItem() instanceof ModSwordItem modSwordItem) {
            return level.sendParticles(modSwordItem.getSweepParticle(mainHand), d, d1, d2, i, d3, d4, d5, d6);
        }
        return level.sendParticles(ParticleTypes.SWEEP_ATTACK, d, d1, d2, i, d3, d4, d5, d6);
    }

    //region IAttacker

    @Unique
    private boolean offhandAttack;

    @Override
    public void setMainhandAttack() {
        offhandAttack = false;
    }

    @Override
    public void setOffhandAttack() {
        offhandAttack = true;
    }

    @Override
    public boolean isOffhandAttack() {
        return offhandAttack;
    }

    //endregion IAttacker
}