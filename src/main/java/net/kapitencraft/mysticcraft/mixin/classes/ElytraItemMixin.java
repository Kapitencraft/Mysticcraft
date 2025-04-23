package net.kapitencraft.mysticcraft.mixin.classes;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.elytra.ElytraData;
import net.kapitencraft.mysticcraft.misc.content.mana.ManaMain;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ElytraItem.class)
public abstract class ElytraItemMixin extends Item {

    @Shadow public abstract boolean isValidRepairItem(ItemStack p_41134_, ItemStack p_41135_);

    public ElytraItemMixin(Properties p_41383_) {
        super(p_41383_);
    }

    /**
     * @author Kapitencraft
     * @reason elytra data :pog:
     */

    @Inject(method = "elytraFlightTick", at = @At("RETURN"), remap = false)
    private void elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks, CallbackInfoReturnable<Boolean> cir) {
        CapabilityHelper.exeCapability(stack, CapabilityHelper.ELYTRA, data -> {
            int manaBoost = data.getLevelForData(ElytraData.MANA_BOOST);
            if (manaBoost > 0) {
                if (ManaMain.consumeMana(entity, 1.5)) {
                    entity.setDeltaMovement(getFireworkSpeedBoost(entity, manaBoost));
                    if (entity.level().isClientSide())
                        MiscHelper.sendManaBoostParticles(entity, entity.getRandom(), entity.getDeltaMovement());
                }
            }
        });
    }

    @Unique
    private static Vec3 getFireworkSpeedBoost(LivingEntity living, int scale) {
        Vec3 sourceLookAngle = living.getLookAngle();
        Vec3 sourceSpeed = living.getDeltaMovement();
        double d1 = 0.1;
        double d2 = 1.5;
        double d3 = 0.5;
        if (scale > 0) {
            d1 *= scale;
            d3 *= scale / 5.;
        }
        return sourceSpeed.add(sourceLookAngle.x * d1 + (sourceLookAngle.x * d2 - sourceSpeed.x) * d3, sourceLookAngle.y * d1 + (sourceLookAngle.y * d2 - sourceSpeed.y) * d3, sourceLookAngle.z * d1 + (sourceLookAngle.z * d2 - sourceSpeed.z) * d3);
    }
}
