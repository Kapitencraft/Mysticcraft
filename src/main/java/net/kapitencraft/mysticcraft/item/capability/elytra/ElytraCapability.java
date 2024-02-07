package net.kapitencraft.mysticcraft.item.capability.elytra;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.kapitencraft.mysticcraft.item.capability.ModCapability;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import java.util.Arrays;


public class ElytraCapability extends ModCapability<ElytraCapability, IElytraData> implements IElytraData {
    private ElytraData data;
    private int level;

    private final LazyOptional<ElytraCapability> self = LazyOptional.of(()-> this);
    private static final Codec<ElytraCapability> CODEC = RecordCodecBuilder.create(elytraCapabilityInstance ->
            elytraCapabilityInstance.group(
                    ElytraData.CODEC.fieldOf("data").forGetter(i -> i.data),
                    Codec.INT.fieldOf("level").forGetter(i -> i.level)
            ).apply(elytraCapabilityInstance, ElytraCapability::new)
    );

    public ElytraCapability() {
        super(CODEC);
    }

    public ElytraCapability(ElytraData data, int i) {
        this();
        this.data = data;
        this.level = i;
    }

    public static void write(FriendlyByteBuf buf, ElytraCapability capability) {
        buf.writeEnum(capability.data);
        buf.writeInt(capability.level);
    }

    public static ElytraCapability read(FriendlyByteBuf buf) {
        return new ElytraCapability(buf.readEnum(ElytraData.class), buf.readInt());
    }

    public static void merge(ItemStack stack, ItemStack stack1) {
        CapabilityHelper.exeCapability(stack, CapabilityHelper.ELYTRA, iElytraData ->
                CapabilityHelper.exeCapability(stack1, CapabilityHelper.ELYTRA, iElytraData1 -> {
                    int level = iElytraData.getLevel();
                    if (level == iElytraData1.getLevel() && level < iElytraData.getData().getMaxLevel()) {
                        iElytraData.setLevel(level+1);
                    }
                })
        );
    }

    public static ElytraCapability create() {
        ElytraCapability capability = new ElytraCapability();
        capability.data = MathHelper.pickRandom(Arrays.stream(ElytraData.values()).toList());
        capability.level = 1;
        return capability;
    }

    @Override
    public void copy(ElytraCapability capability) {
        this.data = capability.data;
        this.level = capability.level;
    }

    @Override
    public ElytraCapability asType() {
        return this;
    }

    @Override
    public Capability<IElytraData> getCapability() {
        return CapabilityHelper.ELYTRA;
    }

    @Override
    public LazyOptional<ElytraCapability> get() {
        return self;
    }

    @Override
    public ElytraData getData() {
        return data;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

}
