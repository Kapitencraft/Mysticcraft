package net.kapitencraft.mysticcraft.item.capability.containable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.item.capability.ModCapability;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public abstract class ContainableCapability<T extends Item> extends ModCapability<ContainableCapability<T>, IContainable<T>> implements IContainable<T> {
    protected static <T extends Item> Codec<ContainableCapability<T>> createCodec(Function<List<ItemStack>, ContainableCapability<T>> constructor) {
        return RecordCodecBuilder.create(containableCapabilityInstance ->
                containableCapabilityInstance.group(
                        ItemStack.CODEC.listOf().fieldOf("Content").forGetter(ContainableCapability::getContent)
                ).apply(containableCapabilityInstance, constructor)
        );
    }

    private int maxAmount;
    private final LazyOptional<ContainableCapability<T>> self = LazyOptional.of(() -> this);
    private final List<ItemStack> content;

    protected ContainableCapability(Codec<ContainableCapability<T>> codec, List<ItemStack> content) {
        super(codec);
        this.content = new ArrayList<>(content); //necessary as Codecs return immutable lists
    }

    protected ContainableCapability(Codec<ContainableCapability<T>> codec) {
        this(codec, new ArrayList<>());
    }

    public List<ItemStack> getContent() {
        return content;
    }

    @Override
    public void copy(ContainableCapability<T> capability) {
        this.content.clear();
        this.content.addAll(capability.content);
    }

    @Override
    public ContainableCapability<T> asType() {
        return this;
    }

    @Override
    public LazyOptional<ContainableCapability<T>> get() {
        return self;
    }

    @Override
    public int insert(T item, int amount) {
        ItemStack holder = getHolder(item);
        if (holder != null) {
            int growth = maxAmount - holder.getCount();
            holder.grow(growth);
            return amount - growth;
        }
        return amount;
    }

    public ItemStack getHolder(Item item) {
        Optional<ItemStack> optional = this.content.stream().filter(stack -> stack.is(item)).findAny();
        if (optional.isPresent()) return optional.get();
        if (checkCanInsert(item)) {
            ItemStack holder = new ItemStack(item);
            this.content.add(holder);
            return holder;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int remove(Item item, int amount) {
        ItemStack holder = getHolder(item);
        if (holder != null) {
            holder.shrink(amount);
            amount = holder.getCount();
            if (amount < 0) content.remove(holder);
            return amount;
        }
        return amount;
    }

    @Override
    public int amount(Item item) {
        ItemStack holder = getHolder(item);
        return holder == null ? 0 : holder.getCount();
    }

    @Override
    public void setMaxAmount(int amount) {
        this.maxAmount = amount;
    }

    @Override
    public int getMaxAmount() {
        return this.maxAmount;
    }
}
