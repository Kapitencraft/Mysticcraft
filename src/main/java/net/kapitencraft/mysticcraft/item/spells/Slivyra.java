package net.kapitencraft.mysticcraft.item.spells;

import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Rarity;

import java.util.List;

public class Slivyra extends SpellItem {

    Component[] description = {Component.literal("Some say, it might be the most"), Component.literal("powerful Magic Weapon in the world")};

    public Slivyra() {
        super(new Properties().rarity(Rarity.RARE), 1, 200, 0);
        this.addSlot(new SpellSlot(Spells.CRYSTAL_WARP));
    }

    @Override
    public List<Component> getItemDescription() {
        return List.of(this.description);
    }

    @Override
    public List<Component> getPostDescription() {
        return null;
    }
}