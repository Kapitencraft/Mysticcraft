package net.kapitencraft.mysticcraft.spell;

import net.kapitencraft.mysticcraft.item.spells.IFireScytheItem;
import net.kapitencraft.mysticcraft.item.spells.SpellItem;
import net.kapitencraft.mysticcraft.item.spells.necron_sword.NecronSword;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.spell.spells.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public enum Spells {
    WITHER_IMPACT("Wither Impact", "1101110", Spell.Type.RELEASE, 0, WitherImpactSpell::execute, item -> item instanceof NecronSword, WitherImpactSpell::getDescription, Rarity.RARE),
    IMPLOSION("Implosion", "0000000", Spell.Type.RELEASE, 0, ImplosionSpell::execute, item -> item instanceof NecronSword, ImplosionSpell::getDescription, FormattingCodes.LEGENDARY),
    INSTANT_TRANSMISSION("Instant Transmission", "1110111", Spell.Type.RELEASE, 0, InstantTransmissionSpell::execute, item -> true, InstantTransmissionSpell::getDescription, Rarity.COMMON),
    EXPLOSIVE_SIGHT("Explosive Sight", "1010110", Spell.Type.RELEASE, 0, ExplosiveSightSpell::execute, item -> true, ExplosiveSightSpell::getDescription, Rarity.UNCOMMON),
    EMPTY_SPELL("Empty Spell", null, Spell.Type.RELEASE, 0, (living, itemStack)-> {}, item -> false, EmptySpell::getDescription, Rarity.UNCOMMON),
    HUGE_HEAL("Huge Heal", "0011011", Spell.Type.RELEASE, 0, HugeHealSpell::execute, HugeHealSpell::canApply, HugeHealSpell::getDescription, Rarity.UNCOMMON),
    FIRE_BOLT_1("Fire Bolt", "0110011", Spell.Type.RELEASE, 0, createFireBold(1, false), item -> item instanceof IFireScytheItem, createFireBoldDesc(1f), Rarity.UNCOMMON),
    FIRE_BOLT_2("Fire Bolt", "0110011", Spell.Type.RELEASE, 0, createFireBold(1.4, false), item -> item instanceof IFireScytheItem, createFireBoldDesc(1.4f), Rarity.UNCOMMON),
    FIRE_BOLT_3("Fire Bolt", "0110011", Spell.Type.RELEASE, 0, createFireBold(2.8, true), item -> item instanceof IFireScytheItem, createFireBoldDesc(2.8f), Rarity.UNCOMMON),
    FIRE_BOLT_4("Fire Bolt", "0110011", Spell.Type.RELEASE, 0, createFireBold(5.2, true), item -> item instanceof IFireScytheItem, createFireBoldDesc(5.2f), Rarity.UNCOMMON),
    FIRE_LANCE("Fire Lance", "1011100", Spell.Type.CYCLE, 0, FireLanceSpell::execute, FireLanceSpell::canApply, FireLanceSpell::getDescription, Rarity.UNCOMMON);

    private static Functions.SpellRun createFireBold(double baseDamage, boolean explosive) {
        return (user, stack) -> {
            FireBoltProjectile projectile = FireBoltProjectile.createProjectile(user.level, user, explosive, baseDamage, "Fire Bolt");
            projectile.shootFromRotation(user, user.getXRot(), user.getYRot(), 0, 2, 1);
            projectile.setBaseDamage(baseDamage);
            user.level.addFreshEntity(projectile);
        };
    }
    private static Supplier<List<Component>> createFireBoldDesc(float damage) {
        return ()-> List.of(Component.literal("Fires a Fire Bolt dealing"), Component.literal(FormattingCodes.RED + damage + FormattingCodes.RESET + " Base Ability Damage"));
    }
    private static class Functions {
        private interface SpellRun {
            void execute(LivingEntity user, ItemStack stack);
        }
        private interface applicationHelper {
            boolean canApply(Item item);
        }
    }
    private final String castingType, name;
    public final Spell.Type TYPE;
    public final double MANA_COST;
    private final Functions.SpellRun run;
    private final Functions.applicationHelper helper;
    private final Supplier<List<Component>> description;
    public final String REGISTRY_NAME;
    public final Rarity RARITY;
    Spells(String name, String castingType, Spell.Type type, double manaCost, Functions.SpellRun toRun, Functions.applicationHelper helper, Supplier<List<Component>> description, Rarity rarity) {
        this.name = name;
        this.TYPE = type;
        this.MANA_COST = manaCost;
        this.run = toRun;
        this.helper = helper;
        this.description = description;
        this.castingType = castingType;
        this.REGISTRY_NAME = name.toLowerCase().replace(" ", "_");
        this.RARITY = rarity;
    }

    public String getCastingType() {
        return castingType;
    }

    public String getName() {
        return this.name;
    }
    public void execute(LivingEntity living, ItemStack stack) {
        this.run.execute(living, stack);
    }

    public List<Component> getDescription() {
        return description.get();
    }

    public boolean canApply(Item item) {return this.helper.canApply(item);}
    public List<Component> addDescription(List<Component> list, SpellItem item, ItemStack stack) {
        int spellSlotAmount = item.getSpellSlotAmount();
        list.add(Component.literal("Ability: " + this.getName() + " " + (spellSlotAmount > 1 ? (item.getIndexForSlot(this) + 1) + " / " + item.getSpellSlotAmount() : "")).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GOLD));
        if (this.description.get() != null) list.addAll(this.description.get());
        if (this.MANA_COST > 0) list.add(Component.literal(FormattingCodes.GRAY + "Mana-Cost: " + FormattingCodes.DARK_RED));
        if (this.castingType != null && item.getSpellSlotAmount() > 1) list.add(Component.literal("Pattern: [" + this.getPattern() + FormattingCodes.RESET + "]"));
        return list;
    }

    public static Spells get(String pattern) {
        for (Spells spells : values()) {
            String castingType = spells.getCastingType();
            if (Objects.equals(castingType, pattern)) {
                return spells;
            }
        }
        return EMPTY_SPELL;
    }

    public static boolean contains(String pattern) {
        return get(pattern) != EMPTY_SPELL;
    }

    public static String getPattern(String s) {
        String replace = s.replace("0", RED);
        return replace.replace("1", BLUE);
    }

    public String getPattern() {
        return getPattern(this.castingType);
    }

    private static final String BLUE = FormattingCodes.BLUE + "\u2726";
    private static final String RED = FormattingCodes.RED + "\u2724";
    public static String getStringForPattern(String pattern, boolean fromTooltip) {
        if (fromTooltip) {
            String replace1 = pattern.replace("Pattern: [", "");
            pattern = replace1.replace("§r]", "");
        }
        String replace = pattern.replace(RED, "0");
        return replace.replace(BLUE, "1");
    }


}
