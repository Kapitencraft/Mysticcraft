package net.kapitencraft.mysticcraft.item.item_bonus.fullset;

import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.init.ModItems;
import net.kapitencraft.mysticcraft.item.item_bonus.FullSetBonus;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Consumer;

public class FrozenBlazeFullSetBonus extends FullSetBonus {
    public FrozenBlazeFullSetBonus() {
        super("Freezing Aura");
    }
    private int i = 0;


    @Override
    public void onTick(Level level, @NotNull LivingEntity entity) {
        List<LivingEntity> entities = MathHelper.getLivingAround(entity, 3);
        entities.forEach(living -> {
            if (!(living.isDeadOrDying() || living == entity) && i % 20 == 0) {
                living.hurt(new EntityDamageSource("freeze", entity).bypassArmor(), 2);
            }
            i++;
        });
    }

    @Override
    public EnumMap<EquipmentSlot, @Nullable RegistryObject<? extends Item>> getReqPieces() {
        return new EnumMap<>(ModItems.FROZEN_BLAZE_ARMOR);
    }

    @Override
    public Consumer<List<Component>> getDisplay() {
        return list -> list.add(Component.literal("Deals 2 Damage per Second to all Entities within 3 Blocks."));
    }
}
