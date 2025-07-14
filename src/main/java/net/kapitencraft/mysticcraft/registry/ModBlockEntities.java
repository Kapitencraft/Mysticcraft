package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.tech.block.entity.MagicFurnaceBlockEntity;
import net.kapitencraft.mysticcraft.tech.block.entity.ManaPortBlockEntity;
import net.kapitencraft.mysticcraft.tech.block.entity.PrismaticGeneratorBlockEntity;
import net.minecraft.Util;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.function.Supplier;

public interface ModBlockEntities {
    DeferredRegister<BlockEntityType<?>> REGISTRY = MysticcraftMod.registry(ForgeRegistries.BLOCK_ENTITY_TYPES);

    RegistryObject<BlockEntityType<ManaPortBlockEntity>> MANA_PORT = register("mana_port", ManaPortBlockEntity::new, ModBlocks.MANA_PORT);
    RegistryObject<BlockEntityType<PrismaticGeneratorBlockEntity>> PRISMATIC_GENERATOR = register("prismatic_generator", PrismaticGeneratorBlockEntity::new, ModBlocks.PRISMATIC_GENERATOR);
    RegistryObject<BlockEntityType<MagicFurnaceBlockEntity>> MAGIC_FURNACE = register("magic_furnace", MagicFurnaceBlockEntity::new, ModBlocks.MAGIC_FURNACE);

    @SafeVarargs
    @SuppressWarnings("DataFlowIssue")
    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> pFactory, Supplier<? extends Block>... pValidBlocks) {
        return REGISTRY.register(name, () -> BlockEntityType.Builder.of(pFactory, Arrays.stream(pValidBlocks).map(Supplier::get).toArray(Block[]::new)).build(Util.fetchChoiceType(References.BLOCK_ENTITY, name)));
    }
}