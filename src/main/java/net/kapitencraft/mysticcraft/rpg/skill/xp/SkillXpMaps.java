package net.kapitencraft.mysticcraft.rpg.skill.xp;

import com.mojang.serialization.Codec;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.rpg.skill.xp.provider.combat.EntityXpProvider;
import net.kapitencraft.mysticcraft.rpg.skill.xp.provider.item.ItemStackXpProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

public class SkillXpMaps {
    public static final DataMapType<Item, ItemStackXpProvider> FISHING = DataMapType.builder(
            MysticcraftMod.res("fishing_xp"),
            Registries.ITEM,
            ItemStackXpProvider.CODEC
    ).build();
    public static final DataMapType<EntityType<?>, EntityXpProvider> COMBAT = DataMapType.builder(
            MysticcraftMod.res("combat_xp"),
            Registries.ENTITY_TYPE,
            EntityXpProvider.CODEC
    ).build();
    public static final DataMapType<Block, Integer> FARMING = DataMapType.builder(
            MysticcraftMod.res("farming_xp"),
            Registries.BLOCK,
            Codec.INT
    ).build();
    public static final DataMapType<Enchantment, Integer> ENCHANTING = DataMapType.builder(
            MysticcraftMod.res("enchanting_xp"),
            Registries.ENCHANTMENT,
            Codec.INT
    ).build();
    public static final DataMapType<Block, Integer> MINING = DataMapType.builder(
            MysticcraftMod.res("mining_xp"),
            Registries.BLOCK,
            Codec.INT
    ).build();
    public static final DataMapType<Block, Integer> FORAGING = DataMapType.builder(
            MysticcraftMod.res("foraging_xp"),
            Registries.BLOCK,
            Codec.INT
    ).build();
    public static final DataMapType<Potion, Integer> ALCHEMY = DataMapType.builder(
            MysticcraftMod.res("alchemy_xp"),
            Registries.POTION,
            Codec.INT
    ).build();
}
