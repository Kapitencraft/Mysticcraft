package net.kapitencraft.mysticcraft.event;

import net.kapitencraft.mysticcraft.event.custom.ItemStackEvent;
import net.kapitencraft.mysticcraft.event.custom.RegisterRarityEvent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.Event;
import net.neoforged.fml.ModLoader;
import net.neoforged.fml.event.IModBusEvent;
import net.neoforged.neoforge.common.NeoForge;

import java.util.List;

public class ModEventFactory {

    public static <T extends Event & IModBusEvent> void fireModEvent(T event) {
        ModLoader.postEvent(event);
    }

    public static void onLoadingItemStack(ItemStack stack) {
        ItemStackEvent.Load event = new ItemStackEvent.Load(stack);
        NeoForge.EVENT_BUS.post(event);
    }

    public static void onSavingItemStack(ItemStack stack, CompoundTag tag) {
        ItemStackEvent.Save event = new ItemStackEvent.Save(stack, tag);
        NeoForge.EVENT_BUS.post(event);
    }

    public static void onRarityRegister(List<Rarity> rarities) {
        RegisterRarityEvent event = new RegisterRarityEvent(rarities);
        fireModEvent(event);
    }
}