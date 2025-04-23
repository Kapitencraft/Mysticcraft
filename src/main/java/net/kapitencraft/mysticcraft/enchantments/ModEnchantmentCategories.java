package net.kapitencraft.mysticcraft.enchantments;

import net.kapitencraft.mysticcraft.inst.MysticcraftServer;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellItem;
import net.kapitencraft.mysticcraft.item.combat.weapon.ranged.QuiverItem;
import net.kapitencraft.mysticcraft.item.material.containable.ContainableItem;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public interface ModEnchantmentCategories {
    EnchantmentCategory SHIELD = EnchantmentCategory.create("shield", (item)-> item instanceof ShieldItem);
    EnchantmentCategory ALL_WEAPONS = EnchantmentCategory.create("all_weapons", item -> EnchantmentCategory.WEAPON.canEnchant(item) || EnchantmentCategory.BOW.canEnchant(item) || EnchantmentCategory.CROSSBOW.canEnchant(item));
    EnchantmentCategory SPELL_ITEM = EnchantmentCategory.create("spell_items", item -> item instanceof SpellItem);
    EnchantmentCategory TOOL = EnchantmentCategory.create("TOOL", item -> item instanceof DiggerItem || ALL_WEAPONS.canEnchant(item));
    EnchantmentCategory GEMSTONE_ITEM = EnchantmentCategory.create("GEMSTONE_ITEM", MysticcraftServer.getData()::has);
    EnchantmentCategory STORAGE_ITEM = EnchantmentCategory.create("CONTAINABLE", item -> item instanceof ContainableItem);
    EnchantmentCategory QUIVER = EnchantmentCategory.create("QUIVER", item -> item instanceof QuiverItem);
    EnchantmentCategory FARMING_TOOLS = EnchantmentCategory.create("FARMING_TOOLS", item -> item instanceof HoeItem || item instanceof AxeItem);
}
