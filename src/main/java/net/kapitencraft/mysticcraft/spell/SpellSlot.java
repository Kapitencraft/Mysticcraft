package net.kapitencraft.mysticcraft.spell;


import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.registry.Spells;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class SpellSlot {

    public static final Codec<SpellSlot> CODEC = ModRegistries.SPELLS.getCodec().xmap(SpellSlot::new, SpellSlot::getSpell);

    private @NotNull Spell spell;

    public SpellSlot() {
        this(Spells.EMPTY);
    }

    public SpellSlot(@NotNull Spell spell) {
        this.spell = spell;
    }

    public SpellSlot(Supplier<? extends @NotNull Spell> spellSupplier) {
        this(spellSupplier.get());
    }

    public @NotNull Spell getSpell() {
        return this.spell;
    }
}
