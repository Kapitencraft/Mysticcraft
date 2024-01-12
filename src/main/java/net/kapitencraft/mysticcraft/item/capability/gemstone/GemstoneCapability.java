package net.kapitencraft.mysticcraft.item.capability.gemstone;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.misc.serialization.NbtSerializer;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GemstoneCapability implements ICapabilityProvider, IGemstoneHandler, INBTSerializable<CompoundTag> {
    private static final Codec<GemstoneCapability> CODEC = RecordCodecBuilder.create(
            gemstoneCapabilityInstance -> gemstoneCapabilityInstance.group(
                    GemstoneSlot.CODEC.listOf().fieldOf("Slots").forGetter(i -> List.of(i.slots))
            ).apply(gemstoneCapabilityInstance, GemstoneCapability::create)
    );
    private static final NbtSerializer<GemstoneCapability> SERIALIZER = new NbtSerializer<>(CODEC);
    private GemstoneSlot[] slots;
    private GemstoneSlot[] defaultSlots;

    private static GemstoneCapability create(List<GemstoneSlot> slots) {
        GemstoneCapability capability = new GemstoneCapability();
        capability.slots = slots.toArray(GemstoneSlot[]::new);
        return capability;
    }

    private final LazyOptional<GemstoneCapability> self = LazyOptional.of(() -> this);
    @Override
    public boolean putGemstone(GemstoneType gemstoneType, GemstoneType.Rarity rarity, int slotIndex) {
        GemstoneSlot slot = slots[slotIndex];
        return slot.putGemstone(gemstoneType, rarity);
    }



    @Override
    public GemstoneSlot[] getSlots() {
        return slots;
    }

    public GemstoneSlot[] getDefaultSlots() {
        return defaultSlots;
    }

    public int getSlotAmount() {
        return getDefaultSlots().length;
    }

    @Override
    public void setDefault(GemstoneSlot[] slots) {
        defaultSlots = slots;
        ensureFilledSlots();
    }


    @Override
    public CompoundTag serializeNBT() {
        return (CompoundTag) SERIALIZER.serialize(this);
    }

    private void ensureFilledSlots() {
        if (defaultSlots == null) throw new IllegalStateException("default Slots may not be null!");
        if (slots == null) {
            slots = defaultSlots;
        }
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        GemstoneCapability capability = SERIALIZER.deserialize(nbt);
        this.copy(capability);
    }

    private void copy(GemstoneCapability capability) {
        this.slots = capability.slots;
        ensureFilledSlots();
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == CapabilityHelper.GEMSTONE ? self.cast() : LazyOptional.empty();
    }
}
