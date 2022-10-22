package net.kapitencraft.mysticcraft.spell;

import net.kapitencraft.mysticcraft.item.spells.SpellItem;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
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
    public final String REGISTRY_NAME;

    public Spell(int mana_cost, String name, Spells.SpellType type, Rarity RARITY, String registry_name) {
        this.MANA_COST = mana_cost;
        this.NAME = name;
        this.TYPE = type;
        this.RARITY = RARITY;
        this.REGISTRY_NAME = registry_name;
    }

    public abstract void execute(Entity user, ItemStack stack);

    public abstract List<Component> getDescription();

    public abstract String getName();

    public List<Component> addDescription(List<Component> list, SpellItem item, ItemStack stack) {
        list.add(Component.literal("Ability: " + this.getName() + (item.getSpellSlots().length > 1 ? (" " + (item.getActiveSpell() + 1) + "/" + item.getSpellSlots().length) : "")).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GOLD));
        list.addAll(this.getDescription());
        if (this.MANA_COST > 0) {
            list.add(Component.literal(FormattingCodes.GRAY.UNICODE + "Mana-Cost: " + FormattingCodes.DARK_RED.UNICODE));
        }
        return list;
    }
}
