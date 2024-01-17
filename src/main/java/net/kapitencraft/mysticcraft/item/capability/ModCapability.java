package net.kapitencraft.mysticcraft.item.capability;

import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.misc.serialization.NbtSerializer;
import net.minecraft.core.Direction;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public abstract class ModCapability<T extends ModCapability<T, K>, K> implements INBTSerializable<Tag>, IModCapability<T, K>, ICapabilityProvider {
    private final NbtSerializer<T> serializer;

    protected ModCapability(Codec<T> serializer) {
        this.serializer = new NbtSerializer<>(serializer);
    }

    @Override
    public Tag serializeNBT() {
        return serializer.serialize((T) this);
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        T data = serializer.deserialize(nbt);
        if (data == null) MysticcraftMod.LOGGER.warn("detected empty capability, skipping!");
        else this.copy(data);
    }

    @Override
    public @NotNull <I> LazyOptional<I> getCapability(@NotNull Capability<I> cap, Direction side) {
        return cap == getCapability() ? get().cast() : LazyOptional.empty();
    }
}
