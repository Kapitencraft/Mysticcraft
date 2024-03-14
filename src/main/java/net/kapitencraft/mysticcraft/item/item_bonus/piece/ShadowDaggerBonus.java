package net.kapitencraft.mysticcraft.item.item_bonus.piece;

import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.item_bonus.ExtraBonus;
import net.kapitencraft.mysticcraft.cooldown.Cooldown;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class ShadowDaggerBonus extends ExtraBonus {
    public ShadowDaggerBonus() {

    }

    @Override
    public Consumer<List<Component>> getDisplay() {
        return list -> list.add(Component.literal("deals 2x damage from behind the enemy"));
    }

    @Override
    public void onUse() {

    }

    @Nullable
    @Override
    public Cooldown getCooldown() {
        return null;
    }

    @Override
    public float onEntityHurt(LivingEntity hurt, LivingEntity user, MiscHelper.DamageType type, float damage) {
        if (MathHelper.isBehind(user, hurt)) damage *= 2;
        return damage;
    }
}
