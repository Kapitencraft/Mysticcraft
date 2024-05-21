package net.kapitencraft.mysticcraft.item.capability;

import net.kapitencraft.mysticcraft.api.Reference;
import net.kapitencraft.mysticcraft.item.capability.containable.QuiverCapability;
import net.kapitencraft.mysticcraft.item.capability.elytra.IElytraData;
import net.kapitencraft.mysticcraft.item.capability.gemstone.IGemstoneHandler;
import net.kapitencraft.mysticcraft.item.capability.item_stat.IItemStatHandler;
import net.kapitencraft.mysticcraft.misc.content.EssenceHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullConsumer;
import net.minecraftforge.common.util.NonNullFunction;
import net.minecraftforge.common.util.NonNullPredicate;

import java.util.function.Function;
import java.util.function.Predicate;

public interface CapabilityHelper {
    Capability<IGemstoneHandler> GEMSTONE = CapabilityManager.get(new CapabilityToken<>(){});
    Capability<IElytraData> ELYTRA = CapabilityManager.get(new CapabilityToken<>(){});
    Capability<EssenceHolder> ESSENCE = CapabilityManager.get(new CapabilityToken<>(){});
    Capability<IItemStatHandler> ITEM_STAT = CapabilityManager.get(new CapabilityToken<>(){});
    Capability<QuiverCapability> QUIVER = CapabilityManager.get(new CapabilityToken<>(){});

    static <K> boolean exeCapability(ItemStack stack, Capability<K> capability, NonNullConsumer<K> consumer) {
        LazyOptional<K> optional = stack.getCapability(capability);
        optional.ifPresent(consumer);
        return optional.isPresent();
    }

    static <K> boolean testCapability(ItemStack stack, Capability<K> capability, NonNullPredicate<K> predicate) {
        Boolean bool = mapCapability(stack, capability, predicate::test);
        return bool != null && bool;
    }

    static <K, T> T mapCapability(ItemStack stack, Capability<K> capability, NonNullFunction<K, T> mapper) {
        return stack.getCapability(capability).map(mapper).orElse(null);
    }

    static <K> boolean hasCapability(ItemStack stack, Capability<K> capability) {
        return stack.getCapability(capability).isPresent();
    }

    static <T> Predicate<ItemStack> hasCapPredicate(Capability<T> capability) {
        return stack -> hasCapability(stack, capability);
    }

    static <T extends ICapability<K>, K> Function<ItemStack, K> getCapFunc(Capability<T> capability) {
        return stack -> {
            Reference<T> reference = Reference.of(null);
            exeCapability(stack, capability, reference::setValue);
            return reference.getValue().asType();
        };
    }

    static boolean hasElytraCapability(ItemStack stack) {
        return hasCapability(stack, ELYTRA);
    }
}
