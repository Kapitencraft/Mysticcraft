package net.kapitencraft.mysticcraft.item.capability.reforging;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.item_bonus.ReforgingBonus;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.kapitencraft.mysticcraft.misc.ModRarities;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
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

    public static Reforge.Builder builder(String registryName) {
        return new Builder(registryName);
    }

    public boolean isOnlyFromStone() {
        return onlyFromStone;
    }

    public JsonObject serialize() {
        JsonObject object = new JsonObject();
        JsonObject mods = new JsonObject();
        final List<Rarity> rarities = List.of(Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.EPIC, ModRarities.LEGENDARY, ModRarities.MYTHIC, ModRarities.DIVINE);
        for (Map.Entry<Attribute, ReforgeStat> entry : statList.entrySet()) {
            JsonArray array = new JsonArray();
            rarities.forEach(rarity -> array.add(entry.getValue().apply(rarity)));
            mods.add(String.valueOf(BuiltInRegistries.ATTRIBUTE.getKey(entry.getKey())), array);
        }
        if (this.bonus != null) {
            object.addProperty("bonus", ReforgeBonuses.byBonus(this.bonus).getSerializedName());
        }
        object.add("mods", mods);
        return object;
    }

    public HashMap<Attribute, Double> applyModifiers(Rarity rarity) {
        HashMap<Attribute, Double> map = new HashMap<>();
        for (Attribute attribute : this.statList.keySet()) {
            map.put(attribute, this.statList.get(attribute).apply(rarity));
        }
        return map;
    }

    public void saveToStack(ItemStack stack) {
        MysticcraftMod.LOGGER.info(Markers.REFORGE_MANAGER, "putting Reforge '{}' to the Stack", this.registryName);
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


    public static boolean reforgeAble(ItemStack stack) {
        if (stack.isEmpty()) return false;
        for (Type type : Type.values()) {
            if (type.mayApply(stack)) {
                return true;
            }
        }
        return false;
    }

    public enum Type implements StringRepresentable {
        MELEE_WEAPON("melee", stack -> stack.getItem() instanceof SwordItem),
        RANGED_WEAPON("ranged", stack -> stack.getItem() instanceof BowItem),
        ARMOR("armor", stack -> stack.getItem() instanceof ArmorItem),
        FISHING_ROD("fishing", stack -> stack.getItem() instanceof FishingRodItem),
        EQUIPMENT("equipment", stack -> !(MELEE_WEAPON.mayApply(stack) || RANGED_WEAPON.mayApply(stack) || ARMOR.mayApply(stack) || FISHING_ROD.mayApply(stack)));

        private static final EnumCodec<Type> CODEC = StringRepresentable.fromEnum(Type::values);

        private final Predicate<ItemStack> applicably;
        private final String name;

        Type(String name, Predicate<ItemStack> applicably) {
            this.applicably = applicably;
            this.name = name;
        }

        public static Type byName(String in) {
            return CODEC.byName(in, ARMOR);
        }

        boolean mayApply(ItemStack stack) {
            return this.applicably.test(stack);
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }

    public enum Functions implements StringRepresentable {
        EMPTY("empty", new ReforgingBonus("Empty") {
            @Override
            public Consumer<List<Component>> getDisplay() {
                return list -> list.add(Component.literal("this bonus is empty"));
            }
        });

        private static final EnumCodec<Functions> CODEC = StringRepresentable.fromEnum(Functions::values);


        private final String name;
        private final ReforgingBonus stat;

        Functions(String name, ReforgingBonus stat) {
            this.name = name;
            this.stat = stat;
        }

        public ReforgingBonus getBonus() {
            return stat;
        }

        public Functions byName(String name) {
            return CODEC.byName(name, EMPTY);
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }
    }
}
