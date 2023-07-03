package net.kapitencraft.mysticcraft.item.reforging;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.item_bonus.ReforgingBonus;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.function.Predicate;

public class Reforge {
    private final MutableComponent name;
    private final HashMap<Attribute, ReforgeStat> statList;
    private final String registryName;
    private final boolean onlyFromStone;
    private final ReforgingBonus bonus;
    private final Type type;

    private Reforge(Builder builder) {
        if (builder.registryName == null) {
            throw new NullPointerException("Reforge name may not be null!");
        }
        this.registryName = builder.registryName;
        if (builder.type == null) {
            throw new NullPointerException("Error loading Reforge '" + this.getRegistryName() + "': Reforge type may not be null!");
        }
        this.name = builder.name == null ? Component.translatable("reforge." + registryName) : builder.name;
        this.bonus = builder.bonus;
        this.onlyFromStone = builder.onlyFromStone;
        this.type = builder.type;
        this.statList = builder.stats;
    }

    public boolean isOnlyFromStone() {
        return onlyFromStone;
    }

    public HashMap<Attribute, Double> applyModifiers(Rarity rarity) {
        HashMap<Attribute, Double> map = new HashMap<>();
        for (Attribute attribute : this.statList.keySet()) {
            map.put(attribute, this.statList.get(attribute).apply(rarity));
        }
        return map;
    }

    public void saveToStack(ItemStack stack) {
        MysticcraftMod.sendInfo("putting Reforge '" + this.registryName + "' to the Stack");
        stack.getOrCreateTag().putString(Reforges.REFORGE_NAME_ID, this.registryName);
    }

    public boolean hasModifier(Attribute attribute) {
        return this.statList.containsKey(attribute);
    }

    public MutableComponent getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public String getRegistryName() {
        return registryName;
    }

    public ReforgingBonus getBonus() {
        return bonus;
    }


    public static @Nullable Reforge getFromStack(ItemStack stack) {

        return Reforges.getReforge(stack);
    }


    public static class Builder {

        private @Nullable MutableComponent name = null;
        private @Nullable ReforgingBonus bonus;
        private final String registryName;
        private final HashMap<Attribute, ReforgeStat> stats = new HashMap<>();
        private boolean onlyFromStone = false;
        private Type type;


        public Builder(String registryName) {
            this.registryName = registryName;
        }

        public Builder onlyFromStone() {
            this.onlyFromStone = true;
            return this;
        }

        public Builder withBonus(ReforgingBonus bonus) {
            this.bonus = bonus;
            return this;
        }

        public Builder withName(MutableComponent name) {
            this.name = name;
            return this;
        }

        public Builder reforgeType(Type type) {
            this.type = type;
            return this;
        }

        public Reforge build() {
            return new Reforge(this);
        }


        public Builder addStat(Attribute attribute, ReforgeStat stat) {
            if (!stats.containsKey(attribute)) {
                stats.put(attribute, stat);
            }
            return this;
        }
    }

    public enum Type {
        MELEE_WEAPON(stack -> stack.getItem() instanceof SwordItem),
        RANGED_WEAPON(stack -> stack.getItem() instanceof BowItem),
        ARMOR(stack -> stack.getItem() instanceof ArmorItem),
        FISHING_ROD(stack -> stack.getItem() instanceof FishingRodItem),
        EQUIPMENT(stack -> !(MELEE_WEAPON.mayApply(stack) || RANGED_WEAPON.mayApply(stack) || ARMOR.mayApply(stack) || FISHING_ROD.mayApply(stack)));

        private final Predicate<ItemStack> applicably;

        Type(Predicate<ItemStack> applicably) {
            this.applicably = applicably;
        }

        boolean mayApply(ItemStack stack) {
            return this.applicably.test(stack);
        }
    }
}
