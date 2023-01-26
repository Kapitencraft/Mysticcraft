package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.spell.Spell;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

import java.util.List;

public class EmptySpell extends Spell {
    private Component[] description = new Component[]{Component.literal("No Spell assigned to this Item"), Component.literal("Use the Spellment-Table to apply Spells"), Component.literal("to this Item")};
    public EmptySpell() {
        super(0, "Empty Spell", null, Spells.CYCLE, Rarity.COMMON, "empty");
    }

    @Override
    public void execute(LivingEntity user, ItemStack stack) {
    }

    @Override
    public boolean canApply(Item stack) {
        return false;
    }

    @Override
    public List<Component> getDescription() {
        return List.of(description);
    }
}
