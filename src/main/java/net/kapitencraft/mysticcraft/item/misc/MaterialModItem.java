package net.kapitencraft.mysticcraft.item.misc;

import net.minecraft.world.item.Item;

public class MaterialModItem extends Item {

    public MaterialModItem(Properties properties, boolean stackable) {
        super(properties.stacksTo(stackable ? 64 : 1));
    }
}
