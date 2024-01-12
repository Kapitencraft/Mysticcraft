package net.kapitencraft.mysticcraft.item.capability;

import net.kapitencraft.mysticcraft.api.Reference;
import net.kapitencraft.mysticcraft.item.capability.elytra.IElytraData;
import net.kapitencraft.mysticcraft.item.capability.gemstone.IGemstoneHandler;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class CapabilityHelper {
    public static final Capability<IGemstoneHandler> GEMSTONE = CapabilityManager.get(new CapabilityToken<>(){});
    public static final Capability<IElytraData> ELYTRA = CapabilityManager.get(new CapabilityToken<>() {});

    public static <T, K> @Nullable T doWith(ItemStack stack, Capability<K> capability, Function<K, T> mapper) {
        return doWith(stack.getCapability(capability), mapper);
    }

    public static <T, K> @Nullable T doWith(LazyOptional<K> optional, Function<K, T> mapper) {
        Reference<T> reference = Reference.of(null);
        optional.ifPresent(k -> reference.setValue(mapper.apply(k)));
        return reference.getValue();
    }
}
