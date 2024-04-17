package net.kapitencraft.mysticcraft.event;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.event.custom.*;
import net.kapitencraft.mysticcraft.item.IEventListener;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneData;
import net.kapitencraft.mysticcraft.item.capability.reforging.Reforge;
import net.kapitencraft.mysticcraft.logging.Markers;
import net.kapitencraft.mysticcraft.misc.MiscRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.IModBusEvent;

import java.util.HashMap;
import java.util.List;

public class ModEventFactory {

    public static <T extends Event & IModBusEvent> void fireModEvent(T event) {
        ModLoader.get().postEventWithWrapInModOrder(event, (mc, e) -> ModLoadingContext.get().setActiveContainer(mc), (mc, e) -> ModLoadingContext.get().setActiveContainer(null));
    }

    public static void onGemstoneDataCreated(GemstoneData helper) {
        MysticcraftMod.sendRegisterDisplay("Gemstone Slots");
        AddGemstonesToItemEvent event = new AddGemstonesToItemEvent(helper);
        MiscRegister.registerGemstones(event);
        MinecraftForge.EVENT_BUS.post(event);
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

    public static void onReforgeRegister(HashMap<String, Reforge> reforges) {
        RegisterReforgesEvent event = new RegisterReforgesEvent(reforges);
        MinecraftForge.EVENT_BUS.post(event);
    }

    public static void fetchItemBonuses(List<IEventListener> listeners, ItemStack stack, EquipmentSlot slot) {
        FetchItemBonusesEvent event = new FetchItemBonusesEvent(listeners, stack, slot);
        MinecraftForge.EVENT_BUS.post(event);
    }

    public static void onRarityRegister(List<Rarity> rarities) {
        RegisterRarityEvent event = new RegisterRarityEvent(rarities);
        MinecraftForge.EVENT_BUS.post(event);
    }
}