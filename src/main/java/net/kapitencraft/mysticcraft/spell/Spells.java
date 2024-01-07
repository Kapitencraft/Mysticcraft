package net.kapitencraft.mysticcraft.spell;

import net.kapitencraft.mysticcraft.helpers.AttributeHelper;
import net.kapitencraft.mysticcraft.helpers.CollectionHelper;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.TextHelper;
import net.kapitencraft.mysticcraft.init.ModAttributes;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.combat.spells.AspectOfTheVoidItem;
import net.kapitencraft.mysticcraft.item.combat.spells.FireLance;
import net.kapitencraft.mysticcraft.item.combat.spells.IFireScytheItem;
import net.kapitencraft.mysticcraft.item.combat.spells.SpellScrollItem;
import net.kapitencraft.mysticcraft.item.combat.spells.necron_sword.NecronSword;
import net.kapitencraft.mysticcraft.item.data.spell.ISpellItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.kapitencraft.mysticcraft.misc.ModRarities;
import net.kapitencraft.mysticcraft.spell.spells.*;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

@Deprecated //change to new System after 1.0
public enum Spells implements Spell {
    WITHER_IMPACT("Wither Impact", "1101110", Type.RELEASE, 300, WitherImpactSpell::execute, item -> item instanceof NecronSword, WitherImpactSpell::getDescription, Rarity.RARE, true, 0, Elements.SHADOW),
    WITHER_SHIELD("Wither Shield", "1101111", Type.RELEASE, 150, WitherShieldSpell::execute, item -> item instanceof NecronSword, WitherShieldSpell::getDescription, Rarity.RARE, true, 0, Elements.EARTH, Elements.SHADOW),
    IMPLOSION("Implosion", "0000000", Type.RELEASE, 300, ImplosionSpell::execute, item -> item instanceof NecronSword, ImplosionSpell::getDescription, ModRarities.LEGENDARY, true, 0, Elements.FIRE, Elements.SHADOW),
    INSTANT_TRANSMISSION("Instant Transmission", "1110111", Type.RELEASE, 50, InstantTransmissionSpell::execute, item -> true, InstantTransmissionSpell::getDescription, Rarity.COMMON, true, 0, Elements.VOID),
    ETHER_WARP("Ether Transmission", "1000111", Type.RELEASE, 50, EtherWarpSpell::execute, item -> item instanceof AspectOfTheVoidItem, EtherWarpSpell::getDescription, Rarity.EPIC, true, 20, Elements.VOID, Elements.AIR),
    EXPLOSIVE_SIGHT("Explosive Sight", "1010110", Type.RELEASE, 150, ExplosiveSightSpell::execute, item -> true, ExplosiveSightSpell::getDescription, Rarity.UNCOMMON, true, 600, Elements.FIRE),
    EMPTY_SPELL("Empty Spell", null, Type.RELEASE, 0, (living, itemStack)-> true, item -> false, EmptySpell::getDescription, Rarity.UNCOMMON, false, 0),
    SHADOW_STEP("Shadow Step", "1110011", Type.RELEASE, 80, ShadowStepSpell::execute, item -> true, ShadowStepSpell::getDescription, Rarity.EPIC, true, 1200, Elements.SHADOW),
    HUGE_HEAL("Huge Heal", "0011011", Type.RELEASE, 70, HugeHealSpell::execute, item -> true, HugeHealSpell::getDescription, Rarity.UNCOMMON, true, 40, Elements.WATER),
    FIRE_BOLT_1("Fire Bolt 1", "0110011", Type.RELEASE, 50, createFireBold(1, false), item -> item instanceof IFireScytheItem, createFireBoldDesc(1f), Rarity.UNCOMMON, true, 0, Elements.FIRE),
    FIRE_BOLT_2("Fire Bolt 2", "0110011", Type.RELEASE, 50, createFireBold(1.4, false), item -> item instanceof IFireScytheItem, createFireBoldDesc(1.4f), Rarity.UNCOMMON, true, 0, Elements.FIRE),
    FIRE_BOLT_3("Fire Bolt 3", "0110011", Type.RELEASE, 50, createFireBold(2.8, true), item -> item instanceof IFireScytheItem, createFireBoldDesc(2.8f), Rarity.UNCOMMON, true, 0, Elements.FIRE),
    FIRE_BOLT_4("Fire Bolt 4", "0110011", Type.RELEASE, 50, createFireBold(5.2, true), item -> item instanceof IFireScytheItem, createFireBoldDesc(5.2f), Rarity.UNCOMMON, true, 0, Elements.FIRE),
    FIRE_LANCE("Fire Lance", "1011100", Type.CYCLE, 5, FireLanceSpell::execute, item -> item instanceof FireLance, FireLanceSpell::getDescription, Rarity.UNCOMMON, true, 0,     Elements.FIRE, Elements.AIR);

    private static final TabGroup SPELL_GROUP = new TabGroup(TabRegister.TabTypes.SPELL);
    private static final List<Spells> WITHOUT_EMPTY = CollectionHelper.remove(values(), EMPTY_SPELL);
    public static HashMap<Spells, RegistryObject<SpellScrollItem>> registerAll() {
        return ModItems.createRegistry(SpellScrollItem::new, (spell) -> spell.REGISTRY_NAME + "_spell_scroll", WITHOUT_EMPTY, SPELL_GROUP);
    }


    private static BiPredicate<LivingEntity, ItemStack> createFireBold(double baseDamage, boolean explosive) {
        return (user, stack) -> {
            FireBoltProjectile projectile = FireBoltProjectile.createProjectile(user.level, user, explosive, baseDamage, "Fire Bolt");
            projectile.shootFromRotation(user, user.getXRot(), user.getYRot(), 0, 2, 1);
            projectile.setBaseDamage(baseDamage);
            user.level.addFreshEntity(projectile);
            return true;
        };
    }
    private static Supplier<List<Component>> createFireBoldDesc(float damage) {
        return ()-> List.of(Component.literal("Fires a Fire Bolt dealing"), Component.literal(TextHelper.wrapInRed(damage) + " Base Ability Damage"));
    }
    private final String castingType, name;
    public final Type TYPE;
    public final double MANA_COST;
    private final BiPredicate<LivingEntity, ItemStack> run;
    private final Predicate<Item> helper;
    private final Supplier<List<Component>> description;
    public final String REGISTRY_NAME;
    public final Rarity RARITY;
    private final List<Element> elements;
    private final int cooldown;

    Spells(String name, String castingType, Type type, double manaCost, BiPredicate<LivingEntity, ItemStack> toRun, Predicate<Item> helper, Supplier<List<Component>> description, Rarity rarity, boolean shouldBeItem, int cooldown, Element... elements) {
        this.name = TextHelper.removeNumbers(name);
        this.TYPE = type;
        this.MANA_COST = manaCost;
        this.run = toRun;
        this.helper = helper;
        this.description = description;
        this.castingType = castingType;
        this.REGISTRY_NAME = name.toLowerCase().replace(" ", "_");
        this.RARITY = rarity;
        this.cooldown = cooldown;
        this.elements = List.of(elements);
    }

    private int getRemainingCooldownTicks(LivingEntity player) {
        return player.getPersistentData().getCompound("SpellCooldowns").getInt(this.name);
    }

    @Override
    public List<Element> elements() {
        return elements;
    }

    public String getCastingType() {
        return castingType;
    }

    @Override
    public Consumer<List<Component>> getDisplay() {
        return list -> list.addAll(description.get());
    }

    @Override
    public String getSuperName() {
        return null;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public Type getType() {
        return this.TYPE;
    }

    @Override
    public String getRegistryName() {
        return REGISTRY_NAME;
    }

    @Override
    public double getDefaultManaCost() {
        return this.MANA_COST;
    }

    @Override
    public int getCooldown() {
        return cooldown;
    }

    @Override
    public Rarity getRarity() {
        return this.RARITY;
    }

    public boolean execute(LivingEntity living, ItemStack stack) {
        boolean success = false;
        CompoundTag SpellCooldowns = living.getPersistentData().getCompound("SpellCooldowns");
        int cooldown = SpellCooldowns.getInt(this.name);
        if (cooldown < 1) {
            success = this.run.test(living, stack);
            cooldown =MathHelper.cooldown(living, this.cooldown);
        } else {
            cooldown--;
        }
        SpellCooldowns.putInt(this.name, cooldown);
        return success;
    }

    public List<Component> getDescription() {
        return description.get();
    }

    public boolean canApply(Item item) {return this.helper.test(item);}
    public void addDescription(List<Component> list, ISpellItem item, ItemStack stack, Player player) {
        int spellSlotAmount = item.getSlotAmount();
        list.add(Component.literal("Ability: " + this.getName() + " " + (spellSlotAmount > 1 ? (item.getIndexForSlot(stack, this) + 1) + " / " + item.getSlotAmount() : "")).withStyle(ChatFormatting.BOLD).withStyle(ChatFormatting.GOLD));
        if (this.description.get() != null) list.addAll(this.description.get());
        if (this.MANA_COST > 0 && player != null) {
            list.add(Component.literal("Mana-Cost: §4" + getManaCostForPlayer(player)).withStyle(ChatFormatting.DARK_GRAY));
        }
        if (this.castingType != null && item.getSlotAmount() > 1) list.add(Component.literal("Pattern: [" + this.getPattern() + "§r]"));
        if (this.cooldown != 0 && player != null) {
            int cooldownTicks = getRemainingCooldownTicks(player);
            list.add(Component.literal("Cooldown: " + (cooldownTicks > 0 ? TextHelper.wrapInRed("ACTIVE") + "(" + MathHelper.round(cooldownTicks / 20., 1) + "s)" : "INACTIVE, " + MathHelper.round(MathHelper.cooldown(player, this.cooldown) / 20., 1) + "s")).withStyle(ChatFormatting.DARK_GRAY));
        }
    }

    public double getManaCostForPlayer(Player player) {
        AttributeInstance instance = player.getAttribute(ModAttributes.MANA_COST.get());
        return MathHelper.round(AttributeHelper.getAttributeValue(instance, this.MANA_COST), 2);
    }

    public static Spells get(String pattern) {
        for (Spells spell : WITHOUT_EMPTY) {
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

    private static final String BLUE = "§9\u2726";
    private static final String RED = "§6\u2724";
    public static String getStringForPattern(String pattern, boolean fromTooltip) {
        if (fromTooltip) {
            String replace1 = pattern.replace("Pattern: [", "");
            pattern = replace1.replace("§r]", "");
        }
        String replace = pattern.replace(RED, "0");
        return replace.replace(BLUE, "1");
    }

    public static Spells getByName(String name) {
        for (Spells spells : WITHOUT_EMPTY) {
            if (Objects.equals(spells.REGISTRY_NAME, name)) {
                return spells;
            }
        }
        return EMPTY_SPELL;
    }
}
