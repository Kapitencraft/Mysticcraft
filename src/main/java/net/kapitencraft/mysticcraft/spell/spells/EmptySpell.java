package net.kapitencraft.mysticcraft.spell.spells;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class EmptySpell {
    private static final Component[] description = new Component[]{Component.literal("No Spell assigned to this Item"), Component.literal("Use the Spellment-Table to apply Spells"), Component.literal("to this Item")};
    public void execute(LivingEntity user, ItemStack stack) {
    }
    public static List<Component> getDescription() {
        return List.of(description);
    }
}
