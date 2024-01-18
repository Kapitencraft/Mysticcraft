package net.kapitencraft.mysticcraft.requirements.type;

import net.minecraft.stats.Stat;

public class StatReqType extends RequirementType {
    public StatReqType(Stat<?> stat, int level) {
        super(value -> value.getStats().getValue(stat), level);
    }
}
