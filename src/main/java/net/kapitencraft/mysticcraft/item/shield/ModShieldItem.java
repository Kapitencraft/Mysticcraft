package net.kapitencraft.mysticcraft.item.shield;

import net.minecraft.world.item.ShieldItem;

public abstract class ModShieldItem extends ShieldItem {
    public ModShieldItem(Properties p_43089_, int durability) {
        super(p_43089_.durability(durability));
    }
}