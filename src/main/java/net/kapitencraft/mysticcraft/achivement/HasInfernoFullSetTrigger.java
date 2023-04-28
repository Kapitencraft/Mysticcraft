package net.kapitencraft.mysticcraft.achivement;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.SimpleCriterionTrigger;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

public class HasInfernoFullSetTrigger extends SimpleCriterionTrigger<HasInfernoFullSetTrigger.HasInfernoFullSetTriggerInstance> {
    static final ResourceLocation ID = new ResourceLocation("mysticcraft:has_inferno");

    @Override
    protected @NotNull HasInfernoFullSetTriggerInstance createInstance(@NotNull JsonObject jsonObject, EntityPredicate.@NotNull Composite composite, @NotNull DeserializationContext p_66250_) {
        return new HasInfernoFullSetTriggerInstance(ID, composite);
    }

    @Override
    public @NotNull ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer player) {
        trigger(player, (hasInfernoFullSetTriggerInstance -> true));
    }

    public static class HasInfernoFullSetTriggerInstance extends AbstractCriterionTriggerInstance {
        public HasInfernoFullSetTriggerInstance(ResourceLocation p_16975_, EntityPredicate.Composite p_16976_) {
            super(p_16975_, p_16976_);
        }
    }
}