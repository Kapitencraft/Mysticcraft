package net.kapitencraft.mysticcraft.capability.reforging;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.kapitencraft.kap_lib.item.bonus.AbstractBonusElement;
import net.kapitencraft.kap_lib.item.bonus.Bonus;
import net.kapitencraft.kap_lib.registry.ExtraCodecs;
import net.kapitencraft.kap_lib.util.ExtraRarities;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Reforge implements AbstractBonusElement {
    private final MutableComponent name;
    private final HashMap<Attribute, ReforgeStat> statList;
    private final String registryName;
    private final boolean onlyFromStone;
    private final Bonus<?> bonus;
    private final Type type;

    private Reforge(Builder builder) {
        if (builder.registryName == null) {
            throw new NullPointerException("Reforge name may not be null!");
        }
        this.registryName = builder.registryName;
        if (builder.type == null) {
            throw new NullPointerException("Error loading Reforge '" + this.getRegistryName() + "': Reforge type may not be null!");
        }
        this.name = Component.translatable("reforge." + registryName);
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
        final List<Rarity> rarities = List.of(Rarity.COMMON, Rarity.UNCOMMON, Rarity.RARE, Rarity.EPIC, ExtraRarities.LEGENDARY, ExtraRarities.MYTHIC, ExtraRarities.DIVINE);
        for (Map.Entry<Attribute, ReforgeStat> entry : statList.entrySet()) {
            JsonArray array = new JsonArray();
            rarities.forEach(rarity -> array.add(entry.getValue().apply(rarity)));
            mods.add(String.valueOf(ForgeRegistries.ATTRIBUTES.getKey(entry.getKey())), array);
        }
        object.add("mods", mods);
        if (this.bonus != null) {
            DataResult<JsonElement> result = ExtraCodecs.BONUS.encodeStart(JsonOps.INSTANCE, this.bonus);
            result.get().ifLeft(e -> object.add("bonus", e))
                    .ifRight(jsonElementPartialResult -> ReforgeManager.LOGGER.warn(Markers.REFORGE_MANAGER, "unable to save bonus: {}", jsonElementPartialResult.message()));
        }
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
        ReforgeManager.LOGGER.debug(Markers.REFORGE_MANAGER, "putting Reforge '{}' to the Stack", this.registryName);
        stack.getOrCreateTag().putString(Reforges.REFORGE_NAME_ID, this.registryName);
    }

    public boolean hasModifier(Attribute attribute) {
        return this.statList.containsKey(attribute);
    }

    public MutableComponent getName() {
        return name;
    }

    public Type type() {
        return type;
    }

    public String getRegistryName() {
        return registryName;
    }

    @Override
    public boolean isHidden() {
        return false;
    }

    public Bonus<?> getBonus() {
        return bonus;
    }

    @Override
    public ResourceLocation getId() {
        return MysticcraftMod.res(registryName);
    }

    @Override
    public MutableComponent getTitle() {
        return Component.translatable("reforge_bonus.name");
    }

    @Override
    public String getNameId() {
        //TODO add RL
        return "reforge_bonus.mysticcraft." + this.registryName;
    }


    public static @Nullable Reforge getFromStack(ItemStack stack) {
        return Reforges.getReforge(stack);
    }


    public static class Builder {

        private Bonus<?> bonus = null;
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

        public Builder withBonus(Bonus<?> bonus) {
            this.bonus = bonus;
            return this;
        }

        public Builder reforgeType(Type type) {
            this.type = type;
            return this;
        }

        public Reforge build() {
            return new Reforge(this);
        }


        public Builder addStat(Attribute attribute, double... stat) {
            return this.addStat(attribute, ReforgeStat.build(stat));
        }

        public Builder addStat(Attribute attribute, ReforgeStat stat) {
            if (!stats.containsKey(attribute)) {
                stats.put(attribute, stat);
            }
            return this;
        }


        public Builder addStat(Supplier<Attribute> attribute, double... stat) {
            return addStat(attribute.get(), stat);
        }
    }

    @SuppressWarnings("deprecation")
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

    @Override
    public String toString() {
        return "Reforge[" + registryName + "]";
    }
}
