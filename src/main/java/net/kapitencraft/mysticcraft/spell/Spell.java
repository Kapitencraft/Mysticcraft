package net.kapitencraft.mysticcraft.spell;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class Spell {

    public final int MANA_COST;
    public final String NAME;

    public Spell(int mana_cost, String name) {
        this.MANA_COST = mana_cost;
        this.NAME = name;
    }

    public void execute(Entity entity, ItemStack stack) {

    }
}
