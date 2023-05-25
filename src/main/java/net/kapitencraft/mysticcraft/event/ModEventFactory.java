package net.kapitencraft.mysticcraft.event;

import net.kapitencraft.mysticcraft.item.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.gemstone.GemstoneType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class ModEventFactory {

    public static void onGemstoneApplied(GemstoneType gemstoneType, GemstoneSlot gemstoneSlot) {
        GemstoneAppliedEvent event = new GemstoneAppliedEvent(gemstoneType, gemstoneSlot);
        MinecraftForge.EVENT_BUS.post(event);
    }

    public static void onLoadingItemStack(ItemStack stack) {
        ItemStackEvent.Load event = new ItemStackEvent.Load(stack);
        MinecraftForge.EVENT_BUS.post(event);
    }

    public static void onSavingItemStack(ItemStack stack, CompoundTag tag) {
        ItemStackEvent.Save event = new ItemStackEvent.Save(stack, tag);
    }
}
