package net.kapitencraft.mysticcraft.item.combat.spells;

import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.kapitencraft.mysticcraft.registry.ModCreativeModTabs;
import net.minecraft.client.gui.components.tabs.Tab;

public interface IFireScytheItem {
    TabGroup FIRE_SCYTHE_GROUP = TabGroup.builder().tab(ModCreativeModTabs.SPELLS).tab(ModCreativeModTabs.WEAPONS_AND_TOOLS).build();
}
