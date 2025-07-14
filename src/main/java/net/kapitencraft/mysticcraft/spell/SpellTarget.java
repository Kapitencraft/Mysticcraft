package net.kapitencraft.mysticcraft.spell;

import net.kapitencraft.mysticcraft.spell.cast.SpellCastContextParams;

public enum SpellTarget {

    /**
     * the spell shall target the caster. only {@link SpellCastContextParams#CASTER} is available to use
     */
    SELF,
    /**
     * the spell shall target a block.
     * <br> Params {@link SpellCastContextParams#CASTER} and {@link SpellCastContextParams#TARGET_BLOCK} are available to use
     */
    BLOCK,
    /**
     * the spell shall target a entity.
     * <br> Params {@link SpellCastContextParams#CASTER} and {@link SpellCastContextParams#TARGET} are available
     */
    ENTITY
}
