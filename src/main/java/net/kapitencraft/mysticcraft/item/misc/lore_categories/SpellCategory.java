package net.kapitencraft.mysticcraft.item.misc.lore_categories;

import net.kapitencraft.mysticcraft.item.capability.spell.ISpellItem;
import net.minecraft.network.chat.Component;

public class SpellCategory extends SimpleItemCategory<ISpellItem> {
    public SpellCategory() {
        super(100, ISpellItem.class, Component.translatable("item.indicator.spell_catalyst"));
    }
}
