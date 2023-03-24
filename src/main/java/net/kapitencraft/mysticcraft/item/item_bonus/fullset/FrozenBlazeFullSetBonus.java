package net.kapitencraft.mysticcraft.item.item_bonus.fullset;

import com.google.common.collect.Multimap;
import net.kapitencraft.mysticcraft.item.item_bonus.FullSetBonus;
import net.kapitencraft.mysticcraft.misc.utils.MiscUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FrozenBlazeFullSetBonus extends FullSetBonus {
    public FrozenBlazeFullSetBonus() {
        super("Freezing Aura");
    }

    @Override
    public void onEntityKilled(LivingEntity killed, LivingEntity user, MiscUtils.DamageType type) {

    }



    @Nullable
    @Override
    public Multimap<Attribute, AttributeModifier> getModifiers(LivingEntity living) {
        return null;
    }

    @Override
    public void onTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity, int slotID, boolean isSelected, int ticks) {
        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(3));
        for (LivingEntity living : entities) {
            if (!(living.isDeadOrDying() || living == entity) && ticks % 20 == 0) {
                living.hurt(new EntityDamageSource("freeze", entity).bypassArmor(), 2);
            }
        }
    }

    @Override
    public List<Component> getDisplay() {
        return List.of(Component.literal("Deals 2 Damage per Second to all Entities within 3 Blocks."));
    }
}
