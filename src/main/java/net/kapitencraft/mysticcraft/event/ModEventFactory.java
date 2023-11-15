package net.kapitencraft.mysticcraft.event;

import net.kapitencraft.mysticcraft.item.data.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.data.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.item.data.reforging.Reforge;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;
import java.util.List;

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
        MinecraftForge.EVENT_BUS.post(event);
    }

    public static void onReforgeRegister(HashMap<String, Reforge> reforges) {
        RegisterReforgesEvent event = new RegisterReforgesEvent(reforges);
        MinecraftForge.EVENT_BUS.post(event);
    }

    public static void onRarityRegister(List<Rarity> rarities) {
        RegisterRarityEvent event = new RegisterRarityEvent(rarities);
        MinecraftForge.EVENT_BUS.post(event);
    }
}
