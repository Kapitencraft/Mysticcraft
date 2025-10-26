package net.kapitencraft.mysticcraft.registry;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.entity.pedestal.AltarBlockEntity;
import net.kapitencraft.mysticcraft.block.entity.pedestal.PedestalBlockEntity;
import net.kapitencraft.mysticcraft.tech.block.entity.*;
import net.minecraft.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.datafix.fixes.References;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Arrays;
import java.util.function.Supplier;

public interface ModBlockEntities {
    DeferredRegister<BlockEntityType<?>> REGISTRY = MysticcraftMod.registry(Registries.BLOCK_ENTITY_TYPE);

    Supplier<BlockEntityType<ManaPortBlockEntity>> MANA_PORT = register("mana_port", ManaPortBlockEntity::new, ModBlocks.MANA_PORT);
    Supplier<BlockEntityType<PrismaticGeneratorBlockEntity>> PRISMATIC_GENERATOR = register("prismatic_generator", PrismaticGeneratorBlockEntity::new, ModBlocks.PRISMATIC_GENERATOR);
    Supplier<BlockEntityType<VulcanicGeneratorBlockEntity>> VULCANIC_GENERATOR = register("vulcanic_generator", VulcanicGeneratorBlockEntity::new, ModBlocks.VULCANIC_GENERATOR);
    Supplier<BlockEntityType<MagicFurnaceBlockEntity>> MAGIC_FURNACE = register("magic_furnace", MagicFurnaceBlockEntity::new, ModBlocks.MAGIC_FURNACE);
    Supplier<BlockEntityType<ManaBatteryBlockEntity>> MANA_BATTERY = register("mana_accumulator", ManaBatteryBlockEntity::new, ModBlocks.MANA_BATTERY);

    Supplier<BlockEntityType<SpellCasterTurretBlockEntity>> SPELL_CASTER_TURRET = register("turret/spell_caster", SpellCasterTurretBlockEntity::new, ModBlocks.SPELL_CASTER_TURRET);
    Supplier<BlockEntityType<ObeliskTurretBlockEntity>> OBELISK_TURRET = register("turret/obelisk", ObeliskTurretBlockEntity::new, ModBlocks.OBELISK_TURRET);

    Supplier<BlockEntityType<AltarBlockEntity>> ALTAR = register("altar", AltarBlockEntity::new, ModBlocks.ALTAR);
    Supplier<BlockEntityType<PedestalBlockEntity>> PEDESTAL = register("pedestal", PedestalBlockEntity::new, ModBlocks.PEDESTAL);

    @SafeVarargs
    @SuppressWarnings("DataFlowIssue")
    private static <T extends BlockEntity> Supplier<BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> pFactory, Supplier<? extends Block>... pValidBlocks) {
        return REGISTRY.register(name, () -> BlockEntityType.Builder.of(pFactory, Arrays.stream(pValidBlocks).map(Supplier::get).toArray(Block[]::new)).build(Util.fetchChoiceType(References.BLOCK_ENTITY, name)));
    }
}