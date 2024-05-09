package net.kapitencraft.mysticcraft.mixin.classes;


import net.kapitencraft.mysticcraft.cooldown.Cooldown;
import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.misc.cooldown.ICooldownable;
import net.kapitencraft.mysticcraft.misc.damage_source.IAbilitySource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ICooldownable {
    private final List<Cooldown> cooldowns = new ArrayList<>();


    public LivingEntityMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    private LivingEntity own() {
        return (LivingEntity) (Object) this;
    }

    /**
     * @reason armor-shredder attribute
     * @author Kapitencraft
     */
    @Inject(method = "getDamageAfterArmorAbsorb", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurtArmor(Lnet/minecraft/world/damagesource/DamageSource;F)V", shift = At.Shift.AFTER), cancellable = true)
    public void getDamageAfterArmorAbsorb(DamageSource source, float damage, CallbackInfoReturnable<Float> cir) {
        double armorShredValue = source.getEntity() instanceof LivingEntity living ? AttributeHelper.getSaveAttributeValue(ModAttributes.ARMOR_SHREDDER.get(), living) : 0;
        double armorValue = Math.max(0, getArmorValue(source) - armorShredValue);
        cir.setReturnValue(MathHelper.calculateDamage(damage, (float) armorValue, (float) own().getAttributeValue(Attributes.ARMOR_TOUGHNESS)));
    }

    /**
     * @reason negative kb
     * @author Kapitencraft
     */
    @Overwrite
    public void knockback(double strenght, double xSpeed, double ySpeed) {
        LivingKnockBackEvent event = ForgeHooks.onLivingKnockBack(own(), (float) strenght, xSpeed, ySpeed);
        if(event.isCanceled()) return;
        strenght = event.getStrength();
        xSpeed = event.getRatioX();
        ySpeed = event.getRatioZ();
        double kbResistance = own().getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
        double reducePercentage = kbResistance / (100 + kbResistance);
        strenght *= 1.0D - Math.min(1, reducePercentage);
        this.hasImpulse = true;
        Vec3 vec3 = this.getDeltaMovement();
        Vec3 vec31 = (new Vec3(xSpeed, 0.0D, ySpeed)).normalize().scale(strenght);
        this.setDeltaMovement(vec3.x / 2.0D - vec31.x, this.onGround ? Math.min(0.4D, vec3.y / 2.0D + strenght) : vec3.y, vec3.z / 2.0D - vec31.z);
    }

    private double getArmorValue(DamageSource source) {
        if (source.getMsgId().equals("true_damage")) {
            return AttributeHelper.getSaveAttributeValue(ModAttributes.TRUE_DEFENCE.get(), own());
        } else if (source instanceof IAbilitySource) {
            return AttributeHelper.getSaveAttributeValue(ModAttributes.MAGIC_DEFENCE.get(), own());
        } else {
            return AttributeHelper.getSaveAttributeValue(Attributes.ARMOR, own());
        }
    }

    @Inject(method = "hurt", at = @At(value = "RETURN", ordinal = 6))
    public void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        if (source.getEntity() != null && source.getEntity() instanceof LivingEntity living) {
            double attackSpeed = AttributeHelper.getSaveAttributeValue(ModAttributes.BONUS_ATTACK_SPEED.get(), living);
            if (attackSpeed > 0) {
                own().invulnerableTime = (int) (20 - (attackSpeed * 0.15));
            }
        }
    }

    @Override
    public @NotNull List<Cooldown> getActiveCooldowns() {
        return cooldowns;
    }

    @Override
    public LivingEntity self() {
        return own();
    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(CallbackInfo ci) {
        tickCooldowns();
    }

    @ModifyConstant(method = "tryAddFrost")
    private float increaseFreezeSlowness(float in) {
        return MiscHelper.forDifficulty(self().getLevel().getDifficulty(), in * 1.5f, in * 2.5f, in * 4f, in);
    }
}