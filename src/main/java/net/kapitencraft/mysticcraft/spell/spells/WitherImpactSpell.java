package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.kap_lib.util.Color;
import net.kapitencraft.mysticcraft.client.particle.options.CircleParticleOptions;
import net.kapitencraft.mysticcraft.helpers.ParticleHelper;
import net.kapitencraft.mysticcraft.item.combat.spells.necron_sword.NecronSword;
import net.kapitencraft.mysticcraft.misc.content.mana.ManaAOE;
import net.kapitencraft.mysticcraft.registry.Spells;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class WitherImpactSpell implements Spell {

    @Override
    public void cast(SpellCastContext context) {
        LivingEntity user = context.getCaster();
        Spells.WITHER_SHIELD.get().cast(context);
        MiscHelper.saveTeleport(user, 10);
        ManaAOE.execute(user, this, 0.4f, 5, 5);
        ParticleHelper.sendParticles(user.level(), new CircleParticleOptions(new Color(143f / 255, 0, 1, 1), 5, 0.6), false, user.getX(), user.getY(), user.getZ(), 1, 0, 0, 0, 0);
    }

    @Override
    public double manaCost() {
        return 300;
    }

    @Override
    public Type getType() {
        return Type.RELEASE;
    }

    @Override
    public int getCooldownTime() {
        return 0;
    }

    @Override
    public boolean canApply(Item item) {
        return item instanceof NecronSword;
    }
}
