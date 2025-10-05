package net.kapitencraft.mysticcraft.item.combat.shield;

import net.kapitencraft.kap_lib.item.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.registry.ModCreativeModTabs;
import net.minecraft.world.item.ShieldItem;

public abstract class ModShieldItem extends ShieldItem {
    public static final TabGroup SHIELD_GROUP = TabGroup.create(ModCreativeModTabs.WEAPONS_AND_TOOLS);
    public ModShieldItem(Properties p_43089_, int durability) {
        super(p_43089_.durability(durability));
    }
}