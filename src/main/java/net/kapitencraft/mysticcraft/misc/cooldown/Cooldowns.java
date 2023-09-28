package net.kapitencraft.mysticcraft.misc.cooldown;

import net.kapitencraft.mysticcraft.enchantments.armor.BonkEnchantment;
import net.kapitencraft.mysticcraft.enchantments.weapon.melee.VenomousEnchantment;
import net.kapitencraft.mysticcraft.misc.HealingHelper;
import net.kapitencraft.mysticcraft.spell.spells.WitherShieldSpell;
import net.kapitencraft.mysticcraft.utils.TagUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;

import java.util.ArrayList;
import java.util.List;

public interface Cooldowns {
    List<Cooldown> cooldowns = new ArrayList<>();

    Cooldown DAMAGE_INDICATOR = new Cooldown(new CompoundPath.Builder("time").withUseAbles(entity -> {
            CompoundTag tag = entity.getPersistentData();
            return entity instanceof ArmorStand && tag.getBoolean("isDamageIndicator");
        }).build(), 35, Entity::kill);
    Cooldown VENOMOUS = new Cooldown(new CompoundPath.Builder("VenomousTimer").build(), 100, living ->  {
            AttributeInstance speed = living.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speed != null) {
                speed.removeModifier(VenomousEnchantment.ID);
            }
        });
    Cooldown WITHER_SHIELD = new Cooldown(new CompoundPath.Builder(WitherShieldSpell.DAMAGE_REDUCTION_TIME).build(), 100, living -> {
            CompoundTag tag = living.getPersistentData();
            float absorption = tag.getFloat(WitherShieldSpell.ABSORPTION_AMOUNT_ID);
            HealingHelper.setEffectReason(living);
            living.heal(absorption / 2);
            living.setAbsorptionAmount(living.getAbsorptionAmount() - absorption);
            tag.putFloat(WitherShieldSpell.ABSORPTION_AMOUNT_ID, 0);
        });
    static Cooldown BONK_ENCHANTMENT(EquipmentSlot slot) {
        CompoundPath path = new CompoundPath.Builder(slot.getName() + ".cooldown").withParent(BonkEnchantment.BONK_ID, builder -> {}).build();
        return new Cooldown(path, 1200, living -> {
            CompoundTag tag = path.getParent().getTag(living);
            if (tag != null) tag.putBoolean(slot.getName(), true);
        });
    }

    static void addCooldown(Cooldown cooldown) {
        cooldowns.add(cooldown);
    }

    static void tickCooldowns(LivingEntity entity) {
        for (Cooldown cooldown : cooldowns) {
            CompoundPath path = cooldown.getPath().getParent();
            CompoundTag tag = path.getTag(entity);
            if (tag != null) {
                TagUtils.reduceBy1(tag, cooldown.getPath().getPath());
            }
        }
    }
}