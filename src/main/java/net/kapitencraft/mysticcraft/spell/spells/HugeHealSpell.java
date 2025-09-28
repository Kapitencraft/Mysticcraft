package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.kap_lib.cooldown.Cooldown;
import net.kapitencraft.mysticcraft.registry.ModCooldowns;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellTarget;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContext;
import net.kapitencraft.mysticcraft.spell.cast.SpellCastContextParams;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HugeHealSpell implements Spell {

    @Override
    public void cast(SpellCastContext context) {
        context.getParamOrThrow(SpellCastContextParams.CASTER).heal(context.getLevel() * 2.5f);
    }

    @Override
    public double manaCost() {
        return 70;
    }

    @Override
    public int castDuration() {
        return 50;
    }

    @Override
    public @NotNull Type getType() {
        return Type.RELEASE;
    }

    @Override
    public @Nullable Cooldown getCooldown() {
        return ModCooldowns.HUGH_HEAL.get();
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
