package net.kapitencraft.mysticcraft.enchantments.abstracts;

import net.kapitencraft.mysticcraft.item.QuiverItem;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellItem;
import net.kapitencraft.mysticcraft.item.gemstone.IGemstoneApplicable;
import net.kapitencraft.mysticcraft.item.tools.ContainableItem;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public abstract class ModEnchantmentCategories {
    public static final EnchantmentCategory ALL_WEAPONS = EnchantmentCategory.create("all_weapons", item -> EnchantmentCategory.WEAPON.canEnchant(item) || EnchantmentCategory.BOW.canEnchant(item) || EnchantmentCategory.CROSSBOW.canEnchant(item));
    public static final EnchantmentCategory SPELL_ITEM = EnchantmentCategory.create("spell_items", item -> item instanceof SpellItem);
    public static final EnchantmentCategory TOOL = EnchantmentCategory.create("TOOL", item -> item instanceof DiggerItem || item instanceof SwordItem || item instanceof BowItem || item instanceof SpellItem);
    public static final EnchantmentCategory GEMSTONE_ITEM = EnchantmentCategory.create("GEMSTONE_ITEM", item -> item instanceof IGemstoneApplicable);
    public static final EnchantmentCategory STORAGE_ITEM = EnchantmentCategory.create("CONTAINABLE", item -> item instanceof ContainableItem);
    public static final EnchantmentCategory QUIVER = EnchantmentCategory.create("QUIVER", item -> item instanceof QuiverItem);
}