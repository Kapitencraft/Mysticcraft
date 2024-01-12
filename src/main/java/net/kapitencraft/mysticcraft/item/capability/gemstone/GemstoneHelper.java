package net.kapitencraft.mysticcraft.item.capability.gemstone;

import net.kapitencraft.mysticcraft.api.Reference;
import net.kapitencraft.mysticcraft.item.capability.CapabilityHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.common.util.NonNullConsumer;
import net.minecraftforge.common.util.NonNullPredicate;

public class GemstoneHelper {

    public static boolean getCapability(ItemStack stack, NonNullConsumer<IGemstoneHandler> handlerConsumer) {
        LazyOptional<IGemstoneHandler> optional = stack.getCapability(CapabilityHelper.GEMSTONE);
        optional.ifPresent(handlerConsumer);
        return optional.isPresent();
    }

    public static boolean exCapability(ItemStack stack, NonNullPredicate<IGemstoneHandler> handlerConsumer) {
        Reference<Boolean> ref = Reference.of(false);
        getCapability(stack, iGemstoneHandler -> ref.setValue(handlerConsumer.test(iGemstoneHandler)));
        return ref.getValue();
    }

    public static boolean hasCapability(ItemStack stack) {
        return stack.getCapability(CapabilityHelper.GEMSTONE).isPresent();
    }
}
