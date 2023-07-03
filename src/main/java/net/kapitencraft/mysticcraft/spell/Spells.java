package net.kapitencraft.mysticcraft.spell;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.item.spells.*;
import net.kapitencraft.mysticcraft.item.spells.necron_sword.NecronSword;
import net.kapitencraft.mysticcraft.misc.FormattingCodes;
import net.kapitencraft.mysticcraft.misc.utils.AttributeUtils;
import net.kapitencraft.mysticcraft.misc.utils.TextUtils;
import net.kapitencraft.mysticcraft.spell.spells.*;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public enum Spells implements Spell {
    WITHER_IMPACT("Wither Impact", "1101110", Type.RELEASE, 300, WitherImpactSpell::execute, item -> item instanceof NecronSword, WitherImpactSpell::getDescription, Rarity.RARE, true),
    WITHER_SHIELD("Wither Shield", "1101111", Type.RELEASE, 150, WitherShieldSpell::execute, item -> item instanceof NecronSword, WitherShieldSpell::getDescription, Rarity.RARE, true),
    IMPLOSION("Implosion", "0000000", Type.RELEASE, 300, ImplosionSpell::execute, item -> item instanceof NecronSword, ImplosionSpell::getDescription, FormattingCodes.LEGENDARY, true),
    INSTANT_TRANSMISSION("Instant Transmission", "1110111", Type.RELEASE, 50, InstantTransmissionSpell::execute, item -> true, InstantTransmissionSpell::getDescription, Rarity.COMMON, true),
    ETHER_WARP("Ether Transmission", "1000111", Type.RELEASE, 50, EtherWarpSpell::execute, item -> item instanceof AspectOfTheVoidItem, EtherWarpSpell::getDescription, Rarity.EPIC, true),
    EXPLOSIVE_SIGHT("Explosive Sight", "1010110", Type.RELEASE, 150, ExplosiveSightSpell::execute, item -> true, ExplosiveSightSpell::getDescription, Rarity.UNCOMMON, true),
    EMPTY_SPELL("Empty Spell", null, Type.RELEASE, 0, (living, itemStack)-> {}, item -> false, EmptySpell::getDescription, Rarity.UNCOMMON, false),
    HUGE_HEAL("Huge Heal", "0011011", Type.RELEASE, 70, HugeHealSpell::execute, item -> true, HugeHealSpell::getDescription, Rarity.UNCOMMON, true),
    FIRE_BOLT_1("Fire Bolt 1", "0110011", Type.RELEASE, 50, createFireBold(1, false), item -> item instanceof IFireScytheItem, createFireBoldDesc(1f), Rarity.UNCOMMON, true),
    FIRE_BOLT_2("Fire Bolt 2", "0110011", Type.RELEASE, 50, createFireBold(1.4, false), item -> item instanceof IFireScytheItem, createFireBoldDesc(1.4f), Rarity.UNCOMMON, true),
    FIRE_BOLT_3("Fire Bolt 3", "0110011", Type.RELEASE, 50, createFireBold(2.8, true), item -> item instanceof IFireScytheItem, createFireBoldDesc(2.8f), Rarity.UNCOMMON, true),
    FIRE_BOLT_4("Fire Bolt 4", "0110011", Type.RELEASE, 50, createFireBold(5.2, true), item -> item instanceof IFireScytheItem, createFireBoldDesc(5.2f), Rarity.UNCOMMON, true),
    FIRE_LANCE("Fire Lance", "1011100", Type.CYCLE, 5, FireLanceSpell::execute, item -> item instanceof FireLance, FireLanceSpell::getDescription, Rarity.UNCOMMON, true);


    public static HashMap<Spells, RegistryObject<Item>> registerAll(DeferredRegister<Item> register) {
        HashMap<Spells, RegistryObject<Item>> map = new HashMap<>();
        MysticcraftMod.sendInfo("loading Spell Scrolls");
        for (Spells spell : values()) {
            if (spell.shouldBeItem) {
                map.put(spell, register.register(spell.REGISTRY_NAME + "_scroll", ()-> new SpellScrollItem(spell)));
            }
        }
        return map;
    }


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
    public final Type TYPE;
    public final double MANA_COST;
    private final Functions.SpellRun run;
    private final Functions.applicationHelper helper;
    private final Supplier<List<Component>> description;
    public final String REGISTRY_NAME;
    private final boolean shouldBeItem;
    public final Rarity RARITY;
    Spells(String name, String castingType, Type type, double manaCost, Functions.SpellRun toRun, Functions.applicationHelper helper, Supplier<List<Component>> description, Rarity rarity, boolean shouldBeItem) {
        this.shouldBeItem = shouldBeItem;
        this.name = TextUtils.removeNumbers(name);
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

    @Override
    public Type getType() {
        return this.TYPE;
    }

    @Override
    public double getDefaultManaCost() {
        return this.MANA_COST;
    }

    @Override
    public Rarity getRarity() {
        return this.RARITY;
    }

    public void execute(LivingEntity living, ItemStack stack) {
        this.run.execute(living, stack);
    }

    public List<Component> getDescription() {
        return description.get();
    }

    public boolean canApply(Item item) {return this.helper.canApply(item);}
    public void addDescription(List<Component> list, SpellItem item, ItemStack ignoredStack, Player player) {
        int spellSlotAmount = item.getSpellSlotAmount();
        list.add(Component.literal("Ability: " + this.getName() + " " + (spellSlotAmount > 1 ? (item.getIndexForSlot(this) + 1) + " / " + item.getSpellSlotAmount() : "")).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GOLD));
        if (this.description.get() != null) list.addAll(this.description.get());
        if (this.MANA_COST > 0 && player != null) {
            list.add(Component.literal(FormattingCodes.GRAY + "Mana-Cost: " + FormattingCodes.DARK_RED + getManaCostForPlayer(player)));
        }
        if (this.castingType != null && item.getSpellSlotAmount() > 1) list.add(Component.literal("Pattern: [" + this.getPattern() + FormattingCodes.RESET + "]"));
    }

    public double getManaCostForPlayer(Player player) {
        AttributeInstance instance = player.getAttribute(ModAttributes.MANA_COST.get());
        return AttributeUtils.getAttributeValue(instance, this.MANA_COST);
    }

    public static Spells get(String pattern) {
        for (Spells spell : values()) {
            String castingType = spell.getCastingType();
            if (Objects.equals(castingType, pattern)) {
                return spell;
            }
        }
        return EMPTY_SPELL;
    }

    public static boolean contains(String pattern) {
        return get(pattern) != EMPTY_SPELL;
    }

    public String getPattern() {
        return getPattern(this.castingType);
    }

    public static String getPattern(String s) {
        String replace = s.replace("0", RED);
        return replace.replace("1", BLUE);
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
