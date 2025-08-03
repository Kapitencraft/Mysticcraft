package net.kapitencraft.mysticcraft.requirement.type;

import net.kapitencraft.kap_lib.requirements.type.RequirementType;
import net.kapitencraft.mysticcraft.capability.reforging.Reforge;
import net.kapitencraft.mysticcraft.capability.reforging.Reforges;
import net.minecraft.resources.ResourceLocation;

public class ReforgeRequirementType implements RequirementType<Reforge> {
    public static final ReforgeRequirementType INSTANCE = new ReforgeRequirementType();

    @Override
    public ResourceLocation getId(Reforge value) {
        return value.getId();
    }

    @Override
    public Reforge getById(ResourceLocation location) {
        return Reforges.byName(location);
    }

    @Override
    public String getName() {
        return "reforge";
    }
}
