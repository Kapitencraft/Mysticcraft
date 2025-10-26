package net.kapitencraft.mysticcraft.capability.spell;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.List;

public record ItemSpells(List<SpellSlot> slots) {
    public static final Codec<ItemSpells> CODEC = SpellSlot.LIST_CODEC.xmap(ItemSpells::new, ItemSpells::slots);
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemSpells> STREAM_CODEC = SpellSlot.STREAM_CODEC.apply(ByteBufCodecs.list()).map(ItemSpells::new, ItemSpells::slots);

    public ItemSpells setSlot(int index, SpellSlot slot) {
        ArrayList<SpellSlot> arrayList = new ArrayList<>(this.slots);
        arrayList.set(index, slot);
        return new ItemSpells(ImmutableList.copyOf(arrayList));
    }

    public SpellSlot getSlot(int index) {
        return this.slots.get(index);
    }

    public void clearSlot(int index) {
        this.slots.set(index, null);
    }

    public boolean hasSpell(Spell spell) {
        for (SpellSlot slot : this.slots) {
            if (slot.getSpell() == spell) return true;
        }
        return false;
    }

    public int getFirstEmpty() {
        for (int i = 0; i < this.slots.size(); i++) {
            if (this.slots.get(i) == null) return i;
        }
        return -1;
    }
}
