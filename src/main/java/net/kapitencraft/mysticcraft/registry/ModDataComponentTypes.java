package net.kapitencraft.mysticcraft.registry;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneCrystalBlock;
import net.kapitencraft.mysticcraft.block.gemstone.GemstoneSeedBlock;
import net.kapitencraft.mysticcraft.capability.ITieredItem;
import net.kapitencraft.mysticcraft.capability.elytra.ElytraAttachment;
import net.kapitencraft.mysticcraft.capability.gemstone.GemstoneHandler;
import net.kapitencraft.mysticcraft.capability.gemstone.ItemGemstoneData;
import net.kapitencraft.mysticcraft.capability.reforging.Reforge;
import net.kapitencraft.mysticcraft.capability.reforging.Reforges;
import net.kapitencraft.mysticcraft.capability.spell.ItemSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.Unit;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public interface ModDataComponentTypes {
    DeferredRegister.DataComponents REGISTRY = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, MysticcraftMod.MOD_ID);

    private static <T> Supplier<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder) {
        return REGISTRY.registerComponentType(name, builder);
    }

    Supplier<DataComponentType<ItemGemstoneData>> ITEM_GEMSTONE_DATA = register("gemstone/data",
            itemGemstoneDataBuilder -> itemGemstoneDataBuilder.persistent(ItemGemstoneData.CODEC).networkSynchronized(ItemGemstoneData.STREAM_CODEC));
    Supplier<DataComponentType<GemstoneCrystalBlock.Size>> GEMSTONE_CRYSTAL_SIZE = register("gemstone/crystal_size",
            sizeBuilder -> sizeBuilder.persistent(GemstoneCrystalBlock.Size.CODEC).networkSynchronized(GemstoneCrystalBlock.Size.STREAM_CODEC));
    Supplier<DataComponentType<GemstoneSeedBlock.MaterialType>> GEMSTONE_SEED_MATERIAL = register("gemstone/seed_material",
            materialTypeBuilder -> materialTypeBuilder.persistent(GemstoneSeedBlock.MaterialType.CODEC));
    Supplier<DataComponentType<GemstoneHandler>> EMBEDDED_GEMSTONES = register("embedded_gemstones",
            gemstoneHandlerBuilder -> gemstoneHandlerBuilder.persistent(GemstoneHandler.CODEC).networkSynchronized(GemstoneHandler.STREAM_CODEC));
    Supplier<DataComponentType<Unit>> SOUL_BOUND = register("soul_bound",
            unitBuilder -> unitBuilder.persistent(Codec.unit(Unit.INSTANCE)).networkSynchronized(StreamCodec.unit(Unit.INSTANCE)));
    Supplier<DataComponentType<Reforge>> REFORGE = register("reforge",
            reforgeBuilder -> reforgeBuilder.persistent(Reforges.CODEC));
    Supplier<DataComponentType<ElytraAttachment>> ELYTRA = register("elytra",
            elytraAttachmentBuilder -> elytraAttachmentBuilder.persistent(ElytraAttachment.CODEC).networkSynchronized(ElytraAttachment.STREAM_CODEC));
    Supplier<DataComponentType<Either<BlockPos, Integer>>> SPELL_TARGET = register("spell_target",
            eitherBuilder -> eitherBuilder.networkSynchronized(ByteBufCodecs.either(BlockPos.STREAM_CODEC, ByteBufCodecs.INT)));
    Supplier<DataComponentType<ItemSpells>> ITEM_SPELLS = register("item_spells",
            itemSpellsBuilder -> itemSpellsBuilder.persistent(ItemSpells.CODEC).networkSynchronized(ItemSpells.STREAM_CODEC));
    Supplier<DataComponentType<Integer>> STARS = register("stars",
            integerBuilder -> integerBuilder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));
    Supplier<DataComponentType<ITieredItem.ItemTier>> TIER = register("tier",
            itemTierBuilder -> itemTierBuilder.persistent(ITieredItem.ItemTier.CODEC));
    Supplier<DataComponentType<Integer>> SHORTBOW_COOLDOWN = register("shortbow_cooldown",
            integerBuilder -> integerBuilder.networkSynchronized(ByteBufCodecs.INT));
    Supplier<DataComponentType<Integer>> WALLET_BALANCE = register("wallet_balance",
            integerBuilder -> integerBuilder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));
    Supplier<DataComponentType<BlockPos>> TECH_WAND_ORIGIN = register("origin",
            blockPosBuilder -> blockPosBuilder.networkSynchronized(BlockPos.STREAM_CODEC));
}
