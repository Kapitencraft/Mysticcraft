package net.kapitencraft.mysticcraft.item.misc.creative_tab;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.helpers.CollectionHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModBlocks;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.item.capability.gemstone.IGemstoneItem;
import net.kapitencraft.mysticcraft.spell.Elements;
import net.kapitencraft.mysticcraft.spell.Spells;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TabRegister {
    /**
     * registry method for custom Creative mod tabs
     */
    @SubscribeEvent
    public static void registerTabs(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(MysticcraftMod.res("spell"), builder ->
                builder.title(Component.translatable("itemGroup.mysticcraft.spell"))
                        .icon(() -> new ItemStack(ModItems.SCYLLA.get()))
                        .displayItems((featureFlagSet, output, flag) -> {
                            output.acceptAll(Spells.createAll().values());
                            TabGroup.registerAll(TabTypes.SPELL, output::acceptAll);
                        })
        );
        event.registerCreativeModeTab(MysticcraftMod.res("gemstone"), builder ->
                builder.title(Component.translatable("itemGroup.mysticcraft.gemstone"))
                        .icon(() -> IGemstoneItem.createData(GemstoneType.Rarity.PERFECT, GemstoneType.JASPER, ModItems.GEMSTONE))
                        .displayItems((featureFlagSet, output, flag) -> {
                            output.acceptAll(CollectionHelper.values(GemstoneType.allItems()));
                            output.acceptAll(GemstoneType.allBlocks().values());
                            output.acceptAll(CollectionHelper.values(GemstoneType.allCrystals()));
                            TabGroup.registerAll(TabTypes.GEMSTONE, output::acceptAll);
                        })
        );
        event.registerCreativeModeTab(MysticcraftMod.res("materials"), builder ->
                builder.title(Component.translatable("itemGroup.mysticcraft.materials"))
                        .icon(()-> new ItemStack(ModItems.ELEMENTAL_SHARDS.get(Elements.FIRE).get()))
                        .displayItems((featureFlagSet, output, flag) -> {
                            output.accept(MiscHelper.of(() -> {
                                ItemStack stack = new ItemStack(Items.PRISMARINE_SHARD);
                                ListTag tags = EnchantedBookItem.getEnchantments(stack);
                                stack.setHoverName(Component.literal("Enchantments"));
                                for (Enchantment enchantment : BuiltInRegistries.ENCHANTMENT) {
                                    ResourceLocation location = EnchantmentHelper.getEnchantmentId(enchantment);
                                    tags.add(EnchantmentHelper.storeEnchantment(location, enchantment.getMaxLevel()));
                                }
                                stack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
                                stack.addTagElement("Enchantments", tags);
                                return stack;
                            }));
                            output.acceptAll(GemstoneType.allBlocks().values());
                            TabGroup.registerAll(TabTypes.MOD_MATERIALS, output::acceptAll);
                        })
        );
        event.registerCreativeModeTab(MysticcraftMod.res("weapons_and_tools"), builder ->
                builder.title(Component.translatable("itemGroup.mysticcraft.weapons_and_tools"))
                        .icon(()-> new ItemStack(ModItems.MANA_STEEL_SWORD.get()))
                        .displayItems((featureFlagSet, output, flag) -> TabGroup.registerAll(TabTypes.WEAPONS_AND_TOOLS, output::acceptAll))
        );
        event.registerCreativeModeTab(MysticcraftMod.res("deco"), builder ->
                builder.title(Component.translatable("itemGroup.mysticcraft.deco"))
                        .icon(()-> new ItemStack(ModBlocks.GOLDEN_WALL.getItem()))
                        .displayItems((featureFlagSet, output, fl) -> TabGroup.registerAll(TabTypes.DECO, output::acceptAll))
        );
    }


    @SubscribeEvent
    public static void addToTabs(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.SPAWN_EGGS) {
            TabGroup.registerAll(TabTypes.SPAWN_EGGS, event::acceptAll);
        } else if (event.getTab() == CreativeModeTabs.FOOD_AND_DRINKS) {
            TabGroup.registerAll(TabTypes.FOOD_AND_DRINK, event::acceptAll);
        } else if (event.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            TabGroup.registerAll(TabTypes.TOOLS_AND_UTILITIES, event::acceptAll);
        } else if (event.getTab() == CreativeModeTabs.OP_BLOCKS) {
            TabGroup.registerAll(TabTypes.OPERATOR, event::acceptAll);
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
        OPERATOR
    }
}
