package net.kapitencraft.mysticcraft.cooldown;

import net.kapitencraft.mysticcraft.helpers.IOHelper;
import net.kapitencraft.mysticcraft.item.item_bonus.fullset.CrimsonArmorFullSetBonus;
import net.kapitencraft.mysticcraft.misc.HealingHelper;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.kapitencraft.mysticcraft.spell.spells.WitherShieldSpell;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

import java.util.ArrayList;
import java.util.List;

public interface Cooldowns {
    List<Cooldown> cooldowns = new ArrayList<>();
    Cooldown WITHER_SHIELD = new Cooldown(CompoundPath.builder(WitherShieldSpell.DAMAGE_REDUCTION_TIME).withParent(CompoundPath.COOLDOWN).build(), 100, entity -> {
        if (entity instanceof LivingEntity living) {
            CompoundTag tag = living.getPersistentData();
            float absorption = tag.getFloat(WitherShieldSpell.ABSORPTION_AMOUNT_ID);
            HealingHelper.setEffectReason(living);
            living.heal(absorption / 2);
            living.setAbsorptionAmount(living.getAbsorptionAmount() - absorption);
            tag.putFloat(WitherShieldSpell.ABSORPTION_AMOUNT_ID, 0);
        }
    });
    Cooldown DOMINUS = new Cooldown(CompoundPath.builder(CrimsonArmorFullSetBonus.COOLDOWN_ID).withParent(CompoundPath.COOLDOWN).build(), 120, living -> {
        if (IOHelper.reduceBy1(living.getPersistentData(), CrimsonArmorFullSetBonus.DOMINUS_ID) > 0)
            Cooldowns.DOMINUS.applyCooldown(living, false);
    });
    MappedCooldown<Spells> SPELLS = new RepresentableMappedCooldown<>("Spells", null);
    MappedCooldown<EquipmentSlot> BONK_ENCHANTMENT = new MappedCooldown<>("Bonk", EquipmentSlot::getName, null);

    static void addCooldown(Cooldown cooldown) {
        cooldowns.add(cooldown);
    }

    static void tickCooldowns(LivingEntity entity) {
        cooldowns.removeIf(cooldown -> {
            CompoundPath path = cooldown.getPath().getParent();
            CompoundTag tag = path.getTag(entity);
            if (tag != null) {
                String tagName = cooldown.getPath().getPath();
                IOHelper.reduceBy1(tag, tagName);
                if (tag.contains(tagName, 3) && tag.getInt(tagName) <= 0) {
                    cooldown.onDone(entity);
                    return true;
                }
            }
            return false;
        });
    }
}