package net.kapitencraft.mysticcraft.item.combat.shield;

import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.kapitencraft.mysticcraft.registry.ModCreativeModTabs;
import net.minecraft.world.item.ShieldItem;

public abstract class ModShieldItem extends ShieldItem {
    public static final TabGroup SHIELD_GROUP = TabGroup.builder().tab(ModCreativeModTabs.WEAPONS_AND_TOOLS).build();
    public ModShieldItem(Properties p_43089_, int durability) {
        super(p_43089_.durability(durability));
    }
}