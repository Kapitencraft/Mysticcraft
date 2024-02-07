package net.kapitencraft.mysticcraft.item.capability;

import net.kapitencraft.mysticcraft.api.Reference;
import net.kapitencraft.mysticcraft.item.capability.elytra.IElytraData;
import net.kapitencraft.mysticcraft.item.capability.gemstone.IGemstoneHandler;
import net.kapitencraft.mysticcraft.misc.content.EssenceHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullConsumer;
import net.minecraftforge.common.util.NonNullPredicate;

import java.util.function.Function;
import java.util.function.Predicate;

public class CapabilityHelper {
    public static final Capability<IGemstoneHandler> GEMSTONE = CapabilityManager.get(new CapabilityToken<>(){});
    public static final Capability<IElytraData> ELYTRA = CapabilityManager.get(new CapabilityToken<>(){});
    public static final Capability<EssenceHolder> ESSENCE = CapabilityManager.get(new CapabilityToken<>(){});

    public static <K> boolean exeCapability(ItemStack stack, Capability<K> capability, NonNullConsumer<K> consumer) {
        LazyOptional<K> optional = stack.getCapability(capability);
        optional.ifPresent(consumer);
        return optional.isPresent();
    }

    public static <K> boolean testCapability(ItemStack stack, Capability<K> capability, NonNullPredicate<K> predicate) {
        Reference<Boolean> reference = Reference.of(false);
        return exeCapability(stack, capability, k -> reference.setValue(predicate.test(k))) || reference.getValue();
    }

    public static <K> boolean hasCapability(ItemStack stack, Capability<K> capability) {
        return stack.getCapability(capability).isPresent();
    }

    public static <T> Predicate<ItemStack> hasCapPredicate(Capability<T> capability) {
        return stack -> hasCapability(stack, capability);
    }

    public static <T extends ICapability<K>, K> Function<ItemStack, K> getCapFunc(Capability<T> capability) {
        return stack -> {
            Reference<T> reference = Reference.of(null);
            exeCapability(stack, capability, reference::setValue);
            return reference.getValue().asType();
        };
    }

    public static boolean hasElytraCapability(ItemStack stack) {
        return hasCapability(stack, ELYTRA);
    }
}
