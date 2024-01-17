package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

import static net.kapitencraft.mysticcraft.misc.MiscRegister.OVERFLOW_MANA_ID;

@Mod.EventBusSubscriber()
public class ManaMain {

    @SubscribeEvent
    public static void manaChange(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        AttributeInstance maxManaInstance = player.getAttribute(ModAttributes.MAX_MANA.get());
        AttributeInstance manaInstance = player.getAttribute(ModAttributes.MANA.get());
        if (maxManaInstance == null || manaInstance == null) {
            return;
        }
        double maxMana = maxManaInstance.getValue();
        double mana = manaInstance.getBaseValue();
        double intel = player.getAttributeValue(ModAttributes.INTELLIGENCE.get());
        double curManaRegen = player.getAttributeValue(ModAttributes.MANA_REGEN.get());
        double manaRegen = maxMana / 500 * (1 + curManaRegen / 100);
        CompoundTag tag = player.getPersistentData();
        tag.putDouble("manaRegen", manaRegen);
        if (AttributeHelper.getSaveAttributeValue(ModAttributes.MANA.get(), player) != -1 && player.level.getBlockState(new BlockPos(player.getX(), player.getY(), player.getZ())).getBlock() == ModBlocks.MANA_FLUID_BLOCK.get()) {
            double overflowMana = getOverflow(player) + manaRegen;
            tag.putDouble(OVERFLOW_MANA_ID, overflowMana);
            if (overflowMana >= AttributeHelper.getSaveAttributeValue(ModAttributes.MAX_MANA.get(), player)) {
                player.hurt(new DamageSource(OVERFLOW_MANA_ID).bypassInvul().bypassMagic().bypassEnchantments().bypassArmor(), Float.MAX_VALUE);
                List<LivingEntity> livings = MathHelper.getLivingAround(player, 5);
                for (LivingEntity living1 : livings) {
                    living1.hurt(new EntityDamageSource(OVERFLOW_MANA_ID, player).bypassArmor().bypassMagic().bypassEnchantments().bypassInvul(), (float) (Float.MAX_VALUE * (0.01 * Math.max(5 - living1.distanceTo(player), 0))));
                }
            }
        }
        if (mana < maxMana) {
             mana += manaRegen;
        }
        if (mana > maxMana) {
            mana = maxMana;
        }
        maxMana = 100 + intel;

        manaInstance.setBaseValue(mana);
        maxManaInstance.setBaseValue(maxMana);
    }

    public static boolean consumeMana(LivingEntity living, double manaToConsume) {
        if (manaToConsume <= 0) return true;
        double mana = getMana(living);
        double overflow = getOverflow(living);
        if (mana + overflow < manaToConsume) {
            return false;
        }
        if (overflow > manaToConsume) {
            overflow -= manaToConsume;
            manaToConsume = 0;
        } else {
            manaToConsume -= overflow;
            overflow = 0;
        }
        if (manaToConsume > 0) {
            mana -= manaToConsume;
        }
        setOverflow(living, overflow);
        setMana(living, mana);
        return true;
    }

    public static double getOverflow(LivingEntity living) {
        return living.getPersistentData().getDouble(OVERFLOW_MANA_ID);
    }

    public static double getMana(LivingEntity living) {
        return AttributeHelper.getSaveAttributeValue(ModAttributes.MANA.get(), living);
    }

    public static void setMana(LivingEntity living, double mana) {
        AttributeInstance instance = living.getAttribute(ModAttributes.MANA.get());
        if (instance != null) {
            instance.setBaseValue(mana);
        }
    }

    public static void setOverflow(LivingEntity living, double overflow) {
        living.getPersistentData().putDouble(OVERFLOW_MANA_ID, overflow);
    }
}
