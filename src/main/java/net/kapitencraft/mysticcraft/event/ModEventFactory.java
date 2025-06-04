package net.kapitencraft.mysticcraft.event;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.event.custom.AddGemstonesToItemEvent;
import net.kapitencraft.mysticcraft.event.custom.ItemStackEvent;
import net.kapitencraft.mysticcraft.event.custom.RegisterRarityEvent;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneData;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.event.IModBusEvent;

import java.util.List;

public class ModEventFactory {

    public static <T extends Event & IModBusEvent> void fireModEvent(T event) {
        ModLoader.get().postEvent(event);
    }

    public static void onGemstoneDataCreated(GemstoneData helper) {
        MysticcraftMod.sendRegisterDisplay("Gemstone Slots");
        AddGemstonesToItemEvent event = new AddGemstonesToItemEvent(helper);
        fireModEvent(event);
        MysticcraftMod.LOGGER.info(Markers.REGISTRY, "Registered {} Gemstone Slots", helper.getAmount());
    }

    public static void onLoadingItemStack(ItemStack stack) {
        ItemStackEvent.Load event = new ItemStackEvent.Load(stack);
        MinecraftForge.EVENT_BUS.post(event);
    }

    public static void onSavingItemStack(ItemStack stack, CompoundTag tag) {
        ItemStackEvent.Save event = new ItemStackEvent.Save(stack, tag);
        MinecraftForge.EVENT_BUS.post(event);
    }

    public static void onRarityRegister(List<Rarity> rarities) {
        RegisterRarityEvent event = new RegisterRarityEvent(rarities);
        fireModEvent(event);
    }
}