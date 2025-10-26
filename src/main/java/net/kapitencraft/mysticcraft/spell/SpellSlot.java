package net.kapitencraft.mysticcraft.spell;


import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.registry.Spells;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistries;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpellSlot {

    public static final Codec<SpellSlot> CODEC = ModRegistries.SPELLS.holderByNameCodec().xmap(SpellSlot::new, SpellSlot::getSpell);
    public static final Codec<List<SpellSlot>> LIST_CODEC = SpellSlot.CODEC.listOf();
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellSlot> STREAM_CODEC = ByteBufCodecs.holderRegistry(ModRegistries.Keys.SPELLS).map(SpellSlot::new, SpellSlot::getSpell);

    private final @NotNull Holder<Spell> spell;
    private final int level;

    public SpellSlot() {
        this(Spells.EMPTY);
    }

    public SpellSlot(@NotNull Holder<Spell> spell) {
        this(spell, 1);
    }

    public SpellSlot(@NotNull Holder<Spell> spell, int level) {
        this.spell = spell;
        this.level = level;
    }

    public @NotNull Holder<Spell> getSpell() {
        return this.spell;
    }

    public int getLevel() {
        return this.level;
    }

    public SpellSlot copy() {
        return new SpellSlot(this.spell, this.level);
    }

    public SpellSlot withLevel(int level) {
        return new SpellSlot(this.spell, level);
    }

    public SpellSlot withSpell(Holder<Spell> spell) {
        return new SpellSlot(spell, this.level);
    }

    public Component description() {
        return Component.translatable(Util.makeDescriptionId("spell", spell.getKey().location())).append(CommonComponents.SPACE).append(Component.translatable("enchantment.level." + this.level));
    }
}
