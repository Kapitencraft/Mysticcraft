package net.kapitencraft.mysticcraft.spell;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public abstract class Spell {

    public final int MANA_COST;
    public final String NAME;
    public final Spells.SpellType TYPE;

    public Spell(int mana_cost, String name, Spells.SpellType type) {
        this.MANA_COST = mana_cost;
        this.NAME = name;
        this.TYPE = type;
    }

    public abstract void execute(Entity user, ItemStack stack);

    public abstract List<Component> getDescription();

    public abstract String getName();


}
