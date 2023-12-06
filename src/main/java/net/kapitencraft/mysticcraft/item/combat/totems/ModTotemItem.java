package net.kapitencraft.mysticcraft.item.combat.totems;

import net.kapitencraft.mysticcraft.item.misc.IModItem;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public abstract class ModTotemItem extends Item implements IModItem {
    public ModTotemItem(Properties p_41383_) {
        super(p_41383_.stacksTo(1));
    }

    public abstract boolean onUse(LivingEntity living, DamageSource source);
}
