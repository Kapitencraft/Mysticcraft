package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.capability.gemstone.IGemstoneItem;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabGroup;
import net.kapitencraft.mysticcraft.item.misc.creative_tab.TabRegister;
import net.kapitencraft.mysticcraft.spell.Elements;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public interface ModCreativeModTabs {
    DeferredRegister<CreativeModeTab> REGISTRY = MysticcraftMod.registry(Registries.CREATIVE_MODE_TAB);

    RegistryObject<CreativeModeTab> SPELLS = REGISTRY.register("spell", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.mysticcraft.spell"))
            .icon(() -> new ItemStack(ModItems.SCYLLA.get()))
            .displayItems((displayParameters, output) -> {
                output.acceptAll(Spells.createForCreativeModeTab());
                TabGroup.registerAll(TabRegister.TabTypes.SPELL, output::acceptAll);
            }).build());

    RegistryObject<CreativeModeTab> GEMSTONES = REGISTRY.register("gemstone", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.mysticcraft.gemstone"))
            .icon(() -> IGemstoneItem.createData(GemstoneType.Rarity.PERFECT, GemstoneType.JASPER, ModItems.GEMSTONE))
            .displayItems((displayParameters, output) -> {
                output.acceptAll(GemstoneType.allItems().actualValues());
                output.acceptAll(GemstoneType.allBlocks().values());
                output.acceptAll(GemstoneType.allCrystals().actualValues());
                output.acceptAll(GemstoneType.allSeeds().actualValues());
                TabGroup.registerAll(TabRegister.TabTypes.GEMSTONE, output::acceptAll);
            }).build());

    RegistryObject<CreativeModeTab> MATERIALS = REGISTRY.register("materials", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.mysticcraft.materials"))
            .icon(()-> new ItemStack(ModItems.ELEMENTAL_SHARDS.get(Elements.FIRE).get()))
            .displayItems((displayParameters, output) -> {
                output.accept(MiscHelper.of(() -> {
                    ItemStack stack = new ItemStack(Items.PRISMARINE_SHARD);
                    ListTag tags = EnchantedBookItem.getEnchantments(stack);
                    stack.setHoverName(Component.literal("Enchantments"));
                    for (Enchantment enchantment : ForgeRegistries.ENCHANTMENTS) {
                        ResourceLocation location = EnchantmentHelper.getEnchantmentId(enchantment);
                        tags.add(EnchantmentHelper.storeEnchantment(location, enchantment.getMaxLevel()));
                    }
                    stack.hideTooltipPart(ItemStack.TooltipPart.MODIFIERS);
                    stack.addTagElement("Enchantments", tags);
                    return stack;
                }));
                TabGroup.registerAll(TabRegister.TabTypes.MOD_MATERIALS, output::acceptAll);
            }).build());

    RegistryObject<CreativeModeTab> WEAPONS_AND_TOOLS = REGISTRY.register("weapons_and_tools", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.mysticcraft.weapons_and_tools"))
            .icon(()-> new ItemStack(ModItems.MANA_STEEL_SWORD.get()))
            .displayItems((displayParameters, output) -> TabGroup.registerAll(TabRegister.TabTypes.WEAPONS_AND_TOOLS, output::acceptAll)).build());

    RegistryObject<CreativeModeTab> DECORATION = REGISTRY.register("deco", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.mysticcraft.deco"))
            .icon(()-> new ItemStack(ModBlocks.GOLDEN_WALL.getItem()))
            .displayItems((displayParameters, output) -> TabGroup.registerAll(TabRegister.TabTypes.DECO, output::acceptAll)).build());
    RegistryObject<CreativeModeTab> TECHNOLOGY = REGISTRY.register("technology", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.mysticcraft.tech"))
            .icon(() -> new ItemStack(ModBlocks.MANA_RELAY.getItem()))
            .displayItems((pParameters, pOutput) -> TabGroup.registerAll(TabRegister.TabTypes.TECHNOLOGY, pOutput::acceptAll)).build());
}
