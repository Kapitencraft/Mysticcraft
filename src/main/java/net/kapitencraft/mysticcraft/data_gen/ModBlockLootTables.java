package net.kapitencraft.mysticcraft.data_gen;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.item.loot_table.functions.PristineFunction;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.kapitencraft.mysticcraft.registry.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ModBlockLootTables extends BlockLootSubProvider {
    protected ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.ARTIFICER_TABLE.get());
        dropSelf(ModBlocks.GOLDEN_STAIRS.get());
        dropSelf(ModBlocks.GOLDEN_SLAB.get());
        dropSelf(ModBlocks.GOLDEN_WALL.get());
        dropSelf(ModBlocks.LAPIS_BUTTON.get());
        dropSelf(ModBlocks.MANGATIC_SLIME.get());
        dropSelf(ModBlocks.MANGATIC_STONE.get());
        dropSelf(ModBlocks.OBSIDIAN_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.REFORGING_ANVIL.get());
        dropSelf(ModBlocks.SOUL_CHAIN.get());
        dropSelf(ModBlocks.PERIDOT_SYCAMORE_LOG.get());
        dropSelf(ModBlocks.PERIDOT_SYCAMORE_WOOD.get());
        dropSelf(ModBlocks.PERIDOT_SYCAMORE_PLANKS.get());
        dropSelf(ModBlocks.PERIDOT_SYCAMORE_SAPLING.get());
        dropSelf(ModBlocks.STRIPPED_PERIDOT_SYCAMORE_LOG.get());
        dropSelf(ModBlocks.STRIPPED_PERIDOT_SYCAMORE_WOOD.get());
        dropSelf(ModBlocks.MANA_RELAY.get());
        dropSelf(ModBlocks.MANA_PORT.get());
        dropSelf(ModBlocks.MANA_BATTERY.get());
        dropSelf(ModBlocks.VULCANIC_GENERATOR.get());
        dropSelf(ModBlocks.PRISMATIC_GENERATOR.get());
        dropSelf(ModBlocks.MAGIC_FURNACE.get());
        dropSelf(ModBlocks.SPELL_CASTER_TURRET.get());
        dropSelf(ModBlocks.OBELISK_TURRET.get());
        dropSelf(ModBlocks.ALTAR.get());
        dropSelf(ModBlocks.PEDESTAL.get());
        dropSelf(ModBlocks.THISTLE.get());
        dropSelf(ModBlocks.MISTLETOE.get());
        dropSelf(ModBlocks.SHADER_TEST_BLOCK.get());
        this.add(ModBlocks.CRIMSONIUM_ORE.get(), createOreDrop(ModBlocks.CRIMSONIUM_ORE.get(), ModItems.RAW_CRIMSONIUM.get()));
        gemstone(ModBlocks.GEMSTONE_BLOCK.get());
        gemstone(ModBlocks.GEMSTONE_CRYSTAL.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        List<Block> blocks = ModBlocks.REGISTRY.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
        blocks.removeAll(NOT_USABLE);
        return blocks;
    }

    private static final List<Block> NOT_USABLE = List.of(
            ModBlocks.MANA_FLUID_BLOCK.get(),
            ModBlocks.FRAGILE_BASALT.get(),
            ModBlocks.DUNGEON_GENERATOR.get(),
            ModBlocks.GEMSTONE_SEED.get() //shouldn't drop anything
    );

    private void gemstone(Block pBlock) {
        this.add(pBlock,
                LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F))
                        .add(DynamicLoot.dynamicEntry(MysticcraftMod.res("gemstone_block"))
                                .when(HAS_SILK_TOUCH).otherwise(
                                        this.applyExplosionDecay(pBlock,
                                                DynamicLoot.dynamicEntry(MysticcraftMod.res("gemstone_item"))
                                                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 5), false))
                                                        .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
                                                        .apply(() -> new PristineFunction(new LootItemCondition[0]))
                                        )
                                )
                        )
                )
        );
    }
}
