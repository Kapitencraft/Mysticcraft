package net.kapitencraft.mysticcraft.spell;


import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.registry.Spells;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Supplier;

public class SpellSlot {

    public static final Codec<SpellSlot> CODEC = ModRegistries.SPELLS.getCodec().xmap(SpellSlot::new, SpellSlot::getSpell);
    public static final Codec<List<SpellSlot>> LIST_CODEC = SpellSlot.CODEC.listOf();

    private @NotNull Spell spell;
    private int level;

    public SpellSlot() {
        this(Spells.EMPTY);
    }

    public SpellSlot(@NotNull Spell spell) {
        this.spell = spell;
        this.level = 1;
    }

    public SpellSlot(Spell spell, int level) {
        this(spell);
        this.level = level;
    }

    public SpellSlot(Supplier<? extends @NotNull Spell> spellSupplier) {
        this(spellSupplier.get());
    }

    public SpellSlot(Supplier<? extends Spell> spell, int level) {
        this(spell);
        this.level = level;
    }

    public @NotNull Spell getSpell() {
        return this.spell;
    }

    public int getLevel() {
        return this.level;
    }

    public SpellSlot copy() {
        return new SpellSlot(this.spell, this.level);
    }

    public Component description() {
        return Component.translatable(spell.getDescriptionId()).append(CommonComponents.SPACE).append(Component.translatable("enchantment.level." + this.level));
    }
}
