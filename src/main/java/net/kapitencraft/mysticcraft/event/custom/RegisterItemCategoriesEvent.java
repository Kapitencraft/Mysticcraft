package net.kapitencraft.mysticcraft.event.custom;

import net.kapitencraft.mysticcraft.client.ItemCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;

import java.util.List;
import java.util.function.Predicate;


public class RegisterItemCategoriesEvent extends Event implements IModBusEvent {
    private final List<ItemCategory> categories;

    public RegisterItemCategoriesEvent(List<ItemCategory> categories) {
        this.categories = categories;
    }

    public void register(Predicate<ItemStack> predicate, Component display) {
        categories.add(ItemCategory.create(predicate, display));
    }
}
