package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.mysticcraft.item.capability.spell.SpellHelper;
import net.kapitencraft.mysticcraft.spell.SpellSlot;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Rarity;

import javax.annotation.Nullable;
import java.util.List;

public class TheStaffOfDestruction extends NormalSpellItem {
    private final Component[] description = {Component.literal("A very explosive tool")};
    public TheStaffOfDestruction() {
        super(new Properties().rarity(Rarity.RARE), 50, 20);

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
    public int getSlotAmount() {
        return 1;
    }

    @Override
    public void generateSlots(SpellHelper stack) {
        stack.setSlot(0, new SpellSlot(Spells.EXPLOSIVE_SIGHT));
    }
}