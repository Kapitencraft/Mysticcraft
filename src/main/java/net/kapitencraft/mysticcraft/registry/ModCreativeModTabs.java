package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.kap_lib.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneType;
import net.kapitencraft.mysticcraft.capability.gemstone.IGemstoneItem;
import net.kapitencraft.mysticcraft.spell.Elements;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface ModCreativeModTabs {
    DeferredRegister<CreativeModeTab> REGISTRY = MysticcraftMod.registry(Registries.CREATIVE_MODE_TAB);

    Holder<CreativeModeTab> SPELLS = REGISTRY.register("spell", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.mysticcraft.spell"))
            .icon(() -> new ItemStack(ModItems.SCYLLA.get()))
            .displayItems((displayParameters, output) -> {
                output.acceptAll(Spells.createForCreativeModeTab());
            }).build());

    Holder<CreativeModeTab> GEMSTONES = REGISTRY.register("gemstone", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.mysticcraft.gemstone"))
            .icon(() -> IGemstoneItem.createData(GemstoneType.Rarity.PERFECT, GemstoneType.JASPER, ModItems.GEMSTONE))
            .displayItems((displayParameters, output) -> {
                output.acceptAll(GemstoneType.allItems().actualValues());
                output.acceptAll(GemstoneType.allBlocks().values());
                output.acceptAll(GemstoneType.allCrystals().actualValues());
                output.acceptAll(GemstoneType.allSeeds().actualValues());
            }).build());

    Holder<CreativeModeTab> MATERIALS = REGISTRY.register("materials", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.mysticcraft.materials"))
            .icon(()-> new ItemStack(ModItems.ELEMENTAL_SHARDS.get(Elements.FIRE).get()))
            .displayItems((displayParameters, output) -> {
                output.accept(MiscHelper.of(() -> {
                    ItemStack stack = new ItemStack(Items.PRISMARINE_SHARD);
                    HolderLookup.RegistryLookup<Enchantment> lookup = displayParameters.holders().lookupOrThrow(Registries.ENCHANTMENT);
                    lookup.listElements().forEach(r -> stack.enchant(r, r.value().getMaxLevel()));
                    stack.set(DataComponents.ITEM_NAME, Component.literal("Enchantments"));
                    return stack;
                }));
            }).build());

    Holder<CreativeModeTab> WEAPONS_AND_TOOLS = REGISTRY.register("weapons_and_tools", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.mysticcraft.weapons_and_tools"))
            .icon(()-> new ItemStack(ModItems.MANA_STEEL_SWORD.get()))
            .build()
    );

    Holder<CreativeModeTab> DECORATION = REGISTRY.register("deco", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.mysticcraft.deco"))
            .icon(()-> new ItemStack(ModBlocks.GOLDEN_WALL.getItem()))
            .build()
    );
    Holder<CreativeModeTab> TECHNOLOGY = REGISTRY.register("technology", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.mysticcraft.tech"))
            .icon(() -> new ItemStack(ModBlocks.MANA_RELAY.getItem()))
            .build()
    );
}
