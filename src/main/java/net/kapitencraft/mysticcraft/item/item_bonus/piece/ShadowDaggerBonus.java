package net.kapitencraft.mysticcraft.item.item_bonus.piece;

import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.kapitencraft.mysticcraft.item.item_bonus.PieceBonus;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;
import java.util.function.Consumer;

public class ShadowDaggerBonus extends PieceBonus {
    public ShadowDaggerBonus() {
        super("?");
    }

    @Override
    public Consumer<List<Component>> getDisplay() {
        return list -> list.add(Component.literal("deals 100% more damage from behind the enemy"));
    }

    @Override
    public float onEntityHurt(LivingEntity hurt, LivingEntity user, MiscHelper.DamageType type, float damage) {
        if (MathHelper.isBehind(user, hurt)) damage *= 2;
        return damage;
    }
}
