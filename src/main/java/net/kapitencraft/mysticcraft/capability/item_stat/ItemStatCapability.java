package net.kapitencraft.mysticcraft.capability.item_stat;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.*;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class ItemStatCapability implements IItemStatHandler {

    private final LazyOptional<ItemStatCapability> self = LazyOptional.of(()-> this);
    private final Map<Type, Long> valuesForType = new HashMap<>();

    public ItemStatCapability() {
    }

    public boolean has() {
        return !this.valuesForType.isEmpty();
    }

    public void add(Type type) {
        this.valuesForType.put(type, 0L);
    }

    public Collection<Type> getTypes() {
        return valuesForType.keySet();
    }

    @Override
    public long getCurrentValue(Type type) {
        return valuesForType.get(type);
    }

    @Override
    public void increase(Type type, long value) {
        if (valuesForType.containsKey(type))
            valuesForType.put(type, valuesForType.get(type) + value);
    }

    @Override
    public void copyFrom(Map<Type, Long> typeLongMap) {
        this.valuesForType.clear();
        this.valuesForType.putAll(typeLongMap);
    }

    @Override
    public Map<Type, Long> getData() {
        return Map.of();
    }

    public enum Type implements StringRepresentable, Predicate<Item> {
        KILLED("killed", item-> item instanceof AxeItem || item instanceof SwordItem || item instanceof BowItem),
        MINED("mined", item -> item instanceof ShovelItem || item instanceof PickaxeItem),
        FARMED("farmed", item -> item instanceof AxeItem || item instanceof HoeItem);

        public static final EnumCodec<Type> CODEC = StringRepresentable.fromEnum(Type::values);


        private final String name;
        private final Predicate<Item> applicationPredicate;
        Type(String name, Predicate<Item> applicationPredicate) {
            this.name = name;
            this.applicationPredicate = applicationPredicate;
        }

        @Override
        public @NotNull String getSerializedName() {
            return name;
        }


        @Override
        public boolean test(Item item) {
            return applicationPredicate.test(item);
        }
    }
}
