package net.kapitencraft.mysticcraft.requirement.condition;

import com.mojang.serialization.MapCodec;
import net.kapitencraft.kap_lib.core.io.serialization.RegistrySerializer;
import net.kapitencraft.kap_lib.requirement.conditions.abstracts.ReqCondition;
import net.kapitencraft.mysticcraft.registry.ModAttachmentTypes;
import net.kapitencraft.mysticcraft.registry.custom.ModRegistries;
import net.kapitencraft.mysticcraft.rpg.classes.RPGClass;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class ClassSelectedRequirementCondition extends ReqCondition<ClassSelectedRequirementCondition> {
    private final Holder<RPGClass> holder;

    private static final MapCodec<ClassSelectedRequirementCondition> CODEC = RegistryFixedCodec.create(ModRegistries.Keys.CLASSES).xmap(ClassSelectedRequirementCondition::new, t -> t.holder).fieldOf("value");
    private static final StreamCodec<RegistryFriendlyByteBuf, ClassSelectedRequirementCondition> STREAM_CODEC = ByteBufCodecs.holderRegistry(ModRegistries.Keys.CLASSES).map(ClassSelectedRequirementCondition::new, t -> t.holder);
    public static final RegistrySerializer<ClassSelectedRequirementCondition> SERIALIZER = new RegistrySerializer<>(CODEC, STREAM_CODEC);

    public ClassSelectedRequirementCondition(Holder<RPGClass> holder) {
        this.holder = holder;
    }

    @Override
    public boolean matches(LivingEntity player) {
        return player.hasData(ModAttachmentTypes.CHARACTER) && player.getData(ModAttachmentTypes.CHARACTER).getRpgClass() == holder;
    }

    @Override
    public RegistrySerializer<ClassSelectedRequirementCondition> getSerializer() {
        return SERIALIZER;
    }

    @Override
    protected @NotNull Component cacheDisplay() {
        return Component.translatable("class_req.display", Component.translatable(Util.makeDescriptionId("class", this.holder.getKey().location())));
    }
}
