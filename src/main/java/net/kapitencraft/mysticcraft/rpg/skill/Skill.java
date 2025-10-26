package net.kapitencraft.mysticcraft.rpg.skill;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.kapitencraft.kap_lib.helpers.ExtraStreamCodecs;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.datamaps.DataMapType;
import org.jetbrains.annotations.NotNull;

public enum Skill implements StringRepresentable {
    FISHING(Items.FISHING_ROD),
    COMBAT(Items.DIAMOND_SWORD),
    MINING(Items.DIAMOND_PICKAXE),
    FARMING(Items.DIAMOND_HOE),
    ENCHANTING(Items.ENCHANTING_TABLE);

    public static final DataMapType<Item, Integer> FISHING_XP_MAP = DataMapType.builder(
            MysticcraftMod.res("fishing_xp"),
            Registries.ITEM,
            Codec.INT
    ).build();
    public static final DataMapType<EntityType<?>, Integer> COMBAT_XP_MAP = DataMapType.builder(
            MysticcraftMod.res("combat_xp"),
            Registries.ENTITY_TYPE,
            Codec.INT
    ).build();
    public static final DataMapType<Block, Integer> FARMING_XP_MAP = DataMapType.builder(
            MysticcraftMod.res("farming_xp"),
            Registries.BLOCK,
            Codec.INT
    ).build();
    public static final DataMapType<Enchantment, Integer> ENCHANTING_XP_MAP = DataMapType.builder(
            MysticcraftMod.res("enchanting_xp"),
            Registries.ENCHANTMENT,
            Codec.INT
    ).build();
    public static final DataMapType<Block, Integer> MINING_XP_MAP = DataMapType.builder(
            MysticcraftMod.res("mining_xp"),
            Registries.BLOCK,
            Codec.INT
    ).build();

    public static final Codec<Skill> CODEC = StringRepresentable.fromEnum(Skill::values);
    public static final StreamCodec<ByteBuf, Skill> STREAM_CODEC = ExtraStreamCodecs.enumCodec(Skill.values());

    Skill(Item item) {
        this.item = item;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name().toLowerCase();
    }

    private final Item item;

    public Item getItem() {
        return item;
    }
}
