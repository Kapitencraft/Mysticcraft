package net.kapitencraft.mysticcraft.event.advancement;

import net.kapitencraft.mysticcraft.event.advancement.custom.ManaConsumedTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;

import java.util.function.Supplier;

public interface ModCriteriaTriggers {
    ManaConsumedTrigger USE_MANA = register(ManaConsumedTrigger::new);


    private static <T extends CriterionTrigger<?>> T register(Supplier<T> supplier) {
        return CriteriaTriggers.register(supplier.get());
    }

    static void init() {
        //<clinit>
    }
}
