package net.kapitencraft.mysticcraft.capability.reforging;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.kapitencraft.kap_lib.inventory.wearable.IWearable;
import net.kapitencraft.kap_lib.item.bonus.AbstractBonusElement;
import net.kapitencraft.kap_lib.item.bonus.Bonus;
import net.kapitencraft.kap_lib.registry.ExtraCodecs;
import net.kapitencraft.kap_lib.util.ExtraRarities;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.Tags;
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
    private final ResourceLocation registryName;
    private final boolean onlyFromStone;
    private final Bonus<?> bonus;
    private final Type type;

    private Reforge(Builder builder, ResourceLocation location) {
        this.registryName = location;
        if (builder.type == null) {
            throw new NullPointerException("Error loading Reforge '" + this.getRegistryName() + "': Reforge type may not be null!");
        }
        this.name = Component.translatable("reforge." + registryName);
        this.bonus = builder.bonus;
        this.onlyFromStone = builder.onlyFromStone;
        this.type = builder.type;
        this.statList = builder.stats;
    }

    public static Reforge.Builder builder() {
        return new Builder();
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
        stack.getOrCreateTag().putString(Reforges.REFORGE_NAME_ID, this.registryName.toString());
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

    public ResourceLocation getRegistryName() {
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
        return registryName;
    }

    @Override
    public MutableComponent getTitle() {
        return Component.translatable("reforge_bonus.name");
    }

    @Override
    public String getNameId() {
        return Util.makeDescriptionId("reforge_bonus", registryName);
    }


    public static @Nullable Reforge getFromStack(ItemStack stack) {
        return Reforges.getReforge(stack);
    }


    public static class Builder {

        private Bonus<?> bonus = null;
        private final HashMap<Attribute, ReforgeStat> stats = new HashMap<>();
        private boolean onlyFromStone = false;
        private Type type;

        public Builder() {
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

        public Reforge build(ResourceLocation location) {
            return new Reforge(this, location);
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
        MELEE_WEAPON("melee", stack -> stack.is(ItemTags.SWORDS)),
        RANGED_WEAPON("ranged", stack -> stack.is(Tags.Items.TOOLS_BOWS) || stack.is(Tags.Items.TOOLS_CROSSBOWS)),
        ARMOR("armor", stack -> stack.is(Tags.Items.ARMORS) || stack.is(Items.ELYTRA)), //TODO add custom elytra support
        FISHING_ROD("fishing", stack -> stack.is(Tags.Items.TOOLS_FISHING_RODS)),
        EQUIPMENT("equipment", stack -> stack.getItem() instanceof IWearable);

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
