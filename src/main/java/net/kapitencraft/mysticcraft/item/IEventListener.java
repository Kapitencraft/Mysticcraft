package net.kapitencraft.mysticcraft.item;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.misc.cooldown.Cooldown;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public interface IEventListener {

    void onEntityKilled(LivingEntity killed, LivingEntity user, MiscHelper.DamageType type);

    void onUse();


    @Nullable
    Cooldown getCooldown();

    @Nullable
    Multimap<Attribute, AttributeModifier> getModifiers(LivingEntity living);

    float onEntityHurt(LivingEntity hurt, LivingEntity user, MiscHelper.DamageType type, float damage);

    float onTakeDamage(LivingEntity hurt, LivingEntity source, MiscHelper.DamageType type, float damage);

    void onTick(Level level, @NotNull LivingEntity entity);

    void onApply(LivingEntity living);

    void onRemove(LivingEntity living);

}
