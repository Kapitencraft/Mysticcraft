package net.kapitencraft.mysticcraft.event.custom;

import net.kapitencraft.kap_lib.item.combat.armor.ModArmorItem;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneData;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.event.IModBusEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.Map;
import java.util.function.Function;

/**
 * Event to add Gemstone Slots to custom Items <br>
 * see {@link net.kapitencraft.mysticcraft.event.handler.ModEventBusEvents#registerGemstoneItems(AddGemstonesToItemEvent)} for examples
 */
public class AddGemstonesToItemEvent extends Event implements IModBusEvent {
    private final GemstoneData helper;

    public AddGemstonesToItemEvent(GemstoneData helper) {
        this.helper = helper;
    }

    /**
     * method to add Gemstone Slots to an Item
     * @param object the item to add to
     * @param builder the Gemstone Slots to add
     */
    public void register(RegistryObject<? extends Item> object, GemstoneSlot.Builder builder) {
        helper.register(object, builder);
    }

    /**
     * helper method to quickly add Gemstone Slots to entire Armor Sets
     * @param objectMap the data of the Armor created via {@link ModArmorItem#createRegistry(DeferredRegister, String, Function)}
     * @param builder the Gemstone Slots to add to each armor element
     */
    public void registerArmor(Map<ArmorItem.Type, ? extends RegistryObject<? extends ModArmorItem>> objectMap, GemstoneSlot.Builder builder) {
        objectMap.values().forEach(object -> register(object, builder));
    }
}
