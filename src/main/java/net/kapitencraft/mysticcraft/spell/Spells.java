package net.kapitencraft.mysticcraft.spell;

import net.kapitencraft.mysticcraft.spell.spells.*;

import java.util.Objects;

public enum Spells {
    WITHER_IMPACT(new WitherImpactSpell()),
    IMPLOSION(new ImplosionSpell()),
    INSTANT_TRANSMISSION(new InstantTransmissionSpell()),
    EXPLOSIVE_SIGHT(new ExplosiveSightSpell()),
    EMPTY_SPELL(new EmptySpell()),
    HUGE_HEAL(new HugeHealSpell()),
    FIRE_BOLT_1(new FireBoltSpell(1, false)),
    FIRE_BOLT_2(new FireBoltSpell(1.4, false)),
    FIRE_BOLT_3(new FireBoltSpell(2.8, true)),
    FIRE_BOLT_4(new FireBoltSpell(5.2, true)),
    FIRE_LANCE(new FireLanceSpell());


    private final Spell spell;
    Spells(Spell spell) {
        this.spell = spell;
    }

    public Spell getSpell() {
        return spell;
    }

    public static Spell get(String pattern) {
        for (Spells spells : values()) {
            String castingType = spells.spell.getCastingType();
            if (Objects.equals(castingType, pattern)) {
                return spells.spell;
            }
        }
        return EMPTY_SPELL.spell;
    }

    public static boolean contains(String pattern) {
        return get(pattern) != EMPTY_SPELL.spell;
    }
}
