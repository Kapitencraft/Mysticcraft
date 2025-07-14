package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.kap_lib.cooldown.Cooldown;
import net.kapitencraft.kap_lib.registry.ExtraAttributes;
import net.kapitencraft.mysticcraft.item.combat.spells.necron_sword.NecronSword;
import net.kapitencraft.mysticcraft.registry.ModCooldowns;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WitherShieldSpell implements Spell {
    public static final String DAMAGE_REDUCTION_TIME = "WS-DamageReductionTime";
    public static final String ABSORPTION_AMOUNT_ID = "WS-AbsorptionAmount";

    @Override
    public void cast(SpellCastContext context) {
        LivingEntity caster = context.getCaster();
        CompoundTag tag = caster.getPersistentData();
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
    public int castDuration() {
        return 10;
    }

    @Override
    public @NotNull Type getType() {
        return Type.RELEASE;
    }

    @Override
    public @Nullable Cooldown getCooldown() {
        return ModCooldowns.WITHER_SHIELD.get();
    }

    @Override
    public @NotNull SpellTarget getTarget() {
        return SpellTarget.SELF;
    }

    @Override
    public boolean canApply(Item item) {
        return item instanceof NecronSword;
    }
}
