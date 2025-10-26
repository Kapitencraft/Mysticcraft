package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.registry.ModCooldowns;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellExecutionFailedException;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class FireBoltSpell extends Spell {

    public FireBoltSpell() {
        super(50, 20, Type.RELEASE, SpellTarget.SELF, ModCooldowns.FIRE_BOLT);
    }

    @Override
    public void cast(SpellCastContext context) throws SpellExecutionFailedException {
        LivingEntity caster = context.getCaster();
        float baseDamage = context.getLevel() * 1.4f;
        FireBoltProjectile projectile = FireBoltProjectile.createProjectile(context.getWorld(), caster, context.getLevel() > 10, baseDamage, this.getHolder());
        projectile.shootFromRotation(caster, caster.getXRot(), caster.getYRot(), 0, 2, 1);
        projectile.setBaseDamage(baseDamage);
        caster.level().addFreshEntity(projectile);
    }

    @Override
    public boolean canApply(Item item) {
        return true;
    }
}
