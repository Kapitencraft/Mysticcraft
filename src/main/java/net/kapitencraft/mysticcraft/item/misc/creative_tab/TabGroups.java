package net.kapitencraft.mysticcraft.item.misc.creative_tab;

import net.kapitencraft.kap_lib.item.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.registry.ModCreativeModTabs;
import net.minecraft.world.item.CreativeModeTabs;

/**
 * used to group items into {@link net.minecraft.world.item.CreativeModeTab}s
 */
public class TabGroups {
    public static final TabGroup TECHNOLOGY = TabGroup.create(ModCreativeModTabs.TECHNOLOGY);
    public static final TabGroup BUILDING_MATERIAL = TabGroup.create(CreativeModeTabs.BUILDING_BLOCKS);
    public static final TabGroup MATERIAL = TabGroup.create(ModCreativeModTabs.MATERIALS);
    public static final TabGroup COMBAT = TabGroup.create(ModCreativeModTabs.WEAPONS_AND_TOOLS);
    public static final TabGroup CRIMSON_MATERIAL = TabGroup.create(ModCreativeModTabs.MATERIALS);
    public static final TabGroup TERROR_MATERIAL = TabGroup.create(ModCreativeModTabs.MATERIALS);
    public static final TabGroup UTILITIES = TabGroup.create(CreativeModeTabs.TOOLS_AND_UTILITIES);
    public static final TabGroup DECO = TabGroup.create(ModCreativeModTabs.DECORATION);
    public static final TabGroup GOLDEN_DECO = TabGroup.create(ModCreativeModTabs.DECORATION);
    public static final TabGroup OPERATOR = TabGroup.create(CreativeModeTabs.OP_BLOCKS);
    public static final TabGroup SPAWN_EGGS = TabGroup.create(CreativeModeTabs.SPAWN_EGGS);

    public static final TabGroup PERIDOT_SYCAMORE = TabGroup.create(ModCreativeModTabs.MATERIALS);
}