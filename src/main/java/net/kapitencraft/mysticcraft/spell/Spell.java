package net.kapitencraft.mysticcraft.spell;

import net.kapitencraft.mysticcraft.item.spells.SpellItem;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

import java.util.List;

public abstract class Spell {
    public static final Type RELEASE = new Type("release");
    public static final Type CYCLE = new Type("cycle");
    public record Type(String name) {

    }
    private static final String BLUE = FormattingCodes.BLUE + "\u2726";
    private static final String RED = FormattingCodes.RED + "\u2724";

    public static String getPattern(String s) {
        String replace = s.replace("0", RED);
        return replace.replace("1", BLUE);
    }

    public static String getStringForPattern(String pattern, boolean fromTooltip) {
        if (fromTooltip) {
            String replace1 = pattern.replace("Pattern: [", "");
            pattern = replace1.replace("§r]", "");
        }
        String replace = pattern.replace(RED, "0");
        return replace.replace(BLUE, "1");

    }

    public String getPattern() {
        return getPattern(this.castingType);
    }


    public final int MANA_COST;
    public final String NAME;
    private final String castingType;
    public final Type TYPE;
    public final Rarity RARITY;
    public final String REGISTRY_NAME;

    public Spell(int mana_cost, String name, String castingType, Type type, Rarity RARITY) {
        this.MANA_COST = mana_cost;
        this.NAME = name;
        if (castingType != null && castingType.length() != 7) {
            throw new RuntimeException("Casting pattern should have a size of 7");
        }
        this.castingType = castingType;
        this.TYPE = type;
        this.RARITY = RARITY;
        this.REGISTRY_NAME = name.toLowerCase().replace(" ", "_");
    }

    public abstract void execute(LivingEntity user, ItemStack stack);
    public abstract boolean canApply(Item stack);

    public abstract List<Component> getDescription();

    public String getName() {
        return NAME;
    }

    public String getCastingType() {
        return this.castingType;
    }

    public List<Component> addDescription(List<Component> list, SpellItem item, ItemStack stack) {
        int spellSlotAmount = item.getSpellSlotAmount();
        list.add(Component.literal("Ability: " + this.getName() + (spellSlotAmount > 1 ? (" " + (item.getActiveSpellIndex() + 1) + "/" + spellSlotAmount) : "")).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GOLD));
        if (this.getDescription() != null) list.addAll(this.getDescription());
        if (this.MANA_COST > 0) list.add(Component.literal(FormattingCodes.GRAY + "Mana-Cost: " + FormattingCodes.DARK_RED));
        if (this.castingType != null && item.getSpellSlotAmount() > 1) list.add(Component.literal("Pattern: [" + this.getPattern() + FormattingCodes.RESET + "]"));
        return list;
    }
}
