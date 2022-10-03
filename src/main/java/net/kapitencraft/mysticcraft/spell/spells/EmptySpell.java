package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class EmptySpell extends Spell {
    Component[] description = new Component[]{Component.literal("No Spell assigned to this Item"), Component.literal("Use the Spell-Girder to apply Spells"), Component.literal("to this Item")};
    public EmptySpell() {
        super(0, "empty", Spells.CYCLE);
    }

    @Override
    public void execute(Entity user, ItemStack stack) {
    }

    @Override
    public List<Component> getDescription() {
        return List.of(description);
    }

    @Override
    public String getName() {
        return "Empty Spell";
    }
}
