package net.kapitencraft.mysticcraft.misc;

import net.kapitencraft.mysticcraft.misc.functions_and_interfaces.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ChangingAttributeModifier extends AttributeModifier {
    private final LivingEntity living;
    private final Provider<Double, LivingEntity> provider;

    public ChangingAttributeModifier(String p_22196_, Operation p_22198_, LivingEntity living, Provider<Double, LivingEntity> provider) {
        super(p_22196_, 0, p_22198_);
        this.living = living;
        this.provider = provider;
    }

    public ChangingAttributeModifier(UUID p_22200_, String p_22201_, Operation p_22203_, LivingEntity living, Provider<Double, LivingEntity> provider) {
        super(p_22200_, p_22201_, 0, p_22203_);
        this.living = living;
        this.provider = provider;
    }

    @Override
    public double getAmount() {
        return provider.provide(living);
    }

    @Override
    public @NotNull CompoundTag save() {
        throw new IllegalStateException("should not save changing attributeModifier");
    }
}
