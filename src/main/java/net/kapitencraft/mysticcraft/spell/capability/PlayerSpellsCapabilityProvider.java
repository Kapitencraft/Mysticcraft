package net.kapitencraft.mysticcraft.spell.capability;

import com.mojang.serialization.Codec;
import net.kapitencraft.kap_lib.item.capability.CapabilityProvider;
import net.kapitencraft.mysticcraft.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.minecraftforge.common.capabilities.Capability;

import java.util.List;

public class PlayerSpellsCapabilityProvider extends CapabilityProvider<List<SpellSlot>, PlayerSpells> {
    protected PlayerSpellsCapabilityProvider(PlayerSpells object, Codec<List<SpellSlot>> codec, Capability<PlayerSpells> capability) {
        super(object, codec, capability);
    }

    public static PlayerSpellsCapabilityProvider create() {
        return new PlayerSpellsCapabilityProvider(PlayerSpells.create(), SpellSlot.LIST_CODEC, CapabilityHelper.PLAYER_SPELLS);
    }

    @Override
    protected List<SpellSlot> fallback() {
        return List.of();
    }
}
