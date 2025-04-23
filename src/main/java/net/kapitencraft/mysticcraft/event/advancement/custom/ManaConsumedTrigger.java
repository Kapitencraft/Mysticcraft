package net.kapitencraft.mysticcraft.event.advancement.custom;

import com.google.gson.JsonObject;
import net.kapitencraft.mysticcraft.MysticcraftMod;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public class ManaConsumedTrigger extends SimpleCriterionTrigger<ManaConsumedTrigger.Instance> {
    private static final ResourceLocation ID = MysticcraftMod.res("use_mana");

    @Override
    protected Instance createInstance(JsonObject pJson, ContextAwarePredicate pPlayer, DeserializationContext pContext) {
        Type type = Type.CODEC.byName(GsonHelper.getAsString(pJson, "amount_type"), Type.MORE);
        double amount = GsonHelper.getAsDouble(pJson, "amount");

        return new Instance(pPlayer, type, amount);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public void trigger(ServerPlayer pPlayer, double amount) {
        this.trigger(pPlayer, instance -> instance.enough(amount));
    }

    private enum Type implements StringRepresentable {
        EXACTLY,
        LESS,
        MORE;

        private static final EnumCodec<Type> CODEC = StringRepresentable.fromEnum(Type::values);

        @Override
        public @NotNull String getSerializedName() {
            return name().toLowerCase();
        }
    }

    public static AbstractCriterionTriggerInstance above(int amount) {
        return new Instance(ContextAwarePredicate.ANY, Type.MORE, amount);
    }

    public static AbstractCriterionTriggerInstance exactly(int amount) {
        return new Instance(ContextAwarePredicate.ANY, Type.EXACTLY, amount);
    }

    protected static class Instance extends AbstractCriterionTriggerInstance {
        private final Type type;
        private final double amount;

        private Instance(ContextAwarePredicate pPlayer, Type type, double amount) {
            super(ManaConsumedTrigger.ID, pPlayer);
            this.type = type;
            this.amount = amount;
        }

        public boolean enough(double mana) {
            return type == Type.EXACTLY ? mana == amount :
                    type == Type.LESS ? mana <= amount :
                            mana >= amount;
        }

        @Override
        public JsonObject serializeToJson(SerializationContext pConditions) {
            JsonObject data = super.serializeToJson(pConditions);
            data.addProperty("amount_type", this.type.getSerializedName());
            data.addProperty("amount", this.amount);
            return data;
        }
    }
}
