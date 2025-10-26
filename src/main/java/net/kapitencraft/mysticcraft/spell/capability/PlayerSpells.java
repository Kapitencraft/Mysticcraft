package net.kapitencraft.mysticcraft.spell.capability;

import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.capability.spell.ItemSpells;
import net.kapitencraft.mysticcraft.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.registry.ModAttachmentTypes;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.kapitencraft.mysticcraft.registry.Spells;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record PlayerSpells(List<SpellSlot> slots) {
    public static final Codec<PlayerSpells> CODEC = SpellSlot.LIST_CODEC.xmap(PlayerSpells::new, PlayerSpells::slots);

    public static PlayerSpells get(Player player) {
        return player.getData(ModAttachmentTypes.PLAYER_SPELLS);
    }

    public static PlayerSpells create() {
        return new PlayerSpells(List.of(
                new SpellSlot(Spells.HUGE_HEAL, 6),
                new SpellSlot(Spells.CURE_VILLAGER),
                new SpellSlot(Spells.EXPLOSIVE_SIGHT, 10)
        ));
    }

    public static void updateSlot(Player player, ItemStack to) {
        int selected = player.getData(ModAttachmentTypes.SELECTED_SPELL_SLOT);
        PlayerSpells spells = get(player);
        if (to.has(ModDataComponentTypes.ITEM_SPELLS)) {
            ItemSpells itemSpells = SpellHelper.getSpells(to);
            if (itemSpells.getFirstEmpty() != 0)
                selected = spells.slots.size(); //move to slot above player slots, which should match the first item slot
        } else if (selected >= spells.slots.size())
                selected = 0;

        player.setData(ModAttachmentTypes.SELECTED_SPELL_SLOT, selected);
    }
}