package net.kapitencraft.mysticcraft.misc;

import net.minecraft.ChatFormatting;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class FormattingCodes {

    public static final String BLACK = "\u00A70";
    public static final String DARK_BLUE = "\u00A71";
    public static final String DARK_GREEN = "\u00A72";
    public static final String DARK_AQUA = "\u00A73";
    public static final String DARK_RED = "\u00A74";
    public static final String DARK_PURPLE = "\u00A75";
    public static final String ORANGE = "\u00A76";
    public static final String GRAY = "\u00A77";
    public static final String DARK_GRAY = "\u00A78";
    public static final String BLUE = "\u00A79";
    public static final String GREEN = "\u00A7a";
    public static final String AQUA = "\u00A7b";
    public static final String RED = "\u00A7c";
    public static final String LIGHT_PURPLE = "\u00A7d";
    public static final String YELLOW = "\u00A7e";
    public static final String WHITE = "\u00A7f";
    public static final String GOLD = "\u00A7g";
    public static final String OBFUSCATED = "\u00A7k";
    public static final String BOLD = "\u00A7l";
    public static final String STRIKETHROUGH = "\u00A7m";
    public static final String UNDERLINE = "\u00A7n";
    public static final String ITALIC = "\u00A7o";
    public static final String RESET = "\u00A7r";

    public static final String CITATION = FormattingCodes.GRAY + FormattingCodes.ITALIC;

    public static final Rarity LEGENDARY = Rarity.create("LEGENDARY", ChatFormatting.GOLD);
    public static final Rarity MYTHIC = Rarity.create("MYTHIC", ChatFormatting.DARK_PURPLE);
    public static final Rarity DIVINE = Rarity.create("DIVINE", ChatFormatting.AQUA);

    public static final EnchantmentCategory SHIELD = EnchantmentCategory.create("shield", (item)-> item instanceof ShieldItem);
}
