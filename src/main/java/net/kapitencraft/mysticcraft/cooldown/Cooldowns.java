package net.kapitencraft.mysticcraft.cooldown;

import net.kapitencraft.kap_lib.cooldown.CompoundPath;
import net.kapitencraft.kap_lib.cooldown.Cooldown;
import net.kapitencraft.kap_lib.cooldown.MappedCooldown;
import net.kapitencraft.mysticcraft.misc.HealingHelper;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.spells.WitherShieldSpell;
import net.minecraft.nbt.CompoundTag;

public interface Cooldowns {
    Cooldown WITHER_SHIELD = new Cooldown(CompoundPath.builder(WitherShieldSpell.DAMAGE_REDUCTION_TIME), 100, living -> {
        CompoundTag tag = living.getPersistentData();
        float absorption = tag.getFloat(WitherShieldSpell.ABSORPTION_AMOUNT_ID);
        HealingHelper.setEffectReason(living);
        living.heal(absorption / 2);
        living.setAbsorptionAmount(living.getAbsorptionAmount() - absorption);
        tag.remove(WitherShieldSpell.ABSORPTION_AMOUNT_ID);
    });
    MappedCooldown<Spell> SPELLS = new MappedCooldown<>("spells", Spell::getDescriptionId, null);
}