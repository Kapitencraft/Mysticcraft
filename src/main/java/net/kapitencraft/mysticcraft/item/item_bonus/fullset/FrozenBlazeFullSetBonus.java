package net.kapitencraft.mysticcraft.item.item_bonus.fullset;

import net.kapitencraft.mysticcraft.item.item_bonus.FullSetBonus;
import net.kapitencraft.mysticcraft.misc.utils.MathUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class FrozenBlazeFullSetBonus extends FullSetBonus {
    public FrozenBlazeFullSetBonus() {
        super("Freezing Aura");
    }

    @Override
    public void onTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity, int slotID, boolean isSelected, int ticks) {
        List<LivingEntity> entities = MathUtils.getLivingAround(entity, 3);
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
