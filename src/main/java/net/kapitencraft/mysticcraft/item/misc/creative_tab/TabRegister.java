package net.kapitencraft.mysticcraft.item.misc.creative_tab;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TabRegister {


    @SubscribeEvent
    public static void addToTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
            TabGroup.registerAll(TabTypes.SPAWN_EGGS, event::acceptAll);
        } else if (event.getTabKey() == CreativeModeTabs.FOOD_AND_DRINKS) {
            TabGroup.registerAll(TabTypes.FOOD_AND_DRINK, event::acceptAll);
        } else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            TabGroup.registerAll(TabTypes.TOOLS_AND_UTILITIES, event::acceptAll);

            MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries = event.getEntries();
            entries.putAfter(new ItemStack(Items.STONE_HOE), new ItemStack(ModItems.STONE_HAMMER.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            entries.putAfter(new ItemStack(Items.IRON_HOE), new ItemStack(ModItems.IRON_HAMMER.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            entries.putAfter(new ItemStack(Items.DIAMOND_HOE), new ItemStack(ModItems.DIAMOND_HAMMER.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            entries.putAfter(new ItemStack(Items.NETHERITE_HOE), new ItemStack(ModItems.NETHERITE_HAMMER.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        } else if (event.getTabKey() == CreativeModeTabs.OP_BLOCKS) {
            TabGroup.registerAll(TabTypes.OPERATOR, event::acceptAll);
        } else if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            TabGroup.registerAll(TabTypes.BUILDING_MATERIALS, event::acceptAll);
        }
    }

    /**
     * different Tab-Types relating to different {@link net.minecraft.world.item.CreativeModeTab}s
     */
    public enum TabTypes {
        SPELL,
        GEMSTONE,
        MOD_MATERIALS,
        WEAPONS_AND_TOOLS,
        SPAWN_EGGS,
        FOOD_AND_DRINK,
        TOOLS_AND_UTILITIES,
        DECO,
        BUILDING_MATERIALS,
        TECHNOLOGY,
        OPERATOR
    }
}
