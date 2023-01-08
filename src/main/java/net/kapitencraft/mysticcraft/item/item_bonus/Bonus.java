package net.kapitencraft.mysticcraft.item.item_bonus;

import com.google.common.collect.Multimap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import javax.annotation.Nullable;
import java.util.List;

public abstract class Bonus {
    protected final String name;

    protected Bonus(String name) {
        this.name = name;
    }

    public abstract void onEntityKilled(LivingEntity killed, LivingEntity user);
    public abstract @Nullable Multimap<Attribute, AttributeModifier> getModifiers(LivingEntity living);

    public abstract List<Component> getDisplay();
}
