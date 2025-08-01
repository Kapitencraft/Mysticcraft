package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.kap_lib.cooldown.Cooldown;
import net.kapitencraft.mysticcraft.registry.ModCooldowns;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellExecutionFailedException;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FireBoltSpell implements Spell {
    private final boolean explosive;
    private final float baseDamage;

    public FireBoltSpell(boolean explosive, float baseDamage) {
        this.explosive = explosive;
        this.baseDamage = baseDamage;
    }

    @Override
    public void cast(SpellCastContext context) throws SpellExecutionFailedException {
        LivingEntity caster = context.getCaster();
        FireBoltProjectile projectile = FireBoltProjectile.createProjectile(caster.level(), caster, explosive, baseDamage, this);
        projectile.shootFromRotation(caster, caster.getXRot(), caster.getYRot(), 0, 2, 1);
        projectile.setBaseDamage(baseDamage);
        caster.level().addFreshEntity(projectile);
    }

    @Override
    public double manaCost() {
        return 50;
    }

    @Override
    public int castDuration() {
        return 20;
    }

    @Override
    public @NotNull Type getType() {
        return Type.RELEASE;
    }

    @Override
    public @Nullable Cooldown getCooldown() {
        return ModCooldowns.FIRE_BOLT.get();
    }

    @Override
    public @NotNull SpellTarget getTarget() {
        return SpellTarget.SELF;
    }

    @Override
    public boolean canApply(Item item) {
        return true;
    }
}
