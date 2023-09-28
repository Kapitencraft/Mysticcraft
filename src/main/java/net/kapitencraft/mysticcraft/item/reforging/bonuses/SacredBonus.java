package net.kapitencraft.mysticcraft.item.reforging.bonuses;

import net.kapitencraft.mysticcraft.item.item_bonus.ReforgingBonus;
import net.kapitencraft.mysticcraft.utils.MiscUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;

import java.util.List;
import java.util.function.Consumer;

public class SacredBonus extends ReforgingBonus {
    public SacredBonus() {
        super("Sacred Blade");
    }

    @Override
    public float onEntityHurt(LivingEntity hurt, LivingEntity user, MiscUtils.DamageType type, float damage) {
        if (type == MiscUtils.DamageType.MELEE && hurt.getMobType() == MobType.UNDEAD) {
            damage *= 1.1f;
        }
        return damage;
    }

    @Override
    public Consumer<List<Component>> getDisplay() {
        return list -> list.add(Component.literal("increases damage dealt to undead by 10%"));
    }
}
