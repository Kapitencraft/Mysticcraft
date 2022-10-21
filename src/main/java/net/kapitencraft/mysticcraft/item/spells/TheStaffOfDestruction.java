package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.init.ModCreativeModeTabs;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Rarity;

import javax.annotation.Nullable;
import java.util.List;

public class TheStaffOfDestruction extends SpellItem {
    private final Component[] description = {Component.literal("A very explosive tool")};
    private SpellSlot[] spellSlots = {new SpellSlot(Spells.EXPLOSIVE_SIGHT)};
    public TheStaffOfDestruction() {
        super(new Properties().tab(ModCreativeModeTabs.SPELL_AND_GEMSTONE).rarity(Rarity.RARE));
    }

    @Override
    public List<Component> getItemDescription() {
        return List.of(description);
    }

    @Override
    public @Nullable List<Component> getPostDescription() {
        return null;
    }

    @Override
    public SpellSlot[] getSpellSlots() {
        return spellSlots;
    }

    @Override
    public int getSpellSlotAmount() {
        return 1;
    }

    @Override
    public int getActiveSpell() {
        return 0;
    }
}
