package net.kapitencraft.mysticcraft.spell;

import net.kapitencraft.mysticcraft.item.spells.SpellItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

import java.util.ArrayList;
import java.util.List;

public abstract class Spell {

    public final int MANA_COST;
    public final String NAME;
    public final Spells.SpellType TYPE;
    public final Rarity RARITY;

    public Spell(int mana_cost, String name, Spells.SpellType type, Rarity RARITY) {
        this.MANA_COST = mana_cost;
        this.NAME = name;
        this.TYPE = type;
        this.RARITY = RARITY;
    }

    public abstract void execute(Entity user, ItemStack stack);

    public abstract List<Component> getDescription();

    public abstract String getName();

    public List<Component> createDescription(Item item) {
        List<Component> list = new ArrayList<>();
        list.add(Component.literal("Ability: " + this.getName() + (item instanceof SpellItem spellItem && spellItem.getSpellSlots().length > 1 ? (" " + (spellItem.getActiveSpell() + 1) + "/" + spellItem.getSpellSlots().length) : "")).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GOLD));
        list.addAll(this.getDescription());

        return list;
    }
}
