package net.kapitencraft.mysticcraft.misc.content;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.helpers.TagHelper;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

@AutoRegisterCapability
public class EssenceHolder implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    private static final Codec<EssenceHolder> CODEC = RecordCodecBuilder.create(essenceHolderInstance ->
            essenceHolderInstance.group(
            Codec.unboundedMap(EssenceType.CODEC, Codec.INT).fieldOf("content").forGetter(EssenceHolder::getContent)
    ).apply(essenceHolderInstance, EssenceHolder::new));

    private final LazyOptional<EssenceHolder> optional = LazyOptional.of(()-> this);

    private final HashMap<EssenceType, Integer> content = new HashMap<>();

    public EssenceHolder(Map<EssenceType, Integer> content) {
        this.content.putAll(content);
    }

    public EssenceHolder() {

    }

    public HashMap<EssenceType, Integer> getContent() {
        return content;
    }

    public void add(EssenceType type, int toAdd) {
        if (content.containsKey(type)) {
            content.put(type, content.get(type) + toAdd);
        } else {
            content.put(type, toAdd);
        }
    }

    @Override
    public String toString() {
        return "Content: " + content;
    }

    public void remove(EssenceType type, int remove) {
        if (content.containsKey(type)) {
            content.put(type, content.get(type) - remove);
            if (content.get(type) < 0) {
                throw new IllegalStateException("you may not have below 0 essence of type " + type.getId());
            }
        }
    }

    public int tryRemove(EssenceType type, int remove) {
        if (content.containsKey(type)) {
            int amount = content.get(type);
            if (amount > remove) {
                content.put(type, amount - remove);
                return 0;
            }
            else {
                content.put(type, 0);
                return remove - amount;
            }
        } else {
            content.put(type, 0);
            return remove;
        }
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityHelper.ESSENCE) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return (CompoundTag) TagHelper.getOrLog(CODEC.encodeStart(NbtOps.INSTANCE, this), new CompoundTag());
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        copyFrom(TagHelper.getOrLog(CODEC.parse(NbtOps.INSTANCE, nbt), new EssenceHolder()));
    }

    public void copyFrom(EssenceHolder oldStore) {
        this.content.putAll(oldStore.content);
    }
}
