package net.kapitencraft.mysticcraft.spell.spells;

import net.kapitencraft.mysticcraft.helpers.MathHelper;
import net.kapitencraft.mysticcraft.helpers.MiscHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class ShadowStepSpell {

    public static boolean execute(LivingEntity user, ItemStack ignored) {
        ArrayList<Vec3> lineOfSight = MathHelper.lineOfSight(user, 20, 0.25);
        List<LivingEntity> hit = new ArrayList<>();
        for (Vec3 vec3 : lineOfSight) {
            List<LivingEntity> entities = user.level.getEntitiesOfClass(LivingEntity.class, new AABB(vec3.x - 0.5, vec3.y - 0.5, vec3.z - 0.5, vec3.x + 0.5, vec3.y + 0.5, vec3.z + 0.5));
            for (LivingEntity living : entities) {
                if (living != user && !hit.contains(living)) {
                    hit.add(living);
                }
            }
        }
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
