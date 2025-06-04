package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.mysticcraft.cooldown.Cooldowns;
import net.kapitencraft.mysticcraft.item.combat.spells.necron_sword.NecronSword;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class WitherShieldSpell implements Spell {
    public static final String DAMAGE_REDUCTION_TIME = "WS-DamageReductionTime";
    public static final String ABSORPTION_AMOUNT_ID = "WS-AbsorptionAmount";

    @Override
    public void cast(SpellCastContext context) {
        LivingEntity caster = context.getCaster();
        CompoundTag tag = caster.getPersistentData();
        Cooldowns.WITHER_SHIELD.applyCooldown(caster, true);
        float absorption = (float) (caster.getAttributeValue(ExtraAttributes.CRIT_DAMAGE.get()) * 0.3);
        if (tag.getFloat(ABSORPTION_AMOUNT_ID) <= 0 || !tag.contains(ABSORPTION_AMOUNT_ID)) {
            caster.setAbsorptionAmount(caster.getAbsorptionAmount() + absorption);
        }
        tag.putFloat(ABSORPTION_AMOUNT_ID, absorption);
    }

    @Override
    public double manaCost() {
        return 150;
    }

    @Override
    public Type getType() {
        return Type.RELEASE;
    }

    @Override
    public int getCooldownTime() {
        return 100;
    }

    @Override
    public boolean canApply(Item item) {
        return item instanceof NecronSword;
    }
}
