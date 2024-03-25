package net.kapitencraft.mysticcraft.misc.cooldown;

import net.kapitencraft.mysticcraft.helpers.IOHelper;
import net.kapitencraft.mysticcraft.item.item_bonus.fullset.CrimsonArmorFullSetBonus;
import net.kapitencraft.mysticcraft.misc.HealingHelper;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.kapitencraft.mysticcraft.spell.spells.WitherShieldSpell;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;

public interface Cooldowns {
    Cooldown WITHER_SHIELD = new Cooldown(CompoundPath.builder(WitherShieldSpell.DAMAGE_REDUCTION_TIME), 100, living -> {
        CompoundTag tag = living.getPersistentData();
        float absorption = tag.getFloat(WitherShieldSpell.ABSORPTION_AMOUNT_ID);
        HealingHelper.setEffectReason(living);
        living.heal(absorption / 2);
        living.setAbsorptionAmount(living.getAbsorptionAmount() - absorption);
        tag.remove(WitherShieldSpell.ABSORPTION_AMOUNT_ID);
    });
    Cooldown DOMINUS = new Cooldown(CompoundPath.builder(CrimsonArmorFullSetBonus.COOLDOWN_ID), 120, living -> {
        if (IOHelper.reduceBy1(living.getPersistentData(), CrimsonArmorFullSetBonus.DOMINUS_ID) > 0)
            Cooldowns.DOMINUS.applyCooldown(living, false);
    });
    MappedCooldown<Spells> SPELLS = new RepresentableMappedCooldown<>("Spells", null);
    MappedCooldown<EquipmentSlot> BONK_ENCHANTMENT = new MappedCooldown<>("Bonk", EquipmentSlot::getName, null);
}