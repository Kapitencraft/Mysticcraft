package net.kapitencraft.mysticcraft.mob_effects;

import net.kapitencraft.mysticcraft.utils.TextUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class StunMobEffect extends MobEffect {
    public StunMobEffect() {
        super(MobEffectCategory.HARMFUL, -16777216);
    }

    @Override
    public boolean isDurationEffectTick(int p_19455_, int p_19456_) {
        return false;
    }

    @Override
    public void removeAttributeModifiers(@NotNull LivingEntity living, @NotNull AttributeMap p_19470_, int p_19471_) {
        super.removeAttributeModifiers(living, p_19470_, p_19471_);
        if (living instanceof Player player) {
            TextUtils.setHotbarDisplay(player, Component.translatable("effect.stun.released").withStyle(ChatFormatting.GREEN));
        }
    }
}
