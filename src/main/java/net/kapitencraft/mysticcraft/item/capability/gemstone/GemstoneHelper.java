package net.kapitencraft.mysticcraft.item.capability.gemstone;

import net.kapitencraft.kap_lib.helpers.NetworkHelper;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.NonNullConsumer;
import net.minecraftforge.common.util.NonNullFunction;
import net.minecraftforge.common.util.NonNullPredicate;

import java.util.Optional;

public class GemstoneHelper {

    public static boolean getCapability(ItemStack stack, NonNullConsumer<IGemstoneHandler> handlerConsumer) {
        return CapabilityHelper.exeCapability(stack, CapabilityHelper.GEMSTONE, handlerConsumer);
    }

    public static boolean exCapability(ItemStack stack, NonNullPredicate<IGemstoneHandler> handlerConsumer) {
        return stack.getCapability(CapabilityHelper.GEMSTONE)
                .map(handlerConsumer::test)
                .orElse(false);
    }

    public static <T> Optional<T> transformCapability(ItemStack stack, NonNullFunction<IGemstoneHandler, T> function) {
        return stack.getCapability(CapabilityHelper.GEMSTONE)
                .map(function);
    }

    public static GemstoneCapability getCapability(ItemStack stack) {
        return (GemstoneCapability) transformCapability(stack, h -> h).orElse(null);
    }

    public static boolean hasCapability(ItemStack stack) {
        return CapabilityHelper.hasCapability(stack, CapabilityHelper.GEMSTONE);
    }

    public static void writeCapability(FriendlyByteBuf buf, GemstoneCapability capability) {
        NetworkHelper.writeArray(buf, capability.getSlots(), GemstoneSlot::toNw);
    }
}
