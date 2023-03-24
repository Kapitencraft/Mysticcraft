package net.kapitencraft.mysticcraft.item.item_bonus;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public abstract class Bonus {
    protected final String name;

    protected Bonus(String name) {
        this.name = name;
    }

    public abstract void onEntityKilled(LivingEntity killed, LivingEntity user, MiscUtils.DamageType type);
    public abstract @Nullable Multimap<Attribute, AttributeModifier> getModifiers(LivingEntity living);
    public abstract void onTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity, int slotID, boolean isSelected, int ticks);
    public abstract List<Component> getDisplay();
}
