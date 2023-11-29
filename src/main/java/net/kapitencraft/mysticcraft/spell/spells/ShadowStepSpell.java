package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ShadowStepSpell {

    public static void execute(LivingEntity user, ItemStack ignored) {
        Level level = user.level;
        if (!level.isClientSide()) {
            List<LivingEntity> list = MathHelper.getAllEntitiesInsideCylinder(0.2f, user.getEyePosition(), user.getRotationVector(), 20, level).stream().filter(entity -> entity instanceof LivingEntity && entity != user).map(entity -> (LivingEntity) entity).toList();
            if (list.isEmpty()) return;
            LivingEntity living = list.get(0);
            Vec3 behind = living.getLookAngle().scale(-2);
            MiscHelper.teleport(user, behind.add(MathHelper.getPosition(user)));
        }
    }

    public static List<Component> getDescription() {
        return List.of(
                Component.literal("Teleports you behind an enemy withing 20 blocks range")
        );
    }
}
