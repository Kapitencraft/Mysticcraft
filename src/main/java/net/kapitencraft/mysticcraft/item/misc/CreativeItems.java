package net.kapitencraft.mysticcraft.item.misc;

import net.kapitencraft.mysticcraft.init.ModItems;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class CreativeItems {

    public static final List<RegistryObject<? extends Item>> CREATIVE_ITEMS = List.of(
            ModItems.MANA_STEEL_SWORD
    );

    public static boolean contains(Item item) {
        return CREATIVE_ITEMS.stream().map(RegistryObject::get).anyMatch(item1 -> item1 == item);
    }
}
