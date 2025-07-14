package net.kapitencraft.mysticcraft.capability.elytra;

import com.mojang.datafixers.util.Pair;
import net.kapitencraft.kap_lib.helpers.MathHelper;
import net.kapitencraft.mysticcraft.capability.CapabilityHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;


public class ElytraCapability implements IElytraData {
    private ElytraData data;
    private int level;

    public ElytraCapability(ElytraData data, int level) {
        this.data = data;
        this.level = level;
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
                    if (level == iElytraData1.getLevel() && level < iElytraData.getDataType().getMaxLevel()) {
                        iElytraData.setLevel(level+1);
                    }
                })
        );
    }

    public static ElytraCapabilityProvider create() {
        return new ElytraCapabilityProvider(new ElytraCapability(MathHelper.pickRandom(Arrays.stream(ElytraData.values()).toList()), 1));
    }

    @Override
    public void copyFrom(Pair<ElytraData, Integer> elytraData) {
        this.data = elytraData.getFirst();
        this.level = elytraData.getSecond();
    }

    @Override
    public Pair<ElytraData, Integer> getData() {
        return Pair.of(this.data, this.level);
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public ElytraData getDataType() {
        return data;
    }
}
