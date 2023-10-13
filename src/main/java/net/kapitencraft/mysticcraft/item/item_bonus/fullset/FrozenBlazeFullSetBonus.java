package net.kapitencraft.mysticcraft.item.item_bonus.fullset;

import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.item.item_bonus.FullSetBonus;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;

public class FrozenBlazeFullSetBonus extends FullSetBonus {
    public FrozenBlazeFullSetBonus() {
        super("Freezing Aura");
    }
    private int i = 0;

    @Override
    public void onTick(@NotNull ItemStack stack, Level level, @NotNull Entity entity) {
        List<LivingEntity> entities = MathHelper.getLivingAround(entity, 3);
        entities.forEach(living -> {
            if (!(living.isDeadOrDying() || living == entity) && i % 20 == 0) {
                living.hurt(new EntityDamageSource("freeze", entity).bypassArmor(), 2);
            }
            i++;
        });
    }

    @Override
    public Consumer<List<Component>> getDisplay() {
        return list -> list.add(Component.literal("Deals 2 Damage per Second to all Entities within 3 Blocks."));
    }
}
