package net.kapitencraft.mysticcraft.data_gen;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.ModBlockStateProperties;
import net.kapitencraft.mysticcraft.block.MoonBlossomFlowerBlock;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneCrystalBlock;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneSeedBlock;
import net.kapitencraft.mysticcraft.registry.BlockRegistryHolder;
import net.kapitencraft.mysticcraft.registry.ModBlocks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, MysticcraftMod.MOD_ID, exFileHelper);
    }

    public ResourceLocation key(Block block) {
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    private void blockWithItem(BlockRegistryHolder<?, ?> holder) {
        simpleBlockWithItem(holder.get(), cubeAll(holder.get()));
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.CRIMSONIUM_ORE);

        makeReforgeAnvil();

        ModelFile gemstoneBlock = models().getBuilder("block/gemstone/block")
                .texture("all", MysticcraftMod.res("block/gemstone/block"))
                .parent(coloredBlock())
                .renderType("translucent");
        getVariantBuilder(ModBlocks.GEMSTONE_BLOCK.get())
                .partialState()
                        .setModels(
                                new ConfiguredModel(gemstoneBlock)
                        );
        simpleBlockItem(ModBlocks.GEMSTONE_BLOCK.get(), gemstoneBlock);
        simpleBlock(ModBlocks.THISTLE.get(), models().cross("thistle", MysticcraftMod.res("block/thistle")).renderType("cutout"));
        getVariantBuilder(ModBlocks.MOON_BLOSSOM.get()).forAllStates(state -> {
            Boolean value = state.getValue(MoonBlossomFlowerBlock.BLOOMING);
            return ConfiguredModel.builder().modelFile(
                    value ?
                            models().cross("moon_blossom_open", MysticcraftMod.res("block/moon_blossom_open")).renderType("cutout")
                            : models().cross("moon_blossom_closed", MysticcraftMod.res("block/moon_blossom_closed")).renderType("cutout")
            ).build();
        });

        simpleBlockWithItem(ModBlocks.SHADER_TEST_BLOCK.get(), models().cubeAll("shader_test", MysticcraftMod.res("block/example")).renderType("mysticcraft:chromatic_cutout"));

        makeGemstoneCrystals();

        makeGemstoneSeeds();
        makeGemstoneSeedItem();

        ResourceLocation WEST_EAST_TEXTURE = MysticcraftMod.res("block/artificer_table_west_east");
        ResourceLocation NORTH_SOUTH_TEXTURE = MysticcraftMod.res("block/artificer_table_north_south");
        getVariantBuilder(ModBlocks.ARTIFICER_TABLE.get())
                .partialState()
                .setModels(
                        new ConfiguredModel(models().withExistingParent("artificer_table", "block/cube")
                                .texture("down", MysticcraftMod.res("block/artificer_table_bottom"))
                                .texture("up", MysticcraftMod.res("block/artificer_table_top"))
                                .texture("north", NORTH_SOUTH_TEXTURE)
                                .texture("east", WEST_EAST_TEXTURE)
                                .texture("south", NORTH_SOUTH_TEXTURE)
                                .texture("west", WEST_EAST_TEXTURE)
                                .texture("particle", NORTH_SOUTH_TEXTURE)
                        )
                );
        simpleBlockItem(ModBlocks.ARTIFICER_TABLE.get(), new ModelFile.UncheckedModelFile(MysticcraftMod.res("block/artificer_table")));

        ModelFile chainModel = createSoulChainModel();
        getVariantBuilder(ModBlocks.SOUL_CHAIN.get()).forAllStates(state -> {
            Direction.Axis axis = state.getValue(RotatedPillarBlock.AXIS);
            return ConfiguredModel.builder().modelFile(chainModel)
                    .rotationY(axis == Direction.Axis.X ? 90 : 0)
                    .rotationX(axis == Direction.Axis.Y ? 0 : 90)
                    .build();
        });

        //simpleBlock(ModBlocks.MANGATIC_SLIME);
        simpleBlock(ModBlocks.MANGATIC_STONE);
        simpleBlockWithItem(ModBlocks.OBELISK_TURRET.get(), models().getExistingFile(MysticcraftMod.res("block/turret/obelisk")));

        ResourceLocation GOLD_BLOCK = ResourceLocation.withDefaultNamespace("block/gold_block");

        slabBlock(ModBlocks.GOLDEN_SLAB.get(), GOLD_BLOCK, GOLD_BLOCK);
        stairsBlock(ModBlocks.GOLDEN_STAIRS.get(), GOLD_BLOCK);
        wallBlock(ModBlocks.GOLDEN_WALL.get(), GOLD_BLOCK);

        ResourceLocation LAPIS_BLOCK = ResourceLocation.withDefaultNamespace("block/lapis_block");
        buttonBlock(ModBlocks.LAPIS_BUTTON.get(), LAPIS_BLOCK);
        simpleBlockItem(ModBlocks.LAPIS_BUTTON.get(), itemModels().buttonInventory("lapis_button_inventory", LAPIS_BLOCK));

        ResourceLocation OBSIDIAN = ResourceLocation.withDefaultNamespace("block/obsidian");
        pressurePlateBlock(ModBlocks.OBSIDIAN_PRESSURE_PLATE.get(), OBSIDIAN);
        itemModels().pressurePlate("obsidian_pressure_plate", OBSIDIAN);

        logBlock(ModBlocks.PERIDOT_SYCAMORE_LOG.get());
        logBlock(ModBlocks.STRIPPED_PERIDOT_SYCAMORE_LOG.get());
        simpleBlockItem(ModBlocks.PERIDOT_SYCAMORE_LOG.get(), this.models().getExistingFile(MysticcraftMod.res("block/peridot_sycamore_log")));
        simpleBlockItem(ModBlocks.STRIPPED_PERIDOT_SYCAMORE_LOG.get(), this.models().getExistingFile(MysticcraftMod.res("block/stripped_peridot_sycamore_log")));

        wood(ModBlocks.PERIDOT_SYCAMORE_WOOD.get(), MysticcraftMod.res("peridot_sycamore_log"));
        wood(ModBlocks.STRIPPED_PERIDOT_SYCAMORE_WOOD.get(), MysticcraftMod.res("stripped_peridot_sycamore_log"));

        simpleBlock(ModBlocks.PERIDOT_SYCAMORE_PLANKS);
    }

    private void simpleBlock(BlockRegistryHolder<? extends Block, BlockItem> block) {
        simpleBlock(block.get());
        simpleBlockItem(block.get(), cubeAll(block.get()));
    }

    private ModelFile createSoulChainModel() {
        return models().withExistingParent("soul_chain", "chain")
                .texture("particle", MysticcraftMod.res("block/soul_chain"))
                .texture("all", MysticcraftMod.res("block/soul_chain"))
                .renderType("cutout");
    }

    private void makeReforgeAnvil() {
        getVariantBuilder(ModBlocks.REFORGING_ANVIL.get())
                .forAllStates(state -> {
                    Direction direction = state.getValue(AnvilBlock.FACING);
                    int rot = switch (direction) {
                        case NORTH -> 180;
                        case SOUTH -> 0;
                        case WEST -> 90;
                        case EAST -> 270;
                        default -> -1;
                    };
                    return ConfiguredModel.builder()
                            .modelFile(models().getBuilder("reforge_anvil")
                                    .parent(models().getExistingFile(ResourceLocation.withDefaultNamespace("template_anvil")))
                                    .texture("top", MysticcraftMod.res("block/reforging_anvil_top"))
                            )
                            .rotationY(rot)
                            .build();
                });
    }

    private void makeGemstoneCrystals() {
        getVariantBuilder(ModBlocks.GEMSTONE_CRYSTAL.get()).forAllStatesExcept(state -> {
            Direction direction = state.getValue(BlockStateProperties.FACING);
            int xRot = switch (direction) {
                case DOWN -> 180;
                case UP -> 0;
                default -> 90;
            };
            int yRot = switch (direction) {
                case SOUTH -> 180;
                case WEST -> 270;
                case EAST -> 90;
                default -> 0;
            };
            return ConfiguredModel.builder()
                    .modelFile(gemstoneCrystal(state.getValue(GemstoneCrystalBlock.SIZE)))
                    .rotationX(xRot)
                    .rotationY(yRot)
                    .build();
        }, ModBlockStateProperties.GEMSTONE_TYPE);
    }

    private ModelFile gemstoneCrystal(GemstoneCrystalBlock.Size size) {
        return this.models().withExistingParent("block/gemstone/crystal/" + size.getSerializedName(), "block/tinted_cross")
                .texture("cross", MysticcraftMod.res("block/gemstone/crystal/" + size.getSerializedName()))
                .renderType("cutout");
    }

    private void makeGemstoneSeedItem() {
        ItemModelBuilder builder = itemModels().withExistingParent("gemstone_seed", "block/block");
        for (GemstoneSeedBlock.MaterialType type : GemstoneSeedBlock.MaterialType.values()) {
            builder.override()
                    .predicate(modLoc("material"), (type.ordinal() + 1) * .1f)
                    .model(variantGemstoneSeed(type));
        }
    }

    private void makeGemstoneSeeds() {
        getVariantBuilder(ModBlocks.GEMSTONE_SEED.get()).forAllStatesExcept(state -> {
            Direction direction = state.getValue(BlockStateProperties.FACING);
            GemstoneSeedBlock.MaterialType type = state.getValue(ModBlockStateProperties.STONE_TYPE);
            int yRot = switch (direction) {
                case DOWN, EAST -> 90;
                case WEST, UP -> 270;
                case SOUTH -> 180;
                case NORTH -> 0;
            };
            return ConfiguredModel.builder()
                    .modelFile(variantGemstoneSeed(type))
                    .rotationY(yRot)
                    .rotationX(switch (direction) {
                        case DOWN -> 90;
                        case UP -> -90;

                        case NORTH, SOUTH, WEST, EAST -> 0;
                    })
                    .build();
        }, ModBlockStateProperties.GEMSTONE_TYPE);
    }

    private ModelFile variantGemstoneSeed(GemstoneSeedBlock.MaterialType type) {
        return this.models().getBuilder("block/gemstone/seed/" + type.getSerializedName())
                .texture("material", blockTexture(type.getBlock()))
                .parent(baseGemstoneSeed());
    }

    private ModelFile baseGemstoneSeed() {
        return models().getBuilder("block/gemstone/seed/base")
                .parent(models().getExistingFile(mcLoc("block")))
                .renderType("cutout")
                .texture("gemstone", MysticcraftMod.res("block/gemstone/seed"))
                .texture("particle", "#gemstone")
                .element()
                .from(0, 0, 0).to(16, 16, 16)
                .allFaces((direction, faceBuilder) -> faceBuilder.texture("#material").cullface(direction))
                .end()
                .element()
                .from(0, 0, 0).to(16, 16, 16)
                .face(Direction.NORTH).texture("#gemstone").cullface(Direction.NORTH).tintindex(0).end()
                .end();
    }

    private ModelFile coloredBlock() {
        return models().withExistingParent("colored_block", "block")
                .texture("particle", "#all")
                .element()
                .from(0, 0, 0).to(16, 16, 16)
                .allFaces((direction, faceBuilder) -> faceBuilder
                        .uvs(0, 0, 16, 16)
                        .texture("#all")
                        .cullface(direction)
                        .tintindex(0)
                )
                .end();
    }

    private void wood(RotatedPillarBlock block, ResourceLocation texture) {
        ModelFile file = models().cubeColumn(name(block), block(texture), block(texture));
        axisBlock(block, file, file);
        simpleBlockItem(block, file);
    }

    public String name(Block block) {
        return key(block).getPath();
    }

    private ResourceLocation block(ResourceLocation in) {
        return in.withPrefix("block/");
    }
}
