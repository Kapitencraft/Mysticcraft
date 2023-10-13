package net.kapitencraft.mysticcraft.item.misc.creative_tab;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.spell.Elements;
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
    @SubscribeEvent
    public static void registerTabs(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(MysticcraftMod.res("spell_and_gemstone"), builder ->
                builder.title(Component.translatable("itemGroup.spell_and_gemstone"))
                        .icon(() -> new ItemStack(ModItems.STAFF_OF_THE_WILD.get()))
                        .displayItems((featureFlagSet, output, flag) -> TabGroup.registerAll(TabTypes.SPELL_AND_GEMSTONE, output::acceptAll)));
        event.registerCreativeModeTab(MysticcraftMod.res("materials"), builder ->
                builder.title(Component.translatable("itemGroup.materials_mm"))
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
                            TabGroup.registerAll(TabTypes.MOD_MATERIALS, output::acceptAll);
                        }));
        event.registerCreativeModeTab(MysticcraftMod.res("weapons_and_tools"), builder ->
                builder.title(Component.translatable("itemGroup.weapons_and_tools_mm"))
                        .icon(()-> new ItemStack(ModItems.MANA_STEEL_SWORD.get()))
                        .displayItems((featureFlagSet, output, flag) -> TabGroup.registerAll(TabTypes.WEAPONS_AND_TOOLS, output::acceptAll)));
    }


    @SubscribeEvent
    public static void addToTabs(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.SPAWN_EGGS) {
            TabGroup.registerAll(TabTypes.SPAWN_EGGS, event::acceptAll);
        } else if (event.getTab() == CreativeModeTabs.FOOD_AND_DRINKS) {
            TabGroup.registerAll(TabTypes.FOOD_AND_DRINK, event::acceptAll);
        } else if (event.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            TabGroup.registerAll(TabTypes.TOOLS_AND_UTILITIES, event::acceptAll);
        }
    }
    public enum TabTypes {
        SPELL_AND_GEMSTONE,
        MOD_MATERIALS,
        WEAPONS_AND_TOOLS,
        SPAWN_EGGS,
        FOOD_AND_DRINK,
        TOOLS_AND_UTILITIES
    }
}
