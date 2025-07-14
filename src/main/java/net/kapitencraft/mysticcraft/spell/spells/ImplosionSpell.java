package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.kap_lib.cooldown.Cooldown;
import net.kapitencraft.mysticcraft.item.combat.spells.necron_sword.NecronSword;
import net.kapitencraft.mysticcraft.misc.content.mana.ManaAOE;
import net.kapitencraft.mysticcraft.registry.ModCooldowns;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ImplosionSpell implements Spell {
    @Override
    public void cast(SpellCastContext context) {
        ManaAOE.execute(context.getCaster(), this, 0.1f, 5, 5);
    }

    @Override
    public double manaCost() {
        return 300;
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
        return ModCooldowns.IMPLOSION.get();
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
