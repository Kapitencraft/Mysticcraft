package net.kapitencraft.mysticcraft.capability.reforging;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.kapitencraft.kap_lib.bonus.AbstractBonusElement;
import net.kapitencraft.kap_lib.bonus.Bonus;
import net.kapitencraft.kap_lib.core.util.ExtraRarities;
import net.kapitencraft.kap_lib.inventory_page.wearable.IWearable;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.kapitencraft.mysticcraft.registry.ModDataComponentTypes;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class Reforge implements AbstractBonusElement {
    public static final ResourceLocation MODIFIER_ID = MysticcraftMod.res("reforge");

    private final MutableComponent name;
    private final HashMap<Holder<Attribute>, ReforgeStat> statList;
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
        for (Map.Entry<Holder<Attribute>, ReforgeStat> entry : statList.entrySet()) {
            JsonArray array = new JsonArray();
            rarities.forEach(rarity -> array.add(entry.getValue().apply(rarity)));
            mods.add(String.valueOf(entry.getKey().getKey().location()), array);
        }
        object.add("mods", mods);
        if (this.bonus != null) {
            DataResult<JsonElement> result = Bonus.CODEC.encodeStart(JsonOps.INSTANCE, this.bonus);
            result.resultOrPartial(s -> ReforgeManager.LOGGER.warn(Markers.REFORGE_MANAGER, "unable to save bonus: {}", s))
                    .ifPresent(e -> object.add("bonus", e));
        }
        return object;
    }

    public HashMap<Holder<Attribute>, Double> applyModifiers(Rarity rarity) {
        HashMap<Holder<Attribute>, Double> map = new HashMap<>();
        for (Holder<Attribute> attribute : this.statList.keySet()) {
            map.put(attribute, this.statList.get(attribute).apply(rarity));
        }
        return map;
    }

    public void saveToStack(ItemStack stack) {
        ReforgeManager.LOGGER.debug(Markers.REFORGE_MANAGER, "putting Reforge '{}' to the Stack", this.registryName);
        stack.set(ModDataComponentTypes.REFORGE, this);
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
        private final HashMap<Holder<Attribute>, ReforgeStat> stats = new HashMap<>();
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


        public Builder addStat(Holder<Attribute> attribute, double... stat) {
            return this.addStat(attribute, ReforgeStat.build(stat));
        }

        public Builder addStat(Holder<Attribute> attribute, ReforgeStat stat) {
            if (!stats.containsKey(attribute)) {
                stats.put(attribute, stat);
            }
            return this;
        }
    }

    @SuppressWarnings("deprecation")
    public enum Type implements StringRepresentable {
        MELEE_WEAPON("melee", stack -> stack.is(ItemTags.SWORDS)),
        RANGED_WEAPON("ranged", stack -> stack.is(Tags.Items.TOOLS_BOW) || stack.is(Tags.Items.TOOLS_CROSSBOW)),
        ARMOR("armor", stack -> stack.is(ItemTags.EQUIPPABLE_ENCHANTABLE)),
        FISHING_ROD("fishing", stack -> stack.is(Tags.Items.TOOLS_FISHING_ROD)),
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
