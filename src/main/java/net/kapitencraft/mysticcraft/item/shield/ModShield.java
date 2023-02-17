package net.kapitencraft.mysticcraft.item.shield;

import net.minecraft.world.item.ShieldItem;

public abstract class ModShield extends ShieldItem {
    public ModShield(Properties p_43089_, int durability) {
        super(p_43089_.durability(durability));
    }
}