package net.kapitencraft.mysticcraft.item.capability.item_stat;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.ModCapability;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.*;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class ItemStatCapability extends ModCapability<ItemStatCapability, IItemStatHandler> implements IItemStatHandler {
    private static final Codec<ItemStatCapability> CODEC = RecordCodecBuilder.create(
            itemStatCapabilityInstance -> itemStatCapabilityInstance.group(
                    Codec.unboundedMap(Type.CODEC, Codec.LONG).fieldOf("valuesForTypes").forGetter(i -> i.valuesForType)
            ).apply(itemStatCapabilityInstance, ItemStatCapability::new)
    );

    private final LazyOptional<ItemStatCapability> self = LazyOptional.of(()-> this);
    private final Map<Type, Long> valuesForType = new HashMap<>();

    public ItemStatCapability() {
        super(CODEC);
    }

    public ItemStatCapability(Map<Type, Long> valuesForType) {
        this();
        this.valuesForType.putAll(valuesForType);
    }

    public static ItemStatCapability read(FriendlyByteBuf buf) {
        return new ItemStatCapability(buf.readMap(buf1 -> buf1.readEnum(Type.class), FriendlyByteBuf::readLong));
    }

    public static void write(FriendlyByteBuf buf, ItemStatCapability capability) {
        buf.writeMap(capability.valuesForType, FriendlyByteBuf::writeEnum, FriendlyByteBuf::writeLong);
    }

    public boolean has() {
        return this.valuesForType.size() > 0;
    }

    @Override
    public void copy(ItemStatCapability capability) {
        this.valuesForType.clear();
        this.valuesForType.putAll(capability.valuesForType);
    }

    @Override
    public ItemStatCapability asType() {
        return this;
    }

    public void add(Type type) {
        this.valuesForType.put(type, 0L);
    }

    @Override
    public Capability<IItemStatHandler> getCapability() {
        return CapabilityHelper.ITEM_STAT;
    }

    public Collection<Type> getTypes() {
        return valuesForType.keySet();
    }

    @Override
    public LazyOptional<ItemStatCapability> get() {
        return self;
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
