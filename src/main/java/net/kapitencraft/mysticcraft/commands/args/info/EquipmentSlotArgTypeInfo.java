package net.kapitencraft.mysticcraft.commands.args.info;

import com.google.gson.JsonObject;
import net.kapitencraft.mysticcraft.commands.args.type.EquipmentSlotArgumentType;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

public class EquipmentSlotArgTypeInfo implements ArgumentTypeInfo<EquipmentSlotArgumentType, EquipmentSlotArgTypeInfo.Template> {
    @Override
    public void serializeToNetwork(@NotNull Template template, @NotNull FriendlyByteBuf buf) {
    }

    @Override
    public @NotNull Template deserializeFromNetwork(@NotNull FriendlyByteBuf p_235377_) {
        return new Template();
    }

    @Override
    public void serializeToJson(@NotNull Template p_235373_, @NotNull JsonObject p_235374_) {

    }

    @Override
    public @NotNull Template unpack(@NotNull EquipmentSlotArgumentType p_235372_) {
        return new Template();
    }

    public class Template implements ArgumentTypeInfo.Template<EquipmentSlotArgumentType> {

        @Override
        public @NotNull EquipmentSlotArgumentType instantiate(@NotNull CommandBuildContext p_235378_) {
            return new EquipmentSlotArgumentType();
        }

        @Override
        public @NotNull ArgumentTypeInfo<EquipmentSlotArgumentType, ?> type() {
            return EquipmentSlotArgTypeInfo.this;
        }
    }
}
