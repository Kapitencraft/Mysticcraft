package net.kapitencraft.mysticcraft.misc.cooldown;

import net.kapitencraft.mysticcraft.enchantments.armor.BonkEnchantment;
import net.kapitencraft.mysticcraft.helpers.TagHelper;
import net.kapitencraft.mysticcraft.item.item_bonus.fullset.CrimsonArmorFullSetBonus;
import net.kapitencraft.mysticcraft.misc.HealingHelper;
import net.kapitencraft.mysticcraft.spell.spells.WitherShieldSpell;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public interface Cooldowns {
    List<Cooldown> cooldowns = new ArrayList<>();
    Cooldown WITHER_SHIELD = new Cooldown(CompoundPath.builder(WitherShieldSpell.DAMAGE_REDUCTION_TIME).build(), 100, living -> {
        CompoundTag tag = living.getPersistentData();
        float absorption = tag.getFloat(WitherShieldSpell.ABSORPTION_AMOUNT_ID);
        HealingHelper.setEffectReason(living);
        living.heal(absorption / 2);
        living.setAbsorptionAmount(living.getAbsorptionAmount() - absorption);
        tag.putFloat(WitherShieldSpell.ABSORPTION_AMOUNT_ID, 0);
    });
    Cooldown DOMINUS = new Cooldown(CompoundPath.builder(CrimsonArmorFullSetBonus.COOLDOWN_ID).build(), 120, living -> {
        TagHelper.reduceBy1(living.getPersistentData(), CrimsonArmorFullSetBonus.DOMINUS_ID);
        //Cooldowns.DOMINUS.applyCooldown(living, false);
    });
    static Cooldown BONK_ENCHANTMENT(EquipmentSlot slot) {
        CompoundPath path = CompoundPath.builder(slot.getName() + ".cooldown").withParent(BonkEnchantment.BONK_ID, builder -> {}).build();
        return new Cooldown(path, 1200, living -> {
            CompoundTag tag = path.getParent().getTag(living);
            if (tag != null) tag.putBoolean(slot.getName(), true);
        });
    }

    static void addCooldown(Cooldown cooldown) {
        cooldowns.add(cooldown);
    }

    static void tickCooldowns(LivingEntity entity) {
        cooldowns.removeIf(cooldown -> {
            CompoundPath path = cooldown.getPath().getParent();
            CompoundTag tag = path.getTag(entity);
            if (tag != null) {
                String tagName = cooldown.getPath().getPath();
                TagHelper.reduceBy1(tag, tagName);
                if (tag.contains(tagName, 3) && tag.getInt(tagName) <= 0) {
                    cooldown.onDone(entity);
                    return true;
                }
            }
            return false;
        });
    }
}