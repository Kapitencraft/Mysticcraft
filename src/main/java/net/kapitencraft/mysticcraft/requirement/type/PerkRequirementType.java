package net.kapitencraft.mysticcraft.requirement.type;

import com.mojang.serialization.Codec;
import net.kapitencraft.kap_lib.core.io.serialization.DataPackSerializer;
import net.kapitencraft.kap_lib.requirement.type.RequirementType;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistries;
import net.kapitencraft.mysticcraft.rpg.perks.Perk;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;

public class PerkRequirementType implements RequirementType<Holder<Perk>> {
    public static final PerkRequirementType INSTANCE = new PerkRequirementType();

    private static final Codec<Holder<Perk>> CODEC = RegistryFixedCodec.create(ModRegistries.Keys.PERKS);
    private static final StreamCodec<RegistryFriendlyByteBuf, Holder<Perk>> STREAM_CODEC = ByteBufCodecs.holderRegistry(ModRegistries.Keys.PERKS);
    private static final DataPackSerializer<Holder<Perk>> SERIALIZER = new DataPackSerializer<>(CODEC, STREAM_CODEC);

    @Override
    public DataPackSerializer<Holder<Perk>> serializer() {
        return SERIALIZER;
    }

    @Override
    public String getName() {
        return "perk";
    }
}
