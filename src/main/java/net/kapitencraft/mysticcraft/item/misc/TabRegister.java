package net.kapitencraft.mysticcraft.item.misc;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.spell.Elements;
import net.kapitencraft.mysticcraft.utils.MiscUtils;
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
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.function.Consumer;

@Mod.EventBusSubscriber(modid = MysticcraftMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class TabRegister {
    @SubscribeEvent
    public static void registerTabs(CreativeModeTabEvent.Register event) {
        event.registerCreativeModeTab(new ResourceLocation(MysticcraftMod.MOD_ID, "spell_and_gemstone"), builder ->
                builder.title(Component.translatable("itemGroup.spell_and_gemstone"))
                        .icon(() -> new ItemStack(ModItems.STAFF_OF_THE_WILD.get()))
                        .displayItems((featureFlagSet, output, flag) -> register(ModItems.TabTypes.SPELL_AND_GEMSTONE, output::acceptAll)));
        event.registerCreativeModeTab(new ResourceLocation(MysticcraftMod.MOD_ID, "materials"), builder ->
                builder.title(Component.translatable("itemGroup.materials_mm"))
                        .icon(()-> new ItemStack(ModItems.ELEMENTAL_SHARDS.get(Elements.FIRE).get()))
                        .displayItems((featureFlagSet, output, flag) -> {
                            output.accept(MiscUtils.of(() -> {
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
                            register(ModItems.TabTypes.MOD_MATERIALS, output::acceptAll);
                        }));
        event.registerCreativeModeTab(new ResourceLocation(MysticcraftMod.MOD_ID, "weapons_and_tools"), builder ->
                builder.title(Component.translatable("itemGroup.weapons_and_tools_mm"))
                        .icon(()-> new ItemStack(ModItems.MANA_STEEL_SWORD.get()))
                        .displayItems((featureFlagSet, output, flag) -> register(ModItems.TabTypes.WEAPONS_AND_TOOLS, output::acceptAll)));
    }

    private static void register(ModItems.TabTypes types, Consumer<Collection<ItemStack>> consumer) {
        consumer.accept(ModItems.tabTypes.get(types).stream().map(RegistryObject::get).map(ItemStack::new).toList());
    }

    @SubscribeEvent
    public static void addToTabs(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.SPAWN_EGGS) {
            register(ModItems.TabTypes.SPAWN_EGGS, event::acceptAll);
        } else if (event.getTab() == CreativeModeTabs.FOOD_AND_DRINKS) {
            register(ModItems.TabTypes.FOOD_AND_DRINK, event::acceptAll);
        } else if (event.getTab() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            register(ModItems.TabTypes.TOOLS_AND_UTILITIES, event::acceptAll);
        }
    }


}
