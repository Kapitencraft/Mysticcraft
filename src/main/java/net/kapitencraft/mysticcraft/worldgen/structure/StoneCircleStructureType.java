package net.kapitencraft.mysticcraft.worldgen.structure;

import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.kapitencraft.mysticcraft.tags.ModTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.NotNull;

public enum StoneCircleStructureType implements StringRepresentable {
    COMMON(MysticcraftMod.res("stone_circle/common"), ModTags.Biomes.HAS_COMMON_STONE_CIRCLE),
    DEEPSLATE(MysticcraftMod.res("stone_circle/deepslate"), ModTags.Biomes.HAS_DEEPSLATE_STONE_CIRCLE),
    SANDSTONE(MysticcraftMod.res("stone_circle/sandstone"), ModTags.Biomes.HAS_SANDSTONE_STONE_CIRCLE);

    public static final EnumCodec<StoneCircleStructureType> CODEC = StringRepresentable.fromEnum(StoneCircleStructureType::values);

    private final ResourceLocation structure;
    private final TagKey<Biome> biomes;

    StoneCircleStructureType(ResourceLocation structure, TagKey<Biome> biomes) {
        this.structure = structure;
        this.biomes = biomes;
    }

    public ResourceLocation getStructure() {
        return structure;
    }

    public TagKey<Biome> getBiomes() {
        return biomes;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name().toLowerCase();
    }
}
