package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.kap_lib.core.helpers.MiscHelper;
import net.kapitencraft.kap_lib.core.helpers.ParticleHelper;
import net.kapitencraft.kap_lib.core.util.Color;
import net.kapitencraft.mysticcraft.client.particle.options.CircleParticleOptions;
import net.kapitencraft.mysticcraft.item.combat.spells.necron_sword.NecronSword;
import net.kapitencraft.mysticcraft.registry.Spells;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistries;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellExecutionFailedException;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.kapitencraft.mysticcraft.util.content.mana.ManaAOE;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class WitherImpactSpell extends Spell {
    private static final Color EXPLOSION_COLOR = Color.fromARGBPacked(0xFF8F00FF);

    public WitherImpactSpell() {
        super(300, 70, Type.RELEASE, SpellTarget.SELF, null);
    }

    @Override
    public void cast(SpellCastContext context) throws SpellExecutionFailedException {
        LivingEntity user = context.getCaster();
        Spells.WITHER_SHIELD.value().cast(context);
        MiscHelper.saveTeleport(user, 10);
        ManaAOE.execute(user, ModRegistries.SPELLS.createIntrusiveHolder(this), 5, 5);
        ParticleHelper.sendParticles(user.level(), new CircleParticleOptions(EXPLOSION_COLOR, 5, 0.6), false, user.getX(), user.getY(), user.getZ(), 1, 0, 0, 0, 0);
    }

    @Override
    public boolean canApply(Item item) {
        return item instanceof NecronSword;
    }
}
