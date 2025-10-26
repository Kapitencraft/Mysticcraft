package net.kapitencraft.mysticcraft.requirement.type;

import net.kapitencraft.kap_lib.io.serialization.DataPackSerializer;
import net.kapitencraft.kap_lib.requirements.type.RequirementType;
import net.kapitencraft.mysticcraft.capability.reforging.Reforge;
import net.kapitencraft.mysticcraft.capability.reforging.Reforges;

public class ReforgeRequirementType implements RequirementType<Reforge> {
    public static final DataPackSerializer<Reforge> SERIALIZER = new DataPackSerializer<>(Reforges.CODEC, Reforges.STREAM_CODEC);

    public static final ReforgeRequirementType INSTANCE = new ReforgeRequirementType();

    @Override
    public DataPackSerializer<Reforge> serializer() {
        return SERIALIZER;
    }

    @Override
    public String getName() {
        return "reforge";
    }
}
