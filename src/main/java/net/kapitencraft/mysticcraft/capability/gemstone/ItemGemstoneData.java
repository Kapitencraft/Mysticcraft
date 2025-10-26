package net.kapitencraft.mysticcraft.capability.gemstone;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record ItemGemstoneData(GemstoneType type, GemstoneType.Rarity rarity) {
    public static final Codec<ItemGemstoneData> CODEC = RecordCodecBuilder.create(i -> i.group(
            GemstoneType.CODEC.fieldOf("type").forGetter(ItemGemstoneData::type),
            GemstoneType.Rarity.CODEC.fieldOf("rarity").forGetter(ItemGemstoneData::rarity)
    ).apply(i, ItemGemstoneData::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ItemGemstoneData> STREAM_CODEC = StreamCodec.composite(
            GemstoneType.STREAM_CODEC, ItemGemstoneData::type,
            GemstoneType.Rarity.STREAM_CODEC, ItemGemstoneData::rarity,
            ItemGemstoneData::new
    );
}
