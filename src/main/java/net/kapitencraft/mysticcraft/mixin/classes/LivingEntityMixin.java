package net.kapitencraft.mysticcraft.mixin.classes;


import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.misc.utils.AttributeUtils;
import net.kapitencraft.mysticcraft.mixin.ILivingEntityMixin;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ILivingEntityMixin {


    public LivingEntityMixin(EntityType<?> p_19870_, Level p_19871_) {
        super(p_19870_, p_19871_);
    }

    private LivingEntity instance() {
        return (LivingEntity) (Object) this;
    }

    @Override
    public float getDamageAfterArmorAbsorb(DamageSource source, float value) {
        if (!source.isBypassArmor()) {
            double armorShredValue = source.getEntity() instanceof LivingEntity living ? AttributeUtils.getSaveAttributeValue(ModAttributes.ARMOR_SHREDDER.get(), living) : 0;
            this.callHurtArmor(source, value);
            value = CombatRules.getDamageAfterAbsorb(value, (float) Math.max(0, instance().getArmorValue() - armorShredValue), (float)instance().getAttributeValue(Attributes.ARMOR_TOUGHNESS));
        }

        return value;
    }

    @Invoker
    abstract void callHurtArmor(DamageSource source, float damage);
}
