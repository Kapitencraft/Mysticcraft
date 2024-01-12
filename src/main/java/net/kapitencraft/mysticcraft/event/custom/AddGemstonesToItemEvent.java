package net.kapitencraft.mysticcraft.event.custom;

import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneData;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneSlot;
import net.kapitencraft.mysticcraft.item.combat.armor.ModArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;

public class AddGemstonesToItemEvent extends Event {
    private final GemstoneData helper;

    public AddGemstonesToItemEvent(GemstoneData helper) {
        this.helper = helper;
    }

    public void register(RegistryObject<? extends Item> object, GemstoneSlot.Builder builder) {
        helper.register((RegistryObject<Item>) object, builder);
    }

    public void registerArmor(Map<EquipmentSlot, RegistryObject<ModArmorItem>> objectMap, GemstoneSlot.Builder builder) {
        objectMap.values().forEach(object -> register(object, builder));
    }
}
