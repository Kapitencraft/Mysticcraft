package net.kapitencraft.mysticcraft.item.capability.gemstone;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.ModCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class GemstoneCapability extends ModCapability<GemstoneCapability, IGemstoneHandler> implements IGemstoneHandler {
    private static final Codec<GemstoneCapability> CODEC = RecordCodecBuilder.create(
            gemstoneCapabilityInstance -> gemstoneCapabilityInstance.group(
                    GemstoneSlot.CODEC.listOf().fieldOf("Slots").forGetter(i -> List.of(i.slots))
            ).apply(gemstoneCapabilityInstance, GemstoneCapability::create)
    );
    private GemstoneSlot[] slots;
    private GemstoneSlot[] defaultSlots;

    public GemstoneCapability() {
        super(CODEC);
    }

    public static GemstoneCapability create(Collection<GemstoneSlot> slots) {
        GemstoneCapability capability = new GemstoneCapability();
        capability.slots = slots.toArray(GemstoneSlot[]::new);
        return capability;
    }

    public static GemstoneCapability of(GemstoneSlot[] slots) {
        GemstoneCapability capability = new GemstoneCapability();
        capability.slots = slots;
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

    private void ensureFilledSlots() {
        if (defaultSlots == null) throw new IllegalStateException("default Slots may not be null!");
        if (slots == null) {
            slots = defaultSlots;
        }
    }

    @Override
    public GemstoneCapability asType() {
        return this;
    }

    public void copy(GemstoneCapability capability) {
        this.slots = capability.slots;

        ensureFilledSlots();
    }

    @Override
    public Capability<IGemstoneHandler> getCapability() {
        return CapabilityHelper.GEMSTONE;
    }

    @Override
    public LazyOptional<GemstoneCapability> get() {
        return self;
    }

    public List<GemstoneSlot> listSlots() {
        return Arrays.asList(slots);
    }
}
