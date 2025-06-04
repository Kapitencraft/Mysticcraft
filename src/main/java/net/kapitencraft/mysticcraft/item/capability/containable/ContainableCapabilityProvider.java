package net.kapitencraft.mysticcraft.item.capability.containable;

import com.mojang.serialization.Codec;
import net.kapitencraft.kap_lib.item.capability.CapabilityProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;

import java.util.List;

public class ContainableCapabilityProvider<T extends Item, C extends IContainable<T>> extends CapabilityProvider<List<ItemStack>, C> {
    private static final Codec<List<ItemStack>> CODEC = ItemStack.CODEC.listOf();

    public ContainableCapabilityProvider(C object, Capability<C> capability) {
        super(object, CODEC, capability);
    }

    @Override
    protected List<ItemStack> fallback() {
        return List.of();
    }
}
