package net.kapitencraft.mysticcraft.mixin.classes;


import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.client.Rendering;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.misc.damage_source.IAbilitySource;
import net.kapitencraft.mysticcraft.utils.AttributeUtils;
import net.kapitencraft.mysticcraft.utils.MathUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {


    public LivingEntityMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    private LivingEntity own() {
        return (LivingEntity) (Object) this;
    }

    static {
        Rendering.addRenderer(new Rendering.RenderHolder(
                new Vec2(50, 180),
                value -> FormattingCodes.DARK_BLUE + "Protection: " + getDamageProtection(value) + "%",
                Rendering.RenderType.SMALL
        ));
        Rendering.addRenderer(new Rendering.RenderHolder(
                new Vec2(50, 200),
                value -> FormattingCodes.DARK_AQUA + "Effective HP: " + MathUtils.round(value.getHealth() * 100 / (100 - getDamageProtection(value)), 2),
                Rendering.RenderType.SMALL
        ));
    }

    private static double getDamageProtection(LivingEntity living) {
        return MathUtils.round(100 - calculateDamage(100, living.getAttributeValue(Attributes.ARMOR), living.getAttributeValue(Attributes.ARMOR_TOUGHNESS)), 2);
    }

    @Overwrite
    public float getDamageAfterArmorAbsorb(DamageSource source, float damage) {
        if (!source.isBypassArmor()) {
            double armorShredValue = source.getEntity() instanceof LivingEntity living ? AttributeUtils.getSaveAttributeValue(ModAttributes.ARMOR_SHREDDER.get(), living) : 0;
            this.callHurtArmor(source, damage);
            double armorValue = Math.max(0, getArmorValue(source) - armorShredValue);
            return calculateDamage(damage, (float) armorValue, (float) own().getAttributeValue(Attributes.ARMOR_TOUGHNESS));
        }

        return damage;
    }

    private double getArmorValue(DamageSource source) {
        if (source.getMsgId().equals("true_damage")) {
            return own().getAttributeValue(ModAttributes.TRUE_DEFENCE.get());
        } else if (source instanceof IAbilitySource) {
            return own().getAttributeValue(ModAttributes.MAGIC_DEFENCE.get());
        } else {
            return own().getAttributeValue(Attributes.ARMOR);
        }
    }

    private static float calculateDamage(float damage, double armorValue, double armorToughnessValue) {
        double f = MysticcraftMod.DAMAGE_CALCULATION_VALUE - armorToughnessValue / 4.0F;
        double defencePercentage = armorValue / (armorValue + f);
        return (float) (damage * (1f - defencePercentage));
    }

    @Invoker
    abstract void callHurtArmor(DamageSource source, float damage);

    @Inject(method = "hurt", at = @At(value = "RETURN", ordinal = 6))
    public void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> info) {
        if (source.getEntity() != null && source.getEntity() instanceof LivingEntity living) {
            double attackSpeed = living.getAttributeValue(ModAttributes.BONUS_ATTACK_SPEED.get());
            if (attackSpeed > 0) {
                own().invulnerableTime = (int) (20 - (attackSpeed * 0.15));
            }
        }
    }
}