package net.kapitencraft.mysticcraft.item.capability.gemstone;

import net.kapitencraft.mysticcraft.api.Reference;
import net.kapitencraft.mysticcraft.helpers.NetworkingHelper;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.NonNullConsumer;
import net.minecraftforge.common.util.NonNullPredicate;

public class GemstoneHelper {

    public static boolean getCapability(ItemStack stack, NonNullConsumer<IGemstoneHandler> handlerConsumer) {
        return CapabilityHelper.exeCapability(stack, CapabilityHelper.GEMSTONE, handlerConsumer);
    }

    public static boolean exCapability(ItemStack stack, NonNullPredicate<IGemstoneHandler> handlerConsumer) {
        Reference<Boolean> ref = Reference.of(false);
        getCapability(stack, iGemstoneHandler -> ref.setValue(handlerConsumer.test(iGemstoneHandler)));
        return ref.getValue();
    }

    public static GemstoneCapability getCapability(ItemStack stack) {
        Reference<IGemstoneHandler> ref = Reference.of(null);
        getCapability(stack, ref::setValue);
        return (GemstoneCapability) ref.getValue();
    }

    public static boolean hasCapability(ItemStack stack) {
        return CapabilityHelper.hasCapability(stack, CapabilityHelper.GEMSTONE);
    }

    public static void writeCapability(FriendlyByteBuf buf, GemstoneCapability capability) {
        NetworkingHelper.writeArray(buf, capability.getSlots(), GemstoneSlot::saveToBytes);
    }

    public static GemstoneCapability readCapability(FriendlyByteBuf buf) {
        return GemstoneCapability.of(NetworkingHelper.readArray(buf, GemstoneSlot[]::new, GemstoneSlot::readFromBytes));
    }
}
