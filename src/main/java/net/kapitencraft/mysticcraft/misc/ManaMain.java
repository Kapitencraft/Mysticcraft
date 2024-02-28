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

    @SuppressWarnings("all")
    @SubscribeEvent
    public static void manaChange(TickEvent.PlayerTickEvent event) {
        Player player = event.player;
        AttributeInstance maxManaInstance = player.getAttribute(ModAttributes.MAX_MANA.get());
        if (!isMagical(player)) {
            throw new IllegalStateException("detected Player unable to use mana, expecting broken mod-state!");
        }
        double maxMana = maxManaInstance.getValue();
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
        growMana(player, manaRegen);
        maxMana = 100 + intel;

        maxManaInstance.setBaseValue(maxMana);
    }

    public static boolean consumeMana(LivingEntity living, double manaToConsume) {
        if (!hasMana(living, manaToConsume)) return false;
        double mana = getMana(living);
        double overflow = getOverflow(living);
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

    public static boolean hasMana(LivingEntity living, double manaToConsume) {
        if (manaToConsume <= 0) return true;
        if (!isMagical(living)) return false;
        double mana = getMana(living);
        double overflow = getOverflow(living);
        return mana + overflow >= manaToConsume;
    }

    public static double getOverflow(LivingEntity living) {
        return isMagical(living) ? living.getPersistentData().getDouble(OVERFLOW_MANA_ID) : 0;
    }

    public static double getMana(LivingEntity living) {
        return AttributeHelper.getSaveAttributeValue(ModAttributes.MANA.get(), living);
    }

    public static void setMana(LivingEntity living, double mana) {
        AttributeInstance instance = living.getAttribute(ModAttributes.MANA.get());
        if (instance != null) {
            instance.setBaseValue(Math.min(mana, living.getAttributeValue(ModAttributes.MAX_MANA.get())));
        }
    }

    public static boolean growMana(LivingEntity living, double mana) {
        setMana(living, getMana(living) + mana);
        return isMagical(living);
    }

    public static boolean isMagical(LivingEntity living) {
        return living.getAttribute(ModAttributes.MANA.get()) != null && living.getAttribute(ModAttributes.MAX_MANA.get()) != null;
    }

    public static void setOverflow(LivingEntity living, double overflow) {
        living.getPersistentData().putDouble(OVERFLOW_MANA_ID, overflow);
    }
}
