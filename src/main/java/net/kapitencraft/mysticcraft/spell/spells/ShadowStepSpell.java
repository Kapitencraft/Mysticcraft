package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ShadowStepSpell {

    public static boolean execute(LivingEntity user, ItemStack ignored) {
        List<LivingEntity> entities = MathHelper.getAllEntitiesInsideCone(LivingEntity.class, 15, 20, user.position(), user.getRotationVector(), user.level);
        List<LivingEntity> hit = new ArrayList<>();
        entities.stream().filter(living -> living != user).forEach(hit::add);
        if (next(hit, user)) MiscHelper.delayed(10, ()-> {
                    if (next(hit, user)) MiscHelper.delayed(10, ()-> next(hit, user));
                }
        );
        return true;
    }

    private static boolean next(List<LivingEntity> list, LivingEntity user) {
        if (list.isEmpty()) return false;
        LivingEntity target = list.get(0);
        list.remove(0);
        teleportBehind(target, user);
        return true;
    }

    private static void teleportBehind(LivingEntity target, LivingEntity user) {
        Vec3 behind = target.getLookAngle().scale(-2);
        MiscHelper.teleport(user, target.position().add(behind));
        Vec2 targetVec = MathHelper.createTargetRotationFromEyeHeight(user, target);
        user.setXRot(targetVec.x);
        user.setYBodyRot(targetVec.y);
        user.setYRot(targetVec.y);
        user.setYHeadRot(targetVec.y);
    }

    public static List<Component> getDescription() {
        return List.of(
                Component.literal("Teleports you behind an enemy withing 20 blocks range")
        );
    }
}
