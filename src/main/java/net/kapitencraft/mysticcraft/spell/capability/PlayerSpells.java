package net.kapitencraft.mysticcraft.spell.capability;

import net.kapitencraft.kap_lib.item.capability.AbstractCapability;
import net.kapitencraft.mysticcraft.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.registry.Spells;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerSpells implements AbstractCapability<List<SpellSlot>> {
    public static PlayerSpells get(Player player) {
        return player.getCapability(CapabilityHelper.PLAYER_SPELLS).orElse(null); //f*** you lazy optionals
    }


    private int slot;

    private final List<SpellSlot> spells = new ArrayList<>();

    public PlayerSpells() {
    }

    public static PlayerSpells create() {
        PlayerSpells spells = new PlayerSpells();
        spells.spells.add(new SpellSlot(Spells.HUGE_HEAL, 6));
        spells.spells.add(new SpellSlot(Spells.CURE_VILLAGER));
        spells.spells.add(new SpellSlot(Spells.EXPLOSIVE_SIGHT, 10));

        return spells;
    }

    @Override
    public void copyFrom(List<SpellSlot> data) {
        spells.clear();
        spells.addAll(data);
    }

    @Override
    public List<SpellSlot> getData() {
        return spells;
    }

    public void setSelectedSlot(int slot) {
        this.slot = slot;
    }

    public void updateSlot() {
        if (this.slot >= this.spells.size()) this.slot = 0;
    }

    public int getSlot() {
        return this.slot;
    }
}